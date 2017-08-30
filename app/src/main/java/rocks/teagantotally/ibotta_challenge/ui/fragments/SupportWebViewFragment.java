package rocks.teagantotally.ibotta_challenge.ui.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.ui.vms.ToolbarVM;

/**
 * Created by tglenn on 8/30/17.
 */

public class SupportWebViewFragment
          extends BaseFragment
          implements View.OnKeyListener {

    private static final String TAG = "SupportWebViewFragment";

    public static final String URL = "url";

    private WebView webView;
    private boolean isWebViewAvailable;
    private ToolbarVM toolbar;
    private String title;
    private String url;

    @Override
    protected ToolbarVM getToolbarViewModel() {
        if (toolbar != null) {
            return toolbar;
        }
        toolbar = new ToolbarVM();
        toolbar.setNavigationIcon(R.drawable.close);
        toolbar.setNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        return toolbar;
    }

    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (webView != null) {
            webView.destroy();
        }
        webView = new WebView(getContext());
        webView.setFitsSystemWindows(true);
        isWebViewAvailable = true;
        init();
        return webView;
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        isWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return isWebViewAvailable
               ? webView
               : null;
    }

    private void init() {
        getWebView().setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view,
                                       String url) {
                super.onPageFinished(view,
                                     url);
                toolbar.setTitle(view.getTitle());

                applyToolbars();
            }
        });
        getWebView().setOnKeyListener(this);
        getWebView().loadUrl(getArguments().getString(URL));
    }

    @Override
    public boolean onKey(View v,
                         int keyCode,
                         KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getWebView().canGoBack()) {
            getWebView().goBack();
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getRoute() {
        return url;
    }
}
