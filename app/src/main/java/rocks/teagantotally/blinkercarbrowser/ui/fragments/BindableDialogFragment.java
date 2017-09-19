package rocks.teagantotally.blinkercarbrowser.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import rocks.teagantotally.blinkercarbrowser.ui.vms.BaseVM;

/**
 * Created by tglenn on 9/18/17.
 */

public class BindableDialogFragment
          extends DialogFragment {
    public static final String TAG = "BindableDialogFragment";

    @LayoutRes int layoutResourceIdentifier;
    int bindingVariableIdentifier;
    BaseVM viewModel;
    ViewDataBinding binding;

    public BindableDialogFragment(@LayoutRes @IntRange(from = 1) int layoutResourceIdentifier,
                                  @IntRange(from = 1) int bindingVariableIdentifier,
                                  @NonNull BaseVM viewModel) {
        Objects.requireNonNull(viewModel,
                               "View model cannot be null");
        if (layoutResourceIdentifier < 1) {
            throw new IllegalArgumentException("Layout resource id must be a positive integer");
        }
        if (bindingVariableIdentifier < 1) {
            throw new IllegalArgumentException("Binding variable id must be a positive integer");
        }

        this.layoutResourceIdentifier = layoutResourceIdentifier;
        this.bindingVariableIdentifier = bindingVariableIdentifier;
        this.viewModel = viewModel;
    }

    /**
     * @return The layout resource identifier to inflate
     */
    @LayoutRes
    public int getLayoutResourceIdentifier() {
        return layoutResourceIdentifier;
    }

    /**
     * Set the layout resource identifier to inflate
     *
     * @param layoutResourceIdentifier Layout resource identifier
     */
    public void setLayoutResourceIdentifier(int layoutResourceIdentifier) {
        this.layoutResourceIdentifier = layoutResourceIdentifier;
    }

    /**
     * @return The binding variable identifier to populate
     */
    public int getBindingVariableIdentifier() {
        return bindingVariableIdentifier;
    }

    /**
     * Set the binding variable identifier
     *
     * @param bindingVariableIdentifier The binding identifer to populate
     */
    public void setBindingVariableIdentifier(int bindingVariableIdentifier) {
        this.bindingVariableIdentifier = bindingVariableIdentifier;
    }

    /**
     * @return The view model
     */
    public BaseVM getViewModel() {
        return viewModel;
    }

    /**
     * Set the view model
     *
     * @param viewModel View model
     */
    public void setViewModel(BaseVM viewModel) {
        this.viewModel = viewModel;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (layoutResourceIdentifier == 0) {
            throw new IllegalStateException("No layout resource identifier specified");
        }
        if (bindingVariableIdentifier == 0) {
            throw new IllegalStateException("No binding variable specified");
        }
        if (viewModel == null) {
            throw new IllegalStateException("View model cannot be null");
        }
        binding = DataBindingUtil.inflate(inflater,
                                          layoutResourceIdentifier,
                                          container,
                                          false);
        binding.setVariable(bindingVariableIdentifier,
                            viewModel);

        return binding.getRoot();
    }
}
