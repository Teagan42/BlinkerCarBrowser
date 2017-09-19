package rocks.teagantotally.blinkercarbrowser.ui.vms;

import org.greenrobot.eventbus.EventBus;

import java.util.EnumSet;

import javax.inject.Inject;

import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListEvent;

/**
 * Created by tglenn on 9/18/17.
 */

public class SearchOptionsVM
          extends BaseVM {
    private boolean showSearchBar = false;
    private boolean searchYear = true;
    private boolean searchMake = true;
    private boolean searchModel = true;
    private boolean groupByYear = false;
    private boolean groupByMake = false;
    private boolean reverseGroups = false;

    /**
     * Create a new view model instance
     *
     * @param eventBus Event bus to register with
     */
    @Inject
    public SearchOptionsVM(EventBus eventBus) {
        super(eventBus);
        setShouldNotify(true);
    }

    /**
     * @return Whether the search bar should be shown
     */
    public boolean getShowSearchBar() {
        return showSearchBar;
    }

    /**
     * Sets whether the search bar should be shown
     *
     * @param showSearchBar Whether the search bar should be shown
     */
    public void setShowSearchBar(boolean showSearchBar) {
        this.showSearchBar = showSearchBar;
        notifyChange();
    }

    /**
     * @return Whether to search vehicle manufacture year
     */
    public boolean getSearchYear() {
        return searchYear;
    }

    /**
     * Sets whether to search vehicle manufacture year
     *
     * @param searchYear Whether to search vehicle manufacture year
     */
    public void setSearchYear(boolean searchYear) {
        this.searchYear = searchYear;
        notifyChange();
    }

    /**
     * @return Whether to search the vehicle make
     */
    public boolean getSearchMake() {
        return searchMake;
    }

    /**
     * Set whether to search the make of the vehicle
     *
     * @param searchMake Whether to search the vehicle make
     */
    public void setSearchMake(boolean searchMake) {
        this.searchMake = searchMake;
        notifyChange();
    }

    /**
     * @return Whether to search vehicle model
     */
    public boolean getSearchModel() {
        return searchModel;
    }

    /**
     * Set whether to search the vehicle model
     *
     * @param searchModel Whether to search the vehicle model
     */
    public void setSearchModel(boolean searchModel) {
        this.searchModel = searchModel;
        notifyChange();
    }

    /**
     * @return Whether to group by year
     */
    public boolean getGroupByYear() {
        return groupByYear;
    }

    /**
     * Set whether to group by year
     *
     * @param groupByYear Whether to group by year
     */
    public void setGroupByYear(boolean groupByYear) {
        this.groupByYear = groupByYear;
        if (groupByYear) {
            groupByMake = false;
        }
        notifyChange();
    }

    /**
     * @return Whether to group by make
     */
    public boolean getGroupByMake() {
        return groupByMake;
    }

    /**
     * Set whether to group by make
     *
     * @param groupByMake Whether to group by make
     */
    public void setGroupByMake(boolean groupByMake) {
        this.groupByMake = groupByMake;
        if (groupByMake) {
            groupByYear = false;
        }
        notifyChange();
    }

    /**
     * @return Whether to reverse groups
     */
    public boolean getReverseGroups() {
        return reverseGroups;
    }

    /**
     * Sets whether to reverse groups
     *
     * @param reverseGroups Whether to reverse groups
     */
    public void setReverseGroups(boolean reverseGroups) {
        this.reverseGroups = reverseGroups;
        notifyChange();
    }

    /**
     * @return Whether to group results
     */
    public boolean shouldGroupResults() {
        return groupByMake || groupByYear;
    }

    /**
     * @return The enum set of query types selected
     */
    public EnumSet<RetrieveVehicleListEvent.QueryType> getQueryTypes() {
        EnumSet<RetrieveVehicleListEvent.QueryType> queryTypes =
                  EnumSet.allOf(RetrieveVehicleListEvent.QueryType.class);

        if (!searchYear) {
            queryTypes.remove(RetrieveVehicleListEvent.QueryType.YEAR);
        }
        if (!searchMake) {
            queryTypes.remove(RetrieveVehicleListEvent.QueryType.MAKE);
        }
        if (!searchModel) {
            queryTypes.remove(RetrieveVehicleListEvent.QueryType.MODEL);
        }

        return queryTypes;
    }
}
