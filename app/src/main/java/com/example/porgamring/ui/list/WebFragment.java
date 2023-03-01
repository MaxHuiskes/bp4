package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.databinding.FragmentWebBinding;

public class WebFragment<ListArray> extends Fragment {

    private FragmentWebBinding binding;
    private WebView webview;
    private Button back;
    private Button forward;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WebViewModel homeViewModel =
                new ViewModelProvider(this).get(WebViewModel.class);

        binding = FragmentWebBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        webview = binding.webView;
        back = binding.back;
        back.setText("<");
        forward = binding.forward;
        forward.setText(">");

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClient webViewClient = new WebViewClient();
        webview.setWebViewClient(webViewClient);
        webview.canGoBackOrForward(10);


        webview.loadUrl("https://www.google.com");

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webview.canGoForward()) {
                    webview.goForward();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webview.canGoBack()) {
                    webview.goBack();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}