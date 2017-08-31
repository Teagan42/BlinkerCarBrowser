package rocks.teagantotally.ibotta_challenge.ui.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.ui.utils.ScreenUtil;
import rocks.teagantotally.ibotta_challenge.ui.views.LoadingView;

/**
 * Created by tglenn on 8/31/17.
 */

public abstract class ImageViewBindingAdapters {
    private static final String TAG = "ImageViewBindingAdapter";
    private static final int KEY_URL = -500;
    private static final int KEY_WIDTH = -501;
    private static final int KEY_HEIGHT = -502;
    private static final int KEY_FIRST_LOAD = -503;

    private static final int NOT_SET = 0;

    @BindingAdapter({"drawable"})
    public static void setDrawable(ImageView view,
                                   @DrawableRes int drawableResourceId) {
        if (drawableResourceId == NOT_SET) {
            return;
        }
        view.setImageDrawable(ContextCompat.getDrawable(view.getContext(),
                                                        drawableResourceId));
    }

    @BindingAdapter({"imageUrl",
                     "imageWidth",
                     "imageHeight"})
    public static void loadImageUrl(
              ImageView view,
              String imageUrl,
              int width,
              int height) {
        loadImageUrl(view,
                     imageUrl,
                     null,
                     width,
                     height);
    }

    @BindingAdapter({"imageUrl",
                     "placeholder",
                     "imageWidth",
                     "imageHeight"})
    public static void loadImageUrl(
              ImageView view,
              String imageUrl,
              Drawable placeholder,
              int width,
              int height) {
        Integer existingWidth = (Integer) view.getTag(KEY_WIDTH);
        Integer existingHeight = (Integer) view.getTag(KEY_HEIGHT);
        Boolean firstLoad = (Boolean) view.getTag(KEY_FIRST_LOAD);
        String existingUrl = (String) view.getTag(KEY_URL);
        boolean loadImage = false;

        if (!Objects.equals(existingUrl,
                            imageUrl)
            || (existingHeight != null && existingHeight != height)
            || (existingWidth != null && existingWidth != width)) {
            loadImage = true;
        }

        if (loadImage) {
            loadImage(view,
                      imageUrl,
                      placeholder,
                      firstLoad == null,
                      ScreenUtil.convertDpToPx(view.getContext(),
                                               width),
                      ScreenUtil.convertDpToPx(view.getContext(),
                                               height));
            view.setTag(KEY_WIDTH,
                        width);
            view.setTag(KEY_HEIGHT,
                        height);
            view.setTag(KEY_URL,
                        imageUrl);
        }
    }

    private static LoadingView getLoaderViewParent(ImageView view) {
        ViewParent parent;
        View currentView = view;
        while ((parent = currentView.getParent()) != null) {
            if (parent instanceof LoadingView) {
                return (LoadingView) parent;
            }
            currentView = (View) parent;
        }

        return null;
    }

    private static void setLoading(LoadingView loadingView,
                                   boolean loading) {
        if (loadingView == null) {
            return;
        }

        loadingView.setLoading(loading);
    }

    private static void loadImage(final ImageView view,
                                  final String imageUrl,
                                  final Drawable placeholder,
                                  boolean firstLoad,
                                  int targetWidth,
                                  int targetHeight) {
        final LoadingView loaderView = getLoaderViewParent(view);
        if (firstLoad) {
            view.setImageDrawable(placeholder);
        }
        if (imageUrl != null) {
            Callback onComplete = new Callback() {
                @Override
                public void onSuccess() {
                    setLoading(loaderView,
                               false);
                }

                @Override
                public void onError() {
                    Log.w(TAG,
                          "onError: unable to load image: " + imageUrl);
                    setLoading(loaderView,
                               false);
                    if (placeholder == null) {
                        view.setImageResource(0);
                    } else {
                        view.setImageDrawable(placeholder);
                    }

                }
            };
            view.setImageResource(0);
            setLoading(loaderView,
                       true);

            if (placeholder == null) {
                Picasso.with(view.getContext())
                       .load(imageUrl)
                       .resize(targetWidth,
                               targetHeight)
                       .centerInside()
                       .priority(Picasso.Priority.HIGH)
                       .into(view,
                             onComplete);
            } else {
                Picasso.with(view.getContext())
                       .load(imageUrl)
                       .resize(targetWidth,
                               targetHeight)
                       .centerInside()
                       .priority(Picasso.Priority.HIGH)
                       .error(placeholder)
                       .placeholder(placeholder)
                       .into(view,
                             onComplete);
            }
        } else {
            setLoading(loaderView,
                       false);
            if (placeholder == null) {
                view.setImageResource(0);
            }
        }
    }
}
