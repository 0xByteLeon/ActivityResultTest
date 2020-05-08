package com.leon.android.actresult

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.ref.WeakReference
import java.util.*

/**
 * @time:2020/05/08 10:00
 * @author:Leon
 * @description: 发送结果包装类
 */
class ActivityResult<T>(
    val data: T?,
    val code: Int = Activity.RESULT_OK,
    val message: String = ""
) {
    fun isSucceed() = code == Activity.RESULT_OK

    fun isFailed() = code == Activity.RESULT_CANCELED

    companion object {
        fun <T> toSucceed(data: T?): ActivityResult<T> {
            return ActivityResult(
                data = data,
                code = Activity.RESULT_OK
            )
        }

        fun <T> toFailed(message: String): ActivityResult<T> {
            return ActivityResult(
                data = null,
                code = Activity.RESULT_CANCELED,
                message = message
            )
        }
    }
}

private val activityResultMap = hashMapOf<String, WeakReference<((ActivityResult<*>) -> Unit)>>()


/*
* 调用此方法启动Activity
* */
fun <T> Activity.startForResult(intent: Intent, onResult: ((ActivityResult<T>) -> Unit)) {
    val uuid = UUID.randomUUID().toString()
    activityResultMap[uuid] = WeakReference(onResult as (ActivityResult<*>) -> Unit)
    intent.putExtra("startForResultRequestCode", uuid)
    this.startActivity(intent)
}

private val activityResultMapLV = hashMapOf<String, MutableLiveData<ActivityResult<*>>>()

/*
* 调用此方法启动Activity
* */
fun <T> Activity.startForResult(intent: Intent): LiveData<ActivityResult<T>> {
    val uuid = UUID.randomUUID().toString()
    val liveData = MutableLiveData<ActivityResult<T>>()
    activityResultMapLV[uuid] = liveData as MutableLiveData<ActivityResult<*>>
    intent.putExtra("startForResultRequestCode", uuid)
    this.startActivity(intent)
    return liveData
}

/*
* 调用此方法发送结果
* */
fun <T> Activity.sendResult(result: ActivityResult<T>): Boolean {
    var callSucceed = false
    val startForResultRequestCode = intent.getStringExtra("startForResultRequestCode")
    startForResultRequestCode?.let { requestCode ->
        activityResultMapLV[requestCode]?.let {
            it.postValue(result)
            callSucceed = true
        }
        activityResultMap[requestCode]?.let {
            it.get()?.invoke(result)
            callSucceed = true
        }
    }
    return callSucceed
}

fun <T> Fragment.startForResult(intent: Intent): LiveData<ActivityResult<T>> {
    val uuid = UUID.randomUUID().toString()
    val liveData = MutableLiveData<ActivityResult<T>>()
    activityResultMapLV[uuid] = liveData as MutableLiveData<ActivityResult<*>>
    intent.putExtra("startForResultRequestCode", uuid)
    this.startActivity(intent)
    return liveData
}

fun <T> Fragment.sendResult(result: ActivityResult<T>): Boolean {
    var callSucceed = false
    val startForResultRequestCode = activity?.intent?.getStringExtra("startForResultRequestCode")
    startForResultRequestCode?.let { requestCode ->
        activityResultMapLV[requestCode]?.let {
            it.postValue(result)
            callSucceed = true
        }
        activityResultMap[requestCode]?.let {
            it.get()?.invoke(result)
            callSucceed = true
        }
    }
    return callSucceed
}

val activityResultLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        val requestCode = getRequestCode(activity)
        activityResultMapLV.remove(requestCode)
        activityResultMap.remove(requestCode)
        Log.d("ResultLifecycle", "${activityResultMapLV.size}")
    }

    private fun getRequestCode(activity: Activity): String? {
        return activity.intent.getStringExtra("startForResultRequestCode")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }
}