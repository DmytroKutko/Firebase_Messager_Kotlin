package com.example.firebasenetworkkotlin.messangerActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasenetworkkotlin.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat"
        initChatLog()
    }

    private fun initChatLog() {
        val adapter = GroupAdapter<ViewHolder>()

        rvChatLog.adapter = adapter
    }
}
