package edu.hanu.mybookmarks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebViewFragment extends Fragment {

    Button btnBack;
    WebView wvMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

//      get ref
        btnBack = view.findViewById(R.id.btn_back);
        wvMain = view.findViewById(R.id.wv_main);

//        get link
        String link = getArguments().getString("URL");

//        redirect to link
        wvMain.setWebViewClient(new WebViewClient());

//        enable js
        WebSettings settings = wvMain.getSettings();
        settings.setJavaScriptEnabled(true);

        wvMain.loadUrl(link);

//        handle back
        btnBack.setOnClickListener(v -> {
            FragmentManager manager = getFragmentManager();
            manager.popBackStack();
        });

        return view;
    }
}