package rocks.teagantotally.ibotta_challenge.datastore.json;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.models.Store;
import rocks.teagantotally.ibotta_challenge.util.GsonUtil;

/**
 * Created by tglenn on 8/30/17.
 */

abstract class JsonHelper {
    static <T> T loadJson(Context context,
                          @RawRes int resourceId,
                          Class<T> clazz) throws
                                          IOException {
        InputStream is = context.getResources()
                                .openRawResource(R.raw.stores);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is,
                                                                     "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer,
                             0,
                             n);
            }
        } finally {
            is.close();
        }

        return GsonUtil.deserialize(writer.toString(),
                                    clazz);
    }
}
