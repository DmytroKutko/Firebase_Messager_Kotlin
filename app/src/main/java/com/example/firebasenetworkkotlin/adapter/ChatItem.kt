package com.example.firebasenetworkkotlin.adapter

import com.example.firebasenetworkkotlin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatToItem: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}

class ChatFromItem: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}