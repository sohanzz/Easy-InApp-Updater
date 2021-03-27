[![](https://jitpack.io/v/sohanzz/Easy-InApp-Updater.svg)](https://jitpack.io/#sohanzz/Easy-InApp-Updater)


<p align="center">
  <img src="https://github.com/sohanzz/Easy-InApp-Updater/blob/master/logo.png" alt="logo" width="520" height="280" class="center" />
</p>



<h1 align="center">Easy In-App Updater</h1>
<p align="center">Android Library to easily implement <a href="https://developer.android.com/guide/app-bundle/in-app-updates">in-app updates</a></p>

## :pencil2: Installation

### Step 1: Add it in your root build.gradle
```Gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
### Step 2: Add the dependency
```Gradle
dependencies {
    implementation 'com.github.sohanzz:Easy-InApp-Updater:v1.0'
}
```

### Step 3: Initialize the InAppUpdateManager

Initialize the UpdateManager in your `onCreate` method of the Activity 
```java
  
    InAppUpdateManager updateManager = new InAppUpdateManager(this, AppUpdateConstant.APP_UPDATE_REQUEST_CODE);
    
    //You can set your preferred update mode using API data.
    updateManager.checkAppUpdate(AppUpdateConstant.APP_UPDATE_TYPE_FLEXIBLE);
    OR
    updateManager.checkAppUpdate(AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE);

```
### Step 3: override onActivityResult method
We need to override `onActivityResult` to detect if the user cancelled the process since the immediate screen can be canceled 

```java
    if (requestCode == AppUpdateConstant.APP_UPDATE_REQUEST_CODE) {
            AppUpdateUtil.showAppUpdateAlert(this, resultCode);
        }
```



**Update Mode**

* **Flexible**: A user experience that provides background download and installation with graceful state monitoring. This UX is appropriate when it’s acceptable for the user to use the app while downloading the update. For example, you want to urge users to try a new feature that’s not critical to the core functionality of your app.

* **Immediate**: A full screen user experience that requires the user to update and restart the app in order to continue using the app. This UX is best for cases where an update is critical for continued use of the app. After a user accepts an immediate update, Google Play handles the update installation and app restart.

### Trigger update from other activity/Fragment


* Call `AppUpdateListener` in your button onClickListener method of the `Fragment/Activity`

```java
  ((AppUpdateListener) Objects.requireNonNull(getActivity())).onAppUpdateClicked();

```

* Implement `AppUpdateListener` in your `MainActivity` and `@Override onAppUpdateClicked` method

```java

        InAppUpdateManager updateManager = new InAppUpdateManager(this, AppUpdateConstant.APP_UPDATE_REQUEST_CODE);
        updateManager.checkAppUpdate(AppUpdateConstant.APP_UPDATE_TYPE_FLEXIBLE);
```


## :page_facing_up: License
```
MIT License

Copyright (c) 2021 Asif Ahmed Sohan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
