package com.example.firebasenetworkkotlin.authorisation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.messager.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initListener()
    }

    private fun initListener() {
        btnLogin.setOnClickListener {
            performLogin()
        }

        tvCreateAccount.setOnClickListener {
            finish()
        }
    }

    private fun performLogin() {
        val email = etEmailLogin.text.toString()
        val password = etPasswordLogin.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Log.d("Login", "Fields is empty!!!")
        }
        Log.d("Login", "Email: $email")

        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                startActivity(Intent(this, LatestMessagesActivity::class.java))
            }
    }
}
