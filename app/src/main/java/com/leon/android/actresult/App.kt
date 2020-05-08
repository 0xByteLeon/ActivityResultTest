package com.leon.android.actresult

import android.app.Application

/**
 * @time:2020/5/8 9:20
 * @author:Leon
 * @description:
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(activityResultLifecycleCallbacks)
    }
}