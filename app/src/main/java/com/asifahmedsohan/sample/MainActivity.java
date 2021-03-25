package com.asifahmedsohan.sample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.asifahmedsohan.easyinappupdater.AppUpdateConstant;
import com.asifahmedsohan.easyinappupdater.AppUpdateUtil;
import com.asifahmedsohan.easyinappupdater.InAppUpdateManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InAppUpdateManager updateManager = new InAppUpdateManager(this, AppUpdateConstant.APP_UPDATE_REQUEST_CODE);
        updateManager.checkAppUpdate(AppUpdateConstant.APP_UPDATE_TYPE_FLEXIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppUpdateConstant.APP_UPDATE_REQUEST_CODE) {
            AppUpdateUtil.showAppUpdateAlert(this, resultCode);
        }
    }
}