package com.yasma.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUp_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val sign_up=findViewById<Button>(R.id.sign_up)

        sign_up.setOnClickListener(){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}