package cn.edu.sjtu.moranxuege.vibecodechatter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sjtu.moranxuege.vibecodechatter.ChattStore.postChatt
import cn.edu.sjtu.moranxuege.vibecodechatter.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private lateinit var view: ActivityPostBinding
    private val prefs by lazy { getSharedPreferences("post_prefs", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityPostBinding.inflate(layoutInflater)
        setContentView(view.root)

        title = getString(R.string.post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        view.usernameEditText.setText(prefs.getString(KEY_LAST_USERNAME, ""))
        view.sendButton.setOnClickListener {
            submitChatt()
        }
    }

    private fun submitChatt() {
        val username = view.usernameEditText.text.toString().trim()
        val message = view.messageEditText.text.toString().trim()

        if (username.isEmpty()) {
            view.usernameEditText.error = getString(R.string.required_field)
            return
        }
        if (message.isEmpty()) {
            view.messageEditText.error = getString(R.string.required_field)
            return
        }

        view.sendButton.isEnabled = false
        prefs.edit().putString(KEY_LAST_USERNAME, username).apply()
        postChatt(applicationContext, Chatt(username = username, message = message)) { success ->
            runOnUiThread {
                if (success) {
                    finish()
                } else {
                    view.sendButton.isEnabled = true
                    Toast.makeText(this, R.string.post_failed, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val KEY_LAST_USERNAME = "last_username"
    }
}
