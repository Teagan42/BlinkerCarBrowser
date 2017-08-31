package rocks.teagantotally.ibotta_challenge.datastore.json;

import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.RetailerDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Store;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetailerJsonDataStore
          implements RetailerDataStore {

    @Inject
    Context context;
    @Inject
    EventBus eventBus;

    @Inject
    public RetailerJsonDataStore(Context context,
                                 EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    private List<Retailer> loadJson() throws
                                      IOException {
        Retailer[] retailers = JsonHelper.loadJson(context,
                                                   R.raw.stores,
                                                   Retailer[].class);
        return Arrays.asList(retailers);
    }

    @Override
    public List<Retailer> getRetailers(int offset,
                                       int limit) {
        try {
            List<Retailer> retailers = loadJson();

            return retailers.subList(offset,
                                     offset + limit);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Retailer getRetailerById(long id) {
        List<Retailer> retailers = null;
        try {
            retailers = loadJson();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (Retailer retailer : retailers) {
            if (retailer.id == id) {
                return retailer;
            }
        }

        return null;
    }

    @Override
    public Retailer getRetailerByName(String name) {
        List<Retailer> retailers;

        try {
            retailers = loadJson();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (Retailer retailer : retailers) {
            if (TextUtils.equals(retailer.name,
                                 name)) {
                return retailer;
            }
        }

        return null;
    }
}
