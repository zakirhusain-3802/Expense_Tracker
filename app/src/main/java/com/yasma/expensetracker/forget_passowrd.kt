package com.yasma.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class forget_passowrd : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_passowrd)

        auth = FirebaseAuth.getInstance()
        val resetPasswordButton=findViewById<Button>(R.id.forget_password)

        resetPasswordButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEtF).text.trim().toString()

            if (email.isEmpty()) {
                val errormsg=findViewById<TextView>(R.id.error_msgPass)
                errormsg.text="Empty field not allowed"
                errormsg.isVisible=true
            } else {
                resetPassword(email.toString())
            }
        }
    }
    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    val errormsg=findViewById<TextView>(R.id.error_msgPass)
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        // User not found exception
                        errormsg.text="User not exists. Please go back and SignUp"
                        errormsg.isVisible=true

                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                        // Invalid email exception
                        errormsg.text="Invalid email"
                        errormsg.isVisible=true

                    } else {
                        // General exception
                        errormsg.text="Failed to send password reset email.\nPlease check internet connectivity"
                        errormsg.isVisible=true

                    }
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Login_Activity::class.java)
        startActivity(intent)
    }
}