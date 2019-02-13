package com.example.firebasenetworkkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener(View.OnClickListener { v ->
            val email = etEmailRegister.text.toString()
            val password = etPasswordRegister.text.toString()
            Log.d("MainActivity", "Email: $email")
            Log.d("MainActivity", "Password: $password")
        })

        tvAlreadyHaveAnAccount.setOnClickListener(View.OnClickListener { v ->
            startActivity(Intent(this, LoginActivity::class.java))
        })
    }
}
