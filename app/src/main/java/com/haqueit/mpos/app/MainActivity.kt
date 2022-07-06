package com.haqueit.mpos.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.haqueit.mpos.app.cardinfo.ReadCardActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnReadCardInfo:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnReadCardInfo = findViewById(R.id.btnReadCardInfo)

        btnReadCardInfo.setOnClickListener {
            var intent = Intent(this, ReadCardActivity::class.java)
            startActivity(intent)
        }
    }
}