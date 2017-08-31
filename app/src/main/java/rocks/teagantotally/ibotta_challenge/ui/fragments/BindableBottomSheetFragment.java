package rocks.teagantotally.ibotta_challenge.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by teaganglenn on 7/10/17.
 */

public class BindableBottomSheetFragment
          extends BottomSheetDialogFragment {
    @LayoutRes int layoutResourceIdentifier;
    int bindingVariableIdentifier;
    Object viewModel;
    ViewDataBinding binding;

    public int getLayoutResourceIdentifier() {
        return layoutResourceIdentifier;
    }

    public void setLayoutResourceIdentifier(int layoutResourceIdentifier) {
        this.layoutResourceIdentifier = layoutResourceIdentifier;
    }

    public int getBindingVariableIdentifier() {
        return bindingVariableIdentifier;
    }

    public void setBindingVariableIdentifier(int bindingVariableIdentifier) {
        this.bindingVariableIdentifier = bindingVariableIdentifier;
    }

    public Object getViewModel() {
        return viewModel;
    }

    public void setViewModel(Object viewModel) {
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
