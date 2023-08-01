package com.yasma.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SignUp_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth=FirebaseAuth.getInstance()

        val login=findViewById<TextView>(R.id.loin_in)
        val name = getColoredSpanned("Already Registered,", "#111111")
        val surName = getColoredSpanned("Sign In !", "#DF2E38")
        login.setText(Html.fromHtml(name+"<b>"+surName+"</b>"))


        login.setOnClickListener(){
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }
        val names=findViewById<EditText>(R.id.nameEt)
        val email=findViewById<EditText>(R.id.emailEt)
        val pass=findViewById<EditText>(R.id.passET)
        val conpass=findViewById<EditText>(R.id.confirmPassEt)

        val sign_up=findViewById<Button>(R.id.sign_up)

        sign_up.setOnClickListener(){
            val un=names.text.toString()
            val em=email.text.toString()
            val pas=pass.text.toString()
            val conpas=conpass.text.toString()

            if(em.isNotEmpty() && pas.isNotEmpty() && conpas.isNotEmpty() && un.isNotEmpty()){
                if (pas==conpas){

                    firebaseAuth.createUserWithEmailAndPassword(em,pas).addOnCompleteListener{
                        if (it.isSuccessful) {


                            FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.currentUser!!.uid).setValue(un).addOnCompleteListener(){
                                if (it.isSuccessful){
                                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                    firebaseAuth.signOut()
                                    val intent = Intent(this, Login_Activity::class.java)
                                    startActivity(intent)

                                }
                                else
                                {
                                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                                }
                            }
//                            firebaseAuth.signOut()
//
//                            val intent = Intent(this, Login_Activity::class.java)
//                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }

                }else{
                    Toast.makeText(this,"Password is Not Matching",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Empty field not Allowed",Toast.LENGTH_SHORT).show()
            }




//            val intent=Intent(this,MainActivity::class.java)
//            startActivity(intent)
        }
    }
    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
}