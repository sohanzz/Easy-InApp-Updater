package com.asifahmedsohan.easyinappupdater;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

/**
 * Created by Asif Ahmed Sohan on 2/3/21
 * Copyright (c) 2021 to Asif Ahmed Sohan. All rights reserved.
 */
public class InAppUpdateStatus {

    private AppUpdateInfo appUpdateInfo;
    private InstallState installState;

    public InAppUpdateStatus() {
    }

    public void setAppUpdateInfo(AppUpdateInfo appUpdateInfo) {
        this.appUpdateInfo = appUpdateInfo;
    }

    public void setInstallState(InstallState installState) {
        this.installState = installState;
    }

    public boolean isUpdateAvailable() {
        return appUpdateInfo != null && appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE;

    }

    public boolean isUpdateInProgress() {
        return appUpdateInfo != null && appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS;
    }

    public boolean isUpdateAllowed(int updateType) {
        return appUpdateInfo != null && appUpdateInfo.isUpdateTypeAllowed(updateType);
    }

    public boolean isDownloading() {
        return installState != null && installState.installStatus() == InstallStatus.DOWNLOADING;

    }

    public boolean isDownloaded() {
        return installState != null && installState.installStatus() == InstallStatus.DOWNLOADED;

    }

    public boolean isInstalled() {
        return installState != null && installState.installStatus() == InstallStatus.INSTALLED;

    }
}

