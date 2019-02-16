package com.example.firebasenetworkkotlin.messager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.adapter.UserItem
import com.example.firebasenetworkkotlin.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select user"

        fetchUsers()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers() {
        val usersRef = FirebaseDatabase.getInstance().getReference("/users")
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("NewMessageActivity", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                
                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()
                }

                rvNewMessages.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}


