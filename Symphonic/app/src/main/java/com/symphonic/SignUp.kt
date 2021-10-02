package com.symphonic

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUp : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        val sign_up_btn = findViewById<Button>(R.id.button)

        sign_up_btn.setOnClickListener {
            val users_mail = findViewById<EditText>(R.id.editTextTextEmailAddress)
            val users_pswd = findViewById<EditText>(R.id.editTextTextPassword)

            if(!checkInputs())
            {
                Toast.makeText(this,"Something went Wrong",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(users_mail.text.toString(), users_pswd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:Success", task.exception)
                        Toast.makeText(this, "successfully login", Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun checkInputs():Boolean
    {
        val users_name = findViewById<EditText>(R.id.editTextTextPersonName)
        val users_mail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val users_pswd = findViewById<EditText>(R.id.editTextTextPassword)
        val user_confirm_pswd = findViewById<EditText>(R.id.editTextTextPassword2)
        if(users_name.text.toString().isEmpty()) {
            users_name.error = "Please Enter name"
            users_name.requestFocus()
            return false
        }
        if(users_mail.text.toString().isEmpty()) {
            users_mail.error = "Please Enter Email Address"
            users_mail.requestFocus()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(users_mail.text.toString()).matches())
        {
            users_mail.error = "Please Enter Valid Email Address"
            users_mail.requestFocus()
            return false
        }
        if(users_pswd.text.toString().isEmpty()) {
            users_pswd.error = "Please Enter Password"
            users_pswd.requestFocus()
            return false
        }
        if(user_confirm_pswd.text.toString().isEmpty()) {
            user_confirm_pswd.error = "Please Enter Password Again"
            user_confirm_pswd.requestFocus()
            return false
        }
        if(user_confirm_pswd.text.toString() != users_pswd.text.toString())
        {
            user_confirm_pswd.error = "Password Not Match"
            user_confirm_pswd.requestFocus()
            return false
        }
        return true
    }
}