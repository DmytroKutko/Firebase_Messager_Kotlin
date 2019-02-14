package com.example.firebasenetworkkotlin.messangerActivities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.adapter.ChatFromItem
import com.example.firebasenetworkkotlin.adapter.ChatToItem
import com.example.firebasenetworkkotlin.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)


        setTitleChat()
        initChatLog()
    }

    private fun setTitleChat() {
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user.username
    }

    private fun initChatLog() {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        rvChatLog.adapter = adapter
    }
}
