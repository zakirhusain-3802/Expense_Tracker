package com.yasma.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth=FirebaseAuth.getInstance()

        val sin_up=findViewById<TextView>(R.id.sign_up)
        sin_up.setOnClickListener(){
            val intent = Intent(this, SignUp_Activity::class.java)
            startActivity(intent)
        }

        val email=findViewById<EditText>(R.id.emailEt)
        val pass=findViewById<EditText>(R.id.passET)

        val log_in=findViewById<Button>(R.id.log_in)
        log_in.setOnClickListener(){
            val em=email.text.toString()
            val pas=pass.text.toString()
            if(em.isNotEmpty() && pas.isNotEmpty() ){


                    firebaseAuth.signInWithEmailAndPassword(em,pas).addOnCompleteListener{
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }

                } else{
                Toast.makeText(this,"Empty field not Allowed", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}