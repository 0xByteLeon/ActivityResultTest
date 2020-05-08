package com.leon.android.actresult.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leon.android.actresult.ActivityResult
import com.leon.android.actresult.R
import com.leon.android.actresult.sendResult
import kotlinx.android.synthetic.main.activity_main2.*

class StartForResultByLVActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var i = 1
        button.setOnClickListener {
            sendResult(
                ActivityResult<Int>(
                    data = i++,
                    code = if (i % 2 == 0) Activity.RESULT_OK else Activity.RESULT_CANCELED
                )
            )
        }
    }
}
