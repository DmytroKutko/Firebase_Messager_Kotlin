package com.example.firebasenetworkkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initListener()
    }

    private fun initListener() {
        btnRegister.setOnClickListener(View.OnClickListener { v ->
            performRegister()
        })

        tvRegisterGotoLogin.setOnClickListener(View.OnClickListener { v ->
            startActivity(Intent(this, LoginActivity::class.java))
        })
    }

    private fun performRegister() {
        val email = etEmailRegister.text.toString()
        val password = etPasswordRegister.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email: $email")
        Log.d("MainActivity", "Password: $password")

        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                else {
                    Log.d("Main", "Successfully created user with uId: ${it.result!!.user.uid}")
                }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_SHORT).show()
                Log.d("Main", "Failed to create user ${it.message}")
            }
    }
}
