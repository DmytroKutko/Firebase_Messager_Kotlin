package com.example.firebasenetworkkotlin.adapter

import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatToItem(val text: String, val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvMessageTo.text = text

        //load user image
        val uri = user.profileImage
        val targetImageView = viewHolder.itemView.civChatPhotoTo
        Picasso.get().load(uri).into(targetImageView)
    }
}

class ChatFromItem(val text: String, val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvMessageFrom.text = text

        //load user image
        val uri = user.profileImage
        val targetImageView = viewHolder.itemView.civChatPhotoFrom
        Picasso.get().load(uri).into(targetImageView)
    }
}