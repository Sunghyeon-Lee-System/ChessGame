package com.example.chessgame

import android.app.Activity
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.*

class ChatManager(
    private val activity: Activity,
    private val myName: String,
    private val dialog: BottomSheetDialog,
    private val chatData: DatabaseReference
) {
    private val list = ArrayList<ChatVO>()

    init {
        val adapter = ChatAdapter(activity.applicationContext, R.layout.chatlist, list, myName)

        val chatList = dialog.findViewById<ListView>(R.id.chatList)
        val chatMessage = dialog.findViewById<EditText>(R.id.chatMessage)
        val sendButton = dialog.findViewById<Button>(R.id.sendButton)

        val chatButton = activity.findViewById<Button>(R.id.chatButton)

        chatList?.adapter = adapter

        sendButton?.setOnClickListener {
            if (chatMessage?.text.toString() == "") {
                Toast.makeText(activity.applicationContext, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                val date = Date()
                val timeNow = SimpleDateFormat("a K:mm")
                val sb = StringBuffer(chatMessage?.text.toString())
                if (sb.length >= 15) {
                    for (i in 0..sb.length / 15) {
                        sb.insert(i * 15, "\n")
                    }
                }
                adapter.notifyDataSetChanged()
                chatMessage?.setText("")

                chatData.push().setValue(
                    ChatVO(
                        myName,
                        sb.toString(),
                        timeNow.format(date)
                    )
                )
                android.util.Log.i("ChessGame", sb.toString())
            }
        }

        chatData.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val value = snapshot.getValue(ChatVO::class.java)
                if (value != null) {
                    list.add(value)
                    if (myName != value.id) {
                        if (!dialog.isShowing) {
                            Toast.makeText(
                                activity.applicationContext,
                                "채팅이 도착했습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                            chatButton.setBackgroundColor(Color.rgb(0, 255, 0))
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}