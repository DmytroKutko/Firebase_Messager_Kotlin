package com.example.firebasenetworkkotlin.adapter

import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.model.ChatMessage
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvUsernameLatestMessage.text = "User"
        viewHolder.itemView.tvLatestMessage.text = chatMessage.text
    }
}