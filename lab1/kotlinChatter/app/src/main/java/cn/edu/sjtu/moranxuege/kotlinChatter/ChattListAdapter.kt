package cn.edu.sjtu.moranxuege.kotlinChatter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import cn.edu.sjtu.moranxuege.kotlinChatter.databinding.ListitemChattBinding

class ChattListAdapter(context: Context, chatts: List<Chatt>) :
    ArrayAdapter<Chatt>(context, 0, chatts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = (convertView?.tag ?: run {
            val rowView = LayoutInflater.from(context).inflate(R.layout.listitem_chatt, parent, false)
            rowView.tag = ListitemChattBinding.bind(rowView)
            rowView.tag
        }) as ListitemChattBinding

        getItem(position)?.run {
            listItemView.usernameTextView.text = username
            listItemView.messageTextView.text = message
            listItemView.timestampTextView.text = timestamp
            listItemView.root.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#E0E0E0" else "#EEEEEE"))
        }

        return listItemView.root
    }
}
