package com.example.petapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoadingManager {

    private Dialog loadingDialog;
    private ImageView ivLoading;

    private Activity activity;

    public LoadingManager(Activity activity) {
        this.activity = activity;
        initLoadingDialog();
    }

    private void initLoadingDialog() {
        loadingDialog = new Dialog(activity, R.style.LoadingDialogStyle);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.layout_loading, null);
        ivLoading = dialogView.findViewById(R.id.ivLoading);
        loadingDialog.setContentView(dialogView);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    public void showLoading(Activity activity) {
        if (!loadingDialog.isShowing()) {
            ivLoading.setVisibility(View.VISIBLE);
            //loadingDialog.show();
        }
        //loadingDialog.show();

        // 获取 ImageView
        ImageView ivLoading = loadingDialog.findViewById(R.id.ivLoading);

        Glide.with(activity).load(R.drawable.loading_animation).into(ivLoading);

        loadingDialog.show();
    }

    public void hideLoading() {
        if (loadingDialog.isShowing()) {
            //ivLoading.setVisibility(View.GONE);
            loadingDialog.dismiss();
        }
    }
}
