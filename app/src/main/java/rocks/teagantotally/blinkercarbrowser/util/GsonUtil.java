package rocks.teagantotally.blinkercarbrowser.util;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.di.Injector;

/**
 * Created by tglenn on 8/30/17.
 */

public class GsonUtil {
    public static final String FORMAT_DATE =
              Injector.get()
                      .applicationContext()
                      .getString(R.string.date_time_format);
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(FORMAT_DATE);

    private static final Gson serializer = new GsonBuilder()
              .setDateFormat(FORMAT_DATE)
              .create();

    public static SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    public static String serialize(Object source) {
        return serializer.toJson(source);
    }

    public static Object deserialize(String json) {
        return deserialize(json,
                           Object.class);
    }

    public static <T> T deserialize(String json,
                                    Class<T> classOfT) {
        return serializer.fromJson(json,
                                   classOfT);
    }

    public static <T> T deserialize(String json,
                                    Type type) {
        return serializer.fromJson(json,
                                   type);
    }

    public static Gson getSerializer() {
        return serializer;
    }
}
