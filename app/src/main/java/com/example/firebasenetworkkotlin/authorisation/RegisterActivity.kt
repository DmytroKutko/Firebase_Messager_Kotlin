package com.example.firebasenetworkkotlin.authorisation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.messangerActivities.LatestMessagesActivity
import com.example.firebasenetworkkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

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

        btnSelectPhoto.setOnClickListener {
            selectPhoto()
        }
    }

    private fun selectPhoto() {
        Log.d("Main selectPhoto", "Try to show photo selector")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("Main onActivityResult", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            civRegisterPhoto.setImageBitmap(bitmap)
            btnSelectPhoto.alpha = 0f

        }
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

                Log.d("Main", "Successfully created user with uId: ${it.result!!.user.uid}")
                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_SHORT).show()
                Log.d("Main", "Failed to create user ${it.message}")
            }
    }

    private fun uploadImageToFirebaseStorage() {

        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val storage = FirebaseStorage.getInstance().getReference("/images/$filename")

        storage.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Main uploadImage", "image successfully uploaded: ${it.metadata!!.path}")

                storage.downloadUrl.addOnSuccessListener {
                    Log.d("Main imageLocation", "$it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val db = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, etUsernameRegister.text.toString(), profileImageUrl)
        db.setValue(user)
            .addOnSuccessListener {
                Log.d("Main Register", "Save user into database")

                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Main saveUser", "Fail to save user into database")
            }
    }
}
