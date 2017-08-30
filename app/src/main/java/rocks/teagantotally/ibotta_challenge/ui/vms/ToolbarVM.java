package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.databinding.BaseObservable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by tglenn on 8/30/17.
 */

public class ToolbarVM
          extends BaseObservable {
    @DrawableRes
    private int logo;
    @StringRes
    private int titleResourceId;
    private String title;
    @ColorRes
    private int titleTextColor;
    @StringRes
    private int subtitle;
    @ColorRes
    private int subtitleTextColor;
    @DrawableRes
    private int background;
    @DrawableRes
    private int navigationIcon;
    private View.OnClickListener navigationClickListener;
    @DrawableRes
    private int overflowIcon;
    @MenuRes
    private int menuResourceId;
    private Toolbar.OnMenuItemClickListener menuItemClickListener;
    @ColorRes
    private int menuItemColorResourceId;
    private Map<Integer, Boolean> menuItemAvailabilityMap = new HashMap<>();
    private Map<Integer, Integer> menuItemDrawableMap = new HashMap<>();
    private View.OnClickListener onClickListener;

    @DrawableRes
    public int getLogo() {
        return logo;
    }

    public void setLogo(@DrawableRes int logo) {
        this.logo = logo;
        notifyChange();
    }

    @StringRes
    public int getTitleResourceId() {
        return titleResourceId;
    }

    public void setTitleResourceId(@StringRes int titleResourceId) {
        if (this.titleResourceId == titleResourceId) {
            return;
        }
        this.titleResourceId = titleResourceId;
        notifyChange();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (TextUtils.equals(this.title,
                             title)) {
            return;
        }
        this.title = title;
        notifyChange();
    }

    @ColorRes
    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(@ColorRes int titleTextColor) {
        if (this.titleTextColor == titleTextColor) {
            return;
        }
        this.titleTextColor = titleTextColor;
        notifyChange();
    }

    @StringRes
    public int getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@StringRes int subtitle) {
        if (this.subtitle == subtitle) {
            return;
        }
        this.subtitle = subtitle;
        notifyChange();
    }

    @ColorRes
    public int getSubtitleTextColor() {
        return subtitleTextColor;
    }

    public void setSubtitleTextColor(@ColorRes int subtitleTextColor) {
        if (this.subtitleTextColor == subtitleTextColor) {
            return;
        }
        this.subtitleTextColor = subtitleTextColor;
        notifyChange();
    }

    @DrawableRes
    public int getBackground() {
        return background;
    }

    public void setBackground(@DrawableRes int background) {
        if (this.background == background) {
            return;
        }
        this.background = background;
        notifyChange();
    }

    @DrawableRes
    public int getNavigationIcon() {
        return navigationIcon;
    }

    public void setNavigationIcon(@DrawableRes int navigationIcon) {
        if (this.navigationIcon == navigationIcon) {
            return;
        }
        this.navigationIcon = navigationIcon;
        notifyChange();
    }

    public View.OnClickListener getNavigationClickListener() {
        return navigationClickListener;
    }

    public void setNavigationClickListener(View.OnClickListener navigationClickListener) {
        if (Objects.equals(this.navigationClickListener,
                           navigationClickListener)) {

            return;
        }
        this.navigationClickListener = navigationClickListener;
        notifyChange();
    }

    @MenuRes
    public int getMenuResourceId() {
        return menuResourceId;
    }

    public void setMenuResourceId(@MenuRes int menuResourceId) {
        if (this.menuResourceId == menuResourceId) {
            return;
        }
        this.menuResourceId = menuResourceId;
        notifyChange();
    }

    public Toolbar.OnMenuItemClickListener getMenuItemClickListener() {
        return menuItemClickListener;
    }

    public void setMenuItemClickListener(Toolbar.OnMenuItemClickListener menuItemClickListener) {
        if (Objects.equals(this.menuItemClickListener,
                           menuItemClickListener)) {
            return;
        }
        this.menuItemClickListener = menuItemClickListener;
        notifyChange();
    }

    @DrawableRes
    public int getOverflowIcon() {
        return overflowIcon;
    }

    public void setOverflowIcon(@DrawableRes int overflowIcon) {
        this.overflowIcon = overflowIcon;
        notifyChange();
    }

    @ColorRes
    public int getMenuItemColorResourceId() {
        return menuItemColorResourceId;
    }

    public void setMenuItemColorResourceId(@ColorRes int menuItemColorResourceId) {
        if (this.menuItemColorResourceId == menuItemColorResourceId) {
            return;
        }
        this.menuItemColorResourceId = menuItemColorResourceId;
        notifyChange();
    }

    public Set<Integer> getMenuItemsWithAvailabilitySet() {
        return menuItemAvailabilityMap.keySet();
    }

    public boolean isMenuItemEnabled(@IdRes int menuItemId) {
        if (!menuItemAvailabilityMap.containsKey(menuItemId)) {
            return true;
        }

        return menuItemAvailabilityMap.get(menuItemId);
    }

    public void disableMenuItem(@IdRes int menuItemId) {
        menuItemAvailabilityMap.put(menuItemId,
                                    false);
        notifyChange();
    }

    public void enableMenuItem(@IdRes int menuItemId) {
        menuItemAvailabilityMap.put(menuItemId,
                                    true);
        notifyChange();
    }

    public void setMenuItemDrawable(@IdRes int menuItemId,
                                    @DrawableRes int drawable) {
        menuItemDrawableMap.put(menuItemId,
                                drawable);
        notifyChange();
    }

    @DrawableRes
    public int getMenuItemDrawable(@IdRes int menuItemId) {
        return menuItemDrawableMap.get(menuItemId);
    }

    public Set<Integer> getMenuItemsWithDrawableSet() {
        return menuItemDrawableMap.keySet();
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        if (Objects.equals(this.onClickListener,
                           onClickListener)) {
            return;
        }
        this.onClickListener = onClickListener;
        notifyChange();
    }
}
