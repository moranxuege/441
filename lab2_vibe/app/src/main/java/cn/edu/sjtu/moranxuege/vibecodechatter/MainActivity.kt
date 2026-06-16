package cn.edu.sjtu.moranxuege.vibecodechatter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sjtu.moranxuege.vibecodechatter.ChattStore.chatts
import cn.edu.sjtu.moranxuege.vibecodechatter.ChattStore.getChatts
import cn.edu.sjtu.moranxuege.vibecodechatter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private lateinit var chattListAdapter: ChattListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)
        title = getString(R.string.app_name)

        chattListAdapter = ChattListAdapter(this, chatts)
        view.chattListView.adapter = chattListAdapter

        view.refreshContainer.setOnRefreshListener {
            refreshTimeline()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshTimeline()
    }

    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))

    private fun refreshTimeline() {
        view.refreshContainer.isRefreshing = true
        getChatts(applicationContext) {
            runOnUiThread {
                chattListAdapter.notifyDataSetChanged()
                view.refreshContainer.isRefreshing = false
            }
        }
    }
}
