package com.example.chessgame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ChatAdapter(
    private val context: Context,
    private val talkList: Int,
    private val chatData: ArrayList<ChatVO>,
    private val id: String
) :
    BaseAdapter() {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return chatData.size
    }

    override fun getItem(p0: Int): Any {
        return chatData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder = ViewHolder()
        var convertView = p1

        if (convertView == null) {
            convertView = inflater.inflate(talkList, p2, false)
            holder.yourMessage = convertView?.findViewById(R.id.yourMessage)
            holder.yourName = convertView?.findViewById(R.id.yourName)
            holder.myMessage = convertView?.findViewById(R.id.myMessage)
            holder.myName = convertView?.findViewById(R.id.myName)

            convertView?.tag = holder
        } else {
            holder = convertView.tag as ViewHolder

            holder.yourMessage?.text = chatData[p0].content
            holder.yourName?.text = chatData[p0].id
            holder.myMessage?.text = chatData[p0].content
            holder.myName?.text = chatData[p0].id
        }

        if (chatData[p0].id == id) {
            holder.yourMessage?.visibility = View.GONE
            holder.yourName?.visibility = View.GONE
            holder.myMessage?.visibility = View.VISIBLE
            holder.myName?.visibility = View.VISIBLE

            holder.myName?.text=chatData[p0].id
            holder.myMessage?.text=chatData[p0].content
        } else {
            holder.yourMessage?.visibility = View.VISIBLE
            holder.yourName?.visibility = View.VISIBLE
            holder.myMessage?.visibility = View.GONE
            holder.yourName?.visibility = View.GONE

            holder.yourName?.text=chatData[p0].id
            holder.yourMessage?.text=chatData[p0].content
        }

        return convertView!!
    }

    inner class ViewHolder {
        var yourName: TextView? = null
        var yourMessage: TextView? = null
        var myName: TextView? = null
        var myMessage: TextView? = null
    }
}