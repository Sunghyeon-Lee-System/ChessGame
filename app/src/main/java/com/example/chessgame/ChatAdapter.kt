package com.example.chessgame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
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
            holder.yourProfile = convertView?.findViewById(R.id.yourProfile)
            holder.yourMessage = convertView?.findViewById(R.id.yourMessage)
            holder.yourName = convertView?.findViewById(R.id.yourName)
            holder.yourTime = convertView?.findViewById(R.id.yourTime)
            holder.myMessage = convertView?.findViewById(R.id.myMessage)
            holder.myName = convertView?.findViewById(R.id.myName)
            holder.myTime = convertView?.findViewById(R.id.myTime)

            convertView?.tag = holder
        } else {
            holder = convertView.tag as ViewHolder

            holder.yourProfile?.setImageResource(R.drawable.sample_profile)
            holder.yourMessage?.text = chatData[p0].content
            holder.yourName?.text = chatData[p0].id
            holder.yourTime?.text = chatData[p0].timeNow
            holder.myMessage?.text = chatData[p0].content
            holder.myName?.text = chatData[p0].id
            holder.myTime?.text = chatData[p0].timeNow
        }

        if (chatData[p0].id == id) {
            holder.yourProfile?.visibility = View.GONE
            holder.yourMessage?.visibility = View.GONE
            holder.yourName?.visibility = View.GONE
            holder.yourTime?.visibility = View.GONE
            holder.myMessage?.visibility = View.VISIBLE
            holder.myName?.visibility = View.VISIBLE
            holder.myTime?.visibility = View.VISIBLE

            holder.myName?.text = chatData[p0].id
            holder.myMessage?.text = chatData[p0].content
            holder.myTime?.text = chatData[p0].timeNow
        } else {
            holder.yourProfile?.visibility = View.VISIBLE
            holder.yourMessage?.visibility = View.VISIBLE
            holder.yourName?.visibility = View.VISIBLE
            holder.yourTime?.visibility = View.VISIBLE
            holder.myMessage?.visibility = View.GONE
            holder.myName?.visibility = View.GONE
            holder.myTime?.visibility = View.GONE

            holder.yourProfile?.setImageResource(R.drawable.sample_profile)
            holder.yourName?.text = chatData[p0].id
            holder.yourMessage?.text = chatData[p0].content
            holder.yourTime?.text = chatData[p0].timeNow
        }

        return convertView!!
    }

    inner class ViewHolder {
        var yourProfile: ImageView? = null
        var yourName: TextView? = null
        var yourMessage: TextView? = null
        var yourTime: TextView? = null

        var myName: TextView? = null
        var myMessage: TextView? = null
        var myTime: TextView? = null
    }
}