package rocks.teagantotally.ibotta_challenge.ui.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.databinding.ViewLoadingBinding;

/**
 * Created by tglenn on 8/31/17.
 */

public class LoadingView
          extends RelativeLayout {
    private static final String TAG = "LoadingView";
    private ViewLoadingBinding binding;
    private ArrayList<View> children = new ArrayList<>();

    public LoadingView(Context context) {
        super(context);
        initialize(null);
    }

    public LoadingView(Context context,
                       AttributeSet attrs) {
        super(context,
              attrs);
        initialize(attrs);
    }

    public LoadingView(Context context,
                       AttributeSet attrs,
                       int defStyleAttr) {
        super(context,
              attrs,
              defStyleAttr);
        initialize(attrs);
    }

    /**
     * Sets the loading property
     *
     * @param view      Loading view
     * @param isLoading Whether it is loading
     */
    @BindingAdapter("loading")
    public static void setLoading(LoadingView view,
                                  boolean isLoading) {
        view.setLoading(isLoading);
    }

    /**
     * Gets the loading property
     *
     * @param view Loading view
     * @return Whether the view is loading
     */
    @InverseBindingAdapter(attribute = "loading")
    public static boolean isLoading(LoadingView view) {
        return view.isLoading();
    }

    /**
     * @return Whether the view is loading
     */
    public boolean isLoading() {
        return this.binding.getIsLoading()
                           .get();
    }

    /**
     * Sets whether the view is loading
     *
     * @param isLoading Whether the view is loading
     */
    public void setLoading(boolean isLoading) {
        if (isLoading == this.binding.getIsLoading()
                                     .get()) {
            return;
        }
        this.binding.getIsLoading()
                    .set(isLoading);
        int visibility = isLoading
                         ? View.INVISIBLE
                         : View.VISIBLE;
        for (View child : children) {
            child.setVisibility(visibility);
        }
    }

    private void initialize(AttributeSet attrs) {
        boolean isLoading = false;
        ColorStateList color = null;
        if (attrs != null) {
            TypedArray typedAttributes =
                      getContext().obtainStyledAttributes(attrs,
                                                          R.styleable.LoadingView);
            isLoading =
                      typedAttributes.getBoolean(R.styleable.LoadingView_loading,
                                                 false);
            color = typedAttributes.getColorStateList(R.styleable.LoadingView_progressColor);
        }
        if (isInEditMode()) {
            inflate(getContext(),
                    R.layout.view_loading,
                    this);
            if (color != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ProgressBar) findViewById(R.id.progress))
                              .setIndeterminateTintList(color);
                }
            }
            findViewById(R.id.children).setVisibility(isLoading
                                                      ? View.INVISIBLE
                                                      : View.VISIBLE);
            findViewById(R.id.progress).setVisibility(isLoading
                                                      ? View.VISIBLE
                                                      : View.INVISIBLE);
            return;
        }
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                                          R.layout.view_loading,
                                          this,
                                          true);
        binding.setIsLoading(new ObservableBoolean(isLoading));
        if (color != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.progress.setIndeterminateTintList(color);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }

        View child;
        int i = 1;
        while ((child = getChildAt(i)) != null) {
            children.add(child);
            i++;
        }
        binding.progress.bringToFront();
    }
}
