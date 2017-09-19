package rocks.teagantotally.blinkercarbrowser.common;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by tglenn on 9/19/17.
 */

public abstract class UITestUtil {
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void waitForUiToSettle() throws
                                           InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();

                return null;
            }
        };

        latch.await(1,
                    TimeUnit.SECONDS);
    }
}