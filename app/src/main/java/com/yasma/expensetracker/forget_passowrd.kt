package com.yasma.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class forget_passowrd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_passowrd)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Login_Activity::class.java)
        startActivity(intent)
    }
}