package rocks.teagantotally.blinkercarbrowser.ui.binding;

import android.databinding.BindingAdapter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by tglenn on 9/18/17.
 */

public abstract class EditTextBindingAdapters {
    /**
     * Binds a text watcher to an edit text view
     *
     * @param view        View to bind to
     * @param textWatcher Text changed listener
     */
    @BindingAdapter({"textChangeListener"})
    public static void setTextWatcher(EditText view,
                                      TextWatcher textWatcher) {
        view.addTextChangedListener(textWatcher);
    }
}
