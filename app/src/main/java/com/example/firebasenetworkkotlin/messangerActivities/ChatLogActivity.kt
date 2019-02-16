package com.example.firebasenetworkkotlin.messangerActivities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.adapter.ChatFromItem
import com.example.firebasenetworkkotlin.adapter.ChatToItem
import com.example.firebasenetworkkotlin.model.ChatMessage
import com.example.firebasenetworkkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()
    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        toUser = intent.getParcelableExtra(NewMessageActivity.USER_KEY)
        rvChatLog.adapter = adapter

        setTitleChat()
//        initChatLog()
        setMessagesListener()
        setListener()
    }

    private fun setMessagesListener() {
        val messageRef = FirebaseDatabase.getInstance().getReference("/messages")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                val text = chatMessage?.text

                if (text != null) {
                    Log.d("ChatMessage", chatMessage.text)
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessagesActivity.currentUser
                        adapter.add(ChatFromItem(text, currentUser!!))
                    } else {
                        adapter.add(ChatToItem(text, toUser!!))
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    private fun setListener() {
        btnSend.setOnClickListener {
            performSendMessage()
        }
    }

    private fun performSendMessage() {
        val messageRef = FirebaseDatabase.getInstance().getReference("/messages").push()

        val toId = toUser?.uid
        val text = etMessage.text.toString().trim()
        val fromId = FirebaseAuth.getInstance().uid
        val currentTime = System.currentTimeMillis() + 2 * 60 * 60 * 1000// + 2 hours

        if (fromId == null || toId == null) {
            return
        }

        val chatMessage = ChatMessage(messageRef.key!!, text, fromId, toId, currentTime)
        messageRef.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("ChatLog", "Reference ${messageRef.key}")
            }
    }

    private fun setTitleChat() {
        supportActionBar?.title = toUser?.username
    }

//    private fun initChatLog() {
//        val adapter = GroupAdapter<ViewHolder>()
//        adapter.add(ChatToItem("hi", toUser!!))
//        adapter.add(ChatFromItem("hey",))
//        adapter.add(ChatToItem("toMessage", toUser!!))
//        adapter.add(ChatFromItem("From message"))
//        rvChatLog.adapter = adapter
//    }
}
