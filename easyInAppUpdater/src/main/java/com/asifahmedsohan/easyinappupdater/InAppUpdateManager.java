package com.asifahmedsohan.easyinappupdater;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.tasks.Task;

/**
 * Created by Asif Ahmed Sohan on 2/2/21
 * Copyright (c) 2021 to Asif Ahmed Sohan. All rights reserved.
 */

public class InAppUpdateManager implements LifecycleObserver {

    private static final String TAG = InAppUpdateManager.class.getSimpleName();


    private AppCompatActivity mActivityReference;
    private Context mContext;

    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener listener;
    private Task<AppUpdateInfo> appUpdateInfoTask = null;
    private InAppUpdateStatus inAppUpdateStatus = null;

    private AlertDialog alertDialog;
    private Lifecycle mLifecycle;
    private int appUpdateType;
    private int mRequestCode;

    public InAppUpdateManager(AppCompatActivity compatActivity, int requestCode) {
        mContext = compatActivity;
        mActivityReference = compatActivity;
        mRequestCode = requestCode;
        setLifecycleOwner(mActivityReference);
        init();
    }

    private void init() {
        setupAlertDialog();

        appUpdateManager = AppUpdateManagerFactory.create(mContext);
        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        inAppUpdateStatus = new InAppUpdateStatus();
    }

    /**
     * This method trigger in-app update for the latest available version
     * of your app in Google Playstore. You can set the app update type
     * here according to your requirements.
     *
     * @param updatePriority set app update type FLEXIBLE/IMMEDIATE
     */
    public void checkAppUpdate(int updatePriority) {
        try {

            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                inAppUpdateStatus.setAppUpdateInfo(appUpdateInfo);

                if ((inAppUpdateStatus.isUpdateAvailable() || inAppUpdateStatus.isUpdateInProgress()) && inAppUpdateStatus.isUpdateAllowed(updatePriority)) {
                    Log.d(TAG, "checkAppUpdate: App update available");
                    if (updatePriority == AppUpdateConstant.APP_UPDATE_TYPE_FLEXIBLE) {
                        listener = installState -> {
                            inAppUpdateStatus.setInstallState(installState);
                            if (inAppUpdateStatus.isDownloaded()) {
                                popupAlertDialogForUserConfirmation();
                            } else if (inAppUpdateStatus.isInstalled()) {
                                unRegisterListener();
                            }
                        };

                        appUpdateType = AppUpdateConstant.APP_UPDATE_TYPE_FLEXIBLE;
                        registerListener();
                        startAppUpdateForComponent(appUpdateInfo);
                    } else if (updatePriority == AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE) {
                        appUpdateType = AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE;
                        startAppUpdateForComponent(appUpdateInfo);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateForComponent(AppUpdateInfo appUpdateInfo) {
        try {
            Log.d(TAG, "startAppUpdate: starting app update");
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    appUpdateType,
                    mActivityReference,
                    mRequestCode);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void getUpdateInfo() {
        try {
            if (appUpdateInfoTask != null) {
                Log.d(TAG, "getUpdateInfo: App update resume..");
                appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                    inAppUpdateStatus.setAppUpdateInfo(appUpdateInfo);

                    if (appUpdateType == AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE) {
                        if (inAppUpdateStatus.isUpdateInProgress()) {
                            try {
                                appUpdateManager.startUpdateFlowForResult(
                                        appUpdateInfo,
                                        AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE,
                                        mActivityReference,
                                        mRequestCode);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (appUpdateType == AppUpdateConstant.APP_UPDATE_TYPE_FLEXIBLE) {
                        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                            popupAlertDialogForUserConfirmation();
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setupAlertDialog() {
        alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("App Update")
                .setMessage("Application successfully updated! You need to restart the app in order to use new features.")
                .setPositiveButton("Restart", (dialogInterface, i) -> {
                    if (appUpdateManager != null)
                        appUpdateManager.completeUpdate();
                })
                .create();
    }

    private void popupAlertDialogForUserConfirmation() {
        if (alertDialog != null) {
            alertDialog.show();

        }
    }

    private void registerListener() {
        if (appUpdateManager != null && listener != null)
            appUpdateManager.registerListener(listener);
    }

    private void unRegisterListener() {
        if (appUpdateManager != null && listener != null)
            appUpdateManager.unregisterListener(listener);

    }

    public void setLifecycleOwner(LifecycleOwner owner) {
        if (mLifecycle != null) mLifecycle.removeObserver(this);
        mLifecycle = owner.getLifecycle();
        mLifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        getUpdateInfo();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        unRegisterListener();
    }
}

