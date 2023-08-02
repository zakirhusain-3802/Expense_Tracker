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
import androidx.core.view.isVisible
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        val login = findViewById<TextView>(R.id.loin_in)
        val name = getColoredSpanned("Already Registered,", "#111111")
        val surName = getColoredSpanned("Sign In !", "#DF2E38")
        login.setText(Html.fromHtml(name + "<b>" + surName + "</b>"))


        login.setOnClickListener() {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }
        val names = findViewById<EditText>(R.id.nameEt)
        val email = findViewById<EditText>(R.id.emailEt)
        val pass = findViewById<EditText>(R.id.passET)
        val conpass = findViewById<EditText>(R.id.confirmPassEt)

        val sign_up = findViewById<Button>(R.id.sign_up)

        sign_up.setOnClickListener() {
            val un = names.text.toString()
            val em = email.text.toString()
            val pas = pass.text.toString()
            val conpas = conpass.text.toString()

            if (em.isNotEmpty() && pas.isNotEmpty() && conpas.isNotEmpty() && un.isNotEmpty()) {
                if (isEmailValid(em.trim().toString())) {
                    // Email is valid, proceed with further actions
                    if (isValidPassword(pas.toString())) {

                        if (pas == conpas) {
                            val errormsg = findViewById<TextView>(R.id.error_msg)


                            firebaseAuth.createUserWithEmailAndPassword(em.trim(), pas)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {


                                        FirebaseDatabase.getInstance().getReference("Users")
                                            .child(firebaseAuth.currentUser!!.uid).setValue(un)
                                            .addOnCompleteListener() {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(
                                                        this,
                                                        "SignUp successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    firebaseAuth.signOut()
                                                    val intent =
                                                        Intent(this, Login_Activity::class.java)
                                                    startActivity(intent)

                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        it.exception.toString(),
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
//                            firebaseAuth.signOut()
//
//                            val intent = Intent(this, Login_Activity::class.java)
//                            startActivity(intent)
                                    } else {

                                        val exception = it.exception
                                        println(exception.toString())

                                        when (exception) {
                                            is FirebaseAuthUserCollisionException -> {
                                                // Invalid user (user doesn't exist)

                                                errormsg.text =
                                                    " Already user exists, Please Login ..!"
                                                errormsg.isVisible = true

                                            }
                                            is FirebaseAuthInvalidCredentialsException -> {
                                                // Invalid credentials (wrong email or password)
                                                errormsg.text = "Incorrect Email or Password"
                                            }

                                            else -> {
                                                // Other authentication exceptions
                                                Toast.makeText(
                                                    this,
                                                    "Please check internet connectivity",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
//                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                        } else {
                            error_msg.isVisible = true
                            error_msg.text = "Password not matching "
                            error_msg.textSize = 15F
//                    Toast.makeText(this,"Password is Not Matching",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        error_msg.isVisible = true
                        error_msg.text =
                           "Password: Min 8 chars, 1 lowercase, 1 uppercase, 1 special (!, +, -), 1 digit."
                        error_msg.textSize = 12F
                    }

                } else {
                    // Invalid email, show an error message or handle it appropriately
                    val errormsg = findViewById<TextView>(R.id.error_msg)
                    errormsg.isVisible = true
                    errormsg.text = "Invalid email address"
                    error_msg.textSize = 15F
                }
            } else {
                error_msg.isVisible = true
                error_msg.text = "Empty field not allowed"
                error_msg.textSize = 15F
//                Toast.makeText(this,"Empty field not Allowed",Toast.LENGTH_SHORT).show()
            }


//            val intent=Intent(this,MainActivity::class.java)
//            startActivity(intent)
        }
    }

    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }

    internal fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
                .firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                .firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

}