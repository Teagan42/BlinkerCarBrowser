package rocks.teagantotally.blinkercarbrowser.ui.vms;

import android.content.Context;
import android.databinding.Observable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.inject.Inject;

import rocks.teagantotally.blinkercarbrowser.BR;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ViewScope;
import rocks.teagantotally.blinkercarbrowser.events.CancelEvent;
import rocks.teagantotally.blinkercarbrowser.events.DialogEvent;
import rocks.teagantotally.blinkercarbrowser.events.RetrieveResultEvent;
import rocks.teagantotally.blinkercarbrowser.events.ServiceEvent;
import rocks.teagantotally.blinkercarbrowser.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.blinkercarbrowser.events.notifications.SnackbarNotificationEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListResultEvent;
import rocks.teagantotally.blinkercarbrowser.services.VehicleService;
import rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview.CompositeItemBinder;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.ClickHandler;

/**
 * Created by tglenn on 8/30/17.
 */

@ViewScope
public class VehicleListVM
          extends BaseListVM<BaseVM> {
    private static final int LIMIT = 40;

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
              new SwipeRefreshLayout.OnRefreshListener() {
                  @Override
                  public void onRefresh() {
                      setShouldNotify(true);
                      setRefreshing(true);
                      setShouldNotify(false);
                      eventBus.post(retrieveVehicleListEvent);
                  }
              };
    private ClickHandler<BaseVM> itemClickHandler =
              new ClickHandler<BaseVM>() {
                  @Override
                  public void onClick(BaseVM item) {
                      if (!(item instanceof VehicleVM)) {
                          return;
                      }

                      eventBus.post(new DialogEvent(R.layout.dialog_vehicle_image,
                                                    BR.vm,
                                                    item));
                  }
              };
    private SearchOptionsVM searchOptionsVM;
    private String query;
    private List<VehicleVM> vehicleVMs = new ArrayList<>();
    private RetrieveVehicleListEvent retrieveVehicleListEvent;
    private ProgressDialogNotificationEvent progressDialogNotificationEvent;
    private TextWatcher queryTextChangeListener =
              new TextWatcher() {
                  private static final long DELAY_MILLISECONDS = 300;
                  private Timer timer;

                  @Override
                  public void beforeTextChanged(CharSequence charSequence,
                                                int i,
                                                int i1,
                                                int i2) {

                  }

                  @Override
                  public void onTextChanged(CharSequence charSequence,
                                            int i,
                                            int i1,
                                            int i2) {

                  }

                  @Override
                  public void afterTextChanged(final Editable editable) {
                      if (timer != null) {
                          timer.cancel();
                      }
                      timer = new Timer();
                      timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        setQuery(editable.toString());
                                    }
                                },
                                DELAY_MILLISECONDS
                      );
                  }
              };

    @Inject
    public VehicleListVM(Context context,
                         EventBus eventBus) {
        super(eventBus);

        setLayoutManager(new LinearLayoutManager(context));
        setItemClickHandler(itemClickHandler);
        setItemBinder(new CompositeItemBinder<>(
                  LoadMoreVM.itemBinder,
                  YearDividerVM.itemBinder,
                  VehicleVM.itemBinder
        ));

        retrieveVehicleListEvent = new RetrieveVehicleListEvent(0,
                                                                LIMIT);
        showProgressDialog();
    }

    /**
     * Set the search options view model
     *
     * @param searchOptionsVM Search options view model
     * @return This view model
     */
    public VehicleListVM setSearchOptions(SearchOptionsVM searchOptionsVM) {
        this.searchOptionsVM = searchOptionsVM;
        this.searchOptionsVM.addOnPropertyChangedCallback(
                  new OnPropertyChangedCallback() {
                      @Override
                      public void onPropertyChanged(Observable sender,
                                                    int propertyId) {
                          // Bubble the change up
                          setShouldNotify(false);
                          setQuery(query);
                          setShouldNotify(true);
                      }
                  }
        );
        return this;
    }

    /**
     * @return Visibility of the search bar view
     */
    public int getSearchBarVisibility() {
        if (searchOptionsVM == null) {
            return View.GONE;
        }

        return searchOptionsVM.getShowSearchBar()
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return Text change listener for query string
     */
    public TextWatcher getQueryTextChangeListener() {
        return queryTextChangeListener;
    }

    /**
     * @return The query string
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the query string
     *
     * @param query String to query
     */
    public void setQuery(String query) {
        this.query = query;
        retrieveVehicleListEvent =
                  new RetrieveVehicleListEvent(searchOptionsVM.getQueryTypes(),
                                               query,
                                               0,
                                               LIMIT);
        eventBus.post(retrieveVehicleListEvent);
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return onRefreshListener;
    }

    /**
     * Unregisters this view model from the event bus
     */
    @Override
    public void unsubscribe() {
        super.unsubscribe();
        for (BaseVM item : getItems()) {
            item.unsubscribe();
        }
    }

    /**
     * @return The search options view model
     */
    public SearchOptionsVM getSearchOptionsVM() {
        return searchOptionsVM;
    }

    private void populateItems() {
        List<BaseVM> itemsToPopulate = new ArrayList<>();
        BaseVM lastItem = getSize() > 0
                          ? getItems().get(getSize() - 1)
                          : null;
        LoadMoreVM loadMoreVM =
                  lastItem instanceof LoadMoreVM
                  ? (LoadMoreVM) lastItem
                  : null;

        if (!searchOptionsVM.getGroupByYear()) {
            itemsToPopulate.addAll(vehicleVMs);
        } else {
            Map<String, List<VehicleVM>> groupedVehicles = new TreeMap<>();
            // Group vehicles by year
            for (VehicleVM vehicleVM : vehicleVMs) {
                if (!groupedVehicles.containsKey(vehicleVM.getYear())) {
                    groupedVehicles.put(vehicleVM.getYear(),
                                        new ArrayList<VehicleVM>());
                }
                groupedVehicles.get(vehicleVM.getYear())
                               .add(vehicleVM);
            }
            // Add to list
            for (String key : groupedVehicles.keySet()) {
                itemsToPopulate.add(Injector.getViewModels()
                                            .getYearDivider()
                                            .setYear(key));
                itemsToPopulate.addAll(groupedVehicles.get(key));
            }
        }

        if (loadMoreVM != null) {
            itemsToPopulate.add(loadMoreVM);
        }

        // Update recycler view items
        setItems(itemsToPopulate);
    }

    private void showProgressDialog() {
        if (progressDialogNotificationEvent != null) {
            return;
        }

        progressDialogNotificationEvent = new ProgressDialogNotificationEvent("Loading");
        eventBus.post(progressDialogNotificationEvent);
    }

    private void dismissProgressDialog() {
        if (progressDialogNotificationEvent == null) {
            return;
        }

        eventBus.post(new CancelEvent(progressDialogNotificationEvent));
        progressDialogNotificationEvent = null;
    }

    //region Event subscriptions

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ServiceEvent event) {
        if (!Objects.equals(event.getServiceTypeClass(),
                            VehicleService.class)) {
            // Not the service we are looking for
            return;
        }

        if (event.getStatus() == ServiceEvent.Status.STOPPED) {
            eventBus.post(new SnackbarNotificationEvent("Something went wrong",
                                                        SnackbarNotificationEvent.Length.SHORT));
        } else {
            eventBus.post(retrieveVehicleListEvent);
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveVehicleListResultEvent event) {
        if (!Objects.equals(event.getRetrieveEvent(),
                            retrieveVehicleListEvent)) {
            // Not the latest event
            return;
        }
        dismissProgressDialog();
        if (event.getResult() != RetrieveResultEvent.Result.SUCCESS) {
            eventBus.post(new SnackbarNotificationEvent(event.getErrorReason(),
                                                        SnackbarNotificationEvent.Length.LONG));
            return;
        }

        setShouldNotify(true);
        RetrieveVehicleListEvent retrieveEvent = event.getRetrieveEvent();
        if (retrieveEvent.getOffset() == 0) {
            getItems().clear();
            vehicleVMs.clear();
        } else if (getItems().get(getSize() - 1) instanceof LoadMoreVM) {
            remove(getSize() - 1);
        }

        for (Vehicle vehicle : event.getResults()) {
            vehicleVMs.add(Injector.getViewModels()
                                   .getVehicle()
                                   .setModel(vehicle));
        }

        populateItems();

        if (event.getResults()
                 .size() == retrieveEvent.getLimit()) {
            if (!searchOptionsVM.getShowSearchBar()) {
                retrieveVehicleListEvent =
                          new RetrieveVehicleListEvent(vehicleVMs.size(),
                                                       LIMIT);
            } else {
                retrieveVehicleListEvent =
                          new RetrieveVehicleListEvent(retrieveEvent.getQueryTypes(),
                                                       retrieveEvent.getQuery(),
                                                       vehicleVMs.size(),
                                                       LIMIT);
            }
            addItem(Injector.getViewModels()
                            .getLoadMore()
                            .setLoadMoreEvent(retrieveVehicleListEvent));
        }

        setRefreshing(false);
        setShouldNotify(true);
    }

    //endregion
}
