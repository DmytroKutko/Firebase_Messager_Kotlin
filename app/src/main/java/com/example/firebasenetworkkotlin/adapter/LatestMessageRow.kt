package com.example.firebasenetworkkotlin.adapter

import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.model.ChatMessage
import com.example.firebasenetworkkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(private val chatMessage: ChatMessage) : Item<ViewHolder>() {

    var chatPartnerUser : User? = null

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvLatestMessage.text = chatMessage.text

        val chatParentId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatParentId = chatMessage.toId
        } else {
            chatParentId = chatMessage.fromId
        }

        val userRef = FirebaseDatabase.getInstance().getReference("/users/$chatParentId")
        userRef.addListenerForSingleValueEvent(object : ChildEventListener, ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.tvUserLatestMessage.text = chatPartnerUser?.username.toString()

                val targetImageView = viewHolder.itemView.civLatestMessage
                Picasso.get().load(chatPartnerUser?.profileImage).into(targetImageView)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}