package cn.edu.sjtu.moranxuege.kotlinChatter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sjtu.moranxuege.kotlinChatter.ChattStore.chatts
import cn.edu.sjtu.moranxuege.kotlinChatter.ChattStore.getChatts
import cn.edu.sjtu.moranxuege.kotlinChatter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private lateinit var chattListAdapter: ChattListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        chattListAdapter = ChattListAdapter(this, chatts)
        view.chattListView.setAdapter(chattListAdapter)

        view.refreshContainer.setOnRefreshListener {
            refreshTimeline()
        }

        refreshTimeline()
    }

    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))

    private fun refreshTimeline() {
        getChatts(applicationContext) {
            runOnUiThread {
                chattListAdapter.notifyDataSetChanged()
            }
            view.refreshContainer.isRefreshing = false
        }
    }
}
