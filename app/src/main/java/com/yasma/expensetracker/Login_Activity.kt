package com.yasma.expensetracker

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Login_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth=FirebaseAuth.getInstance()

        val sin_up=findViewById<TextView>(R.id.sign_up)
        val name = getColoredSpanned("Not Registered Yet,", "#111111")
        val surName = getColoredSpanned("Sign Up !", "#DF2E38")
        sin_up.text = Html.fromHtml(name+"<b>"+surName+"</b>")

        sin_up.setOnClickListener {
            val intent = Intent(this, SignUp_Activity::class.java)
            startActivity(intent)
        }

        val forget=findViewById<TextView>(R.id.password_fog)
        forget.setOnClickListener(){
            forget_passowrd()
        }

        val email=findViewById<EditText>(R.id.emailEt)
        val pass=findViewById<EditText>(R.id.passET)

        val log_in=findViewById<Button>(R.id.log_in)
        log_in.setOnClickListener {
            val em=email.text.toString()
            val pas=pass.text.toString()
            if(em.isNotEmpty() && pas.isNotEmpty() ){


                    firebaseAuth.signInWithEmailAndPassword(em,pas).addOnCompleteListener{
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {

                            val errormsg=findViewById<TextView>(R.id.error_msg)

                            errormsg.isVisible=true
                            val exception = it.exception

                            when (exception) {
                                is FirebaseAuthInvalidUserException -> {
                                    // Invalid user (user doesn't exist)
                                    errormsg.text="User not exists, Please Signup ..!"

                                }
                                is FirebaseAuthInvalidCredentialsException -> {
                                    // Invalid credentials (wrong email or password)
                                    errormsg.text="Incorrect Email or Password"
                                }

                                else -> {
                                    // Other authentication exceptions
                                    Toast.makeText(this, "Please check internet connectivity", Toast.LENGTH_SHORT).show()
                                }



                            }








//                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }

                } else{
                    val errormsg=findViewById<TextView>(R.id.error_msg)
                     errormsg.text="Empty field not allowed"
                     errormsg.isVisible=true

            }

        }


    }

    private fun forget_passowrd() {
        val intent = Intent(this, forget_passowrd::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
}