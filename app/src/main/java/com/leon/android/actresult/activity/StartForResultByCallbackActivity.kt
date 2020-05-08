package com.leon.android.actresult.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leon.android.actresult.ActivityResult
import com.leon.android.actresult.R
import com.leon.android.actresult.sendResult
import kotlinx.android.synthetic.main.activity_start_for_result_by_callback.*

class StartForResultByCallbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_for_result_by_callback)
        var i = 1
        sendResultBtn.setOnClickListener {
            sendResult(
                ActivityResult(
                    data = i++,
                    code = if (i % 2 == 0) Activity.RESULT_OK else Activity.RESULT_CANCELED
                )
            )
        }
    }
}
