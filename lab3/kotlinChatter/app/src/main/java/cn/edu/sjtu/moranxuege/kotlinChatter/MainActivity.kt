package cn.edu.sjtu.moranxuege.kotlinChatter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableList
import cn.edu.sjtu.moranxuege.kotlinChatter.ChattStore.chatts
import cn.edu.sjtu.moranxuege.kotlinChatter.ChattStore.getChatts
import cn.edu.sjtu.moranxuege.kotlinChatter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private lateinit var chattListAdapter: ChattListAdapter

    private val propertyObserver = object: ObservableList.OnListChangedCallback<ObservableList<Chatt>>() {
        override fun onChanged(sender: ObservableList<Chatt>?) { }
        override fun onItemRangeChanged(sender: ObservableList<Chatt>?, positionStart: Int, itemCount: Int) { }
        override fun onItemRangeInserted(
            sender: ObservableList<Chatt>?,
            positionStart: Int,
            itemCount: Int
        ) {
            println("onItemRangeInserted: $positionStart, $itemCount")
            runOnUiThread {
                chattListAdapter.notifyDataSetChanged()
            }
        }
        override fun onItemRangeMoved(sender: ObservableList<Chatt>?, fromPosition: Int, toPosition: Int,
                                      itemCount: Int) { }
        override fun onItemRangeRemoved(sender: ObservableList<Chatt>?, positionStart: Int, itemCount: Int) { }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        chattListAdapter = ChattListAdapter(this, chatts)
        view.chattListView.setAdapter(chattListAdapter)

        chatts.addOnListChangedCallback(propertyObserver)

        view.refreshContainer.setOnRefreshListener {
            refreshTimeline()
        }

        getChatts()
    }

    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))

    override fun onDestroy() {
        super.onDestroy()
        chatts.removeOnListChangedCallback(propertyObserver)
    }

    private fun refreshTimeline() {
        getChatts()
        view.refreshContainer.isRefreshing = false
    }
}
