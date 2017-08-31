package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetailersRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveRetailersEvent;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.CompositeItemBinder;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetailerListVM
          extends BaseListVM<BaseObservable> {
    private static final int LIMIT = 40;

    private List<BaseObservable> retailers;
    private boolean isRefreshing;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
              new SwipeRefreshLayout.OnRefreshListener() {
                  @Override
                  public void onRefresh() {
                      setRefreshing(true);
                      new RetrieveRetailersEvent(0,
                                                 LIMIT).post();
                  }
              };
    private EventBus eventBus;
    private Context context;

    public RetailerListVM(EventBus eventBus,
                          Context context) {
        this.eventBus = eventBus;

        eventBus.register(this);
        setLayoutManager(new LinearLayoutManager(context));
        setItemBinder(new CompositeItemBinder<BaseObservable>(
                  LoadMoreVM.itemBinder,
                  RetailerVM.itemBinder
        ));

        onRefreshListener.onRefresh();
    }

    public List<BaseObservable> getRetailers() {
        return retailers;
    }

    public void setRetailers(List<BaseObservable> retailers) {
        if (Objects.equals(this.retailers,
                           retailers)) {
            return;
        }
        this.retailers = retailers;
        notifyChange();
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return onRefreshListener;
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        if (isRefreshing == refreshing) {
            return;
        }
        isRefreshing = refreshing;
        notifyChange();
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return onRefreshListener;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetailersRetrievedEvent event) {
        if (event.getRetrieveEvent()
                 .getOffset() == 0) {
            setRetailers(new ArrayList<BaseObservable>());
        } else if (retailers.get(retailers.size() - 1) instanceof LoadMoreVM) {
            retailers.remove(retailers.size() - 1);
        }
        for (Retailer retailer : event.getRetailers()) {
            retailers.add(new RetailerVM(retailer,
                                         context,
                                         eventBus));
        }

        if (event.getRetailers()
                 .size() == event.getRetrieveEvent()
                                 .getLimit()) {
            retailers.add(new LoadMoreVM(new RetrieveRetailersEvent(retailers.size(),
                                                                    LIMIT)));
        }

        notifyChange();
    }
}
