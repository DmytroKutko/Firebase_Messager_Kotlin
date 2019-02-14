package com.example.firebasenetworkkotlin.adapter

import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class UserItem(val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvUsernameNewMessage.text = user.username
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.civUserPhoto)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}