package com.leon.android.actresult.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.leon.android.actresult.R
import com.leon.android.actresult.startForResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startForLVBtn.setOnClickListener {
            startForResult<Int>(Intent(this, StartForResultByLVActivity::class.java)).observe(this, Observer {
                resultValTV.append("Send Result by LiveDate : ${it.data} \n")
                toast("Send Result by LiveDate : ${it.data}")
            })
        }

        startForResultBtn.setOnClickListener {
            startForResult<Int>(Intent(this,
                StartForResultByCallbackActivity::class.java)) {
                if (it.isSucceed()){
                    resultValTV.append("Send Result by Callback : ${it.data} \n")
                    toast("Send Result by Callback : ${it.data}")
                } else {
                    resultValTV.append("Send Result by Callback : ${it.data} \n")
                    toast("Send Result by Callback : Failed !")
                }
            }
        }
    }

    fun toast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
