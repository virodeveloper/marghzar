package pick.com.app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import pick.com.app.R;
import pick.com.app.base.model.ToolbarCustom;
import pick.com.app.databinding.FragmentWebviewBinding;

public class WebviewFragment extends Fragment {

    private static final String URL = "url";
    private static final String TITLE = "title";
    private ToolbarCustom toolbarCustom;
    private FragmentWebviewBinding binding;
    private String urlToLoad;

    public static WebviewFragment getInstance(String mUrl) {
        WebviewFragment overviewFragment = new WebviewFragment();
        Bundle args = new Bundle();
        args.putString(URL, mUrl);
        overviewFragment.setArguments(args);
        return overviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = DataBindingUtil.bind(getView());
        if(null != getArguments()){
            urlToLoad = getArguments().getString(URL);
        }
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.loadUrl(urlToLoad);
        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
