package com.cesar.cesarcivitatis.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.cesar.cesarcivitatis.R;
import com.cesar.cesarcivitatis.databinding.DialogWebViewBinding;

import org.jetbrains.annotations.NotNull;


/**
 * Created by cesar on 12/3/2018.
 */

public class WebViewDialog extends DialogFragment {

    private String url;

    boolean loadingFinished = true;
    boolean redirect = false;
    private DialogWebViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @NotNull LayoutInflater inflate= getActivity().getLayoutInflater();
        binding = DialogWebViewBinding.inflate(inflate);
        builder.setView(binding.getRoot());


        initWebView();

        binding.civClose.setOnClickListener(view -> WebViewDialog.this.dismiss());

        return builder.create();
    }

    private void initWebView() {
        if(url.isEmpty()){
            dismiss();
        }else{
            Log.i("--->initWebView", ": "+url);
            binding.webview.getSettings().setJavaScriptEnabled(true);

            binding.webview.loadUrl(url);

            showProgress(true);

            binding.webview.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view, WebResourceRequest request) {
                    if (!loadingFinished) {
                        redirect = true;
                    }

                    loadingFinished = false;
                    binding.webview.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(
                        WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    loadingFinished = false;
                    //SHOW LOADING IF IT ISNT ALREADY VISIBLE
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (!redirect) {
                        loadingFinished = true;
                    }

                    if (loadingFinished && !redirect) {
                        showProgress(false);
                    } else {
                        redirect = false;
                    }
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    Log.i("--->initWebView", "onReceivedSslError: "+url);
                    handler.proceed();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void showProgress(boolean control) {
        binding.progress.setVisibility(control? View.VISIBLE: View.GONE);
    }
}