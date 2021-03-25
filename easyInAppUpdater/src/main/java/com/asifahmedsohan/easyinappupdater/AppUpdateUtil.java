package com.asifahmedsohan.easyinappupdater;

import android.app.Activity;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Asif Ahmed Sohan on 2/3/21
 * Copyright (c) 2021 to Asif Ahmed Sohan. All rights reserved.
 */
public class AppUpdateUtil {

    public static void showAppUpdateAlert(Context context, int resultCode) {
        if (resultCode == RESULT_OK) {
            Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), "App download started..", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (resultCode == RESULT_CANCELED) {
            Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), "App download canceled.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
}

