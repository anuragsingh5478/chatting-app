package com.anuragsingh5478.chattingapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var editTextemail : EditText
    lateinit var editTextpassword : EditText
    lateinit var buttonLogin : Button
    lateinit var buttonRegister : Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        editTextemail = findViewById(R.id.editTextEmail)
        editTextpassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            val textEmail = editTextemail.text.toString()
            val textPassword = editTextpassword.text.toString()
            loginUser(textEmail, textPassword);

        }

        buttonRegister.setOnClickListener(View.OnClickListener {
            val txt_email: String = editTextemail.text.toString()
            val txt_password: String = editTextpassword.text.toString()
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(this, "empty credentials", Toast.LENGTH_LONG).show()
            } else if (txt_password.length < 6) {
                Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT)
                    .show()
            } else {
                registerUser(txt_email, txt_password)
            }
        })




    }

    private fun
            loginUser(email: String, password: String){
        if(email.trim()!= "" && password.trim()!=""){
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(this, "login sucessful", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "registeration sucessful",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }




}