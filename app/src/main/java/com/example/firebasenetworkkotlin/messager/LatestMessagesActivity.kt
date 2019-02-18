package com.example.firebasenetworkkotlin.messager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.firebasenetworkkotlin.R
import com.example.firebasenetworkkotlin.adapter.LatestMessageRow
import com.example.firebasenetworkkotlin.authorisation.RegisterActivity
import com.example.firebasenetworkkotlin.model.ChatMessage
import com.example.firebasenetworkkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*

class LatestMessagesActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    val latestMessagesMap = HashMap<String, ChatMessage>()
    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        rvLatestMessages.adapter = adapter
        rvLatestMessages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        //set on item click listener
        adapter.setOnItemClickListener { item, view ->
            Log.d("OnLatestMessageClick", item.id.toString())
            val intent = Intent(this, ChatLogActivity::class.java)

            val row = item as LatestMessageRow
            row.chatPartnerUser
            intent.putExtra(NewMessageActivity.USER_KEY,row.chatPartnerUser)
            startActivity(intent)
        }

//        setDummyRows()
        latestMessageListener()
        fetchCurrentUser()
        verifyUserIsLoggedIn()
    }

    private fun latestMessageListener() {
        val fromId = FirebaseAuth.getInstance().uid
        val messageRef = FirebaseDatabase.getInstance().getReference("/latest_messages/$fromId")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }

//    private fun setDummyRows() {
//        val adapter = GroupAdapter<ViewHolder>()
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//        rvLatestMessages.adapter = adapter
//    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val userRef = FirebaseDatabase.getInstance().getReference("/users/$uid")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d("CurrentUser", currentUser?.username)
            }

        })
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            exitToRegisterActivity()
        }
    }

    private fun exitToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuNewMessage -> {
                startActivity(Intent(this, NewMessageActivity::class.java))
            }
            R.id.menuSignOut -> {
                FirebaseAuth.getInstance().signOut()
                exitToRegisterActivity()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
