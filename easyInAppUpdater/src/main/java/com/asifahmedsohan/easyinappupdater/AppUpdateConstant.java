package com.asifahmedsohan.easyinappupdater;

import com.google.android.play.core.install.model.AppUpdateType;

/**
 * Created by Asif Ahmed Sohan on 2/1/21
 * Copyright (c) 2021 to Asif Ahmed Sohan. All rights reserved.
 */
public class AppUpdateConstant {

    public static final int APP_UPDATE_TYPE_FLEXIBLE = AppUpdateType.FLEXIBLE;
    public static final int APP_UPDATE_TYPE_IMMEDIATE = AppUpdateType.IMMEDIATE;
    public static final int APP_UPDATE_REQUEST_CODE = 1248;
    public static final int APP_UPDATE_REQUEST_CODE_FOR_FRAGMENT = 1359;


    public enum UpdateMode {
        FLEXIBLE(0),
        IMMEDIATE(1);

        private long value;

        UpdateMode(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }
}
