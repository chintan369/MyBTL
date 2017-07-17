package com.agraeta.user.btl

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

class SingleNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_notification)

        val directMessage: String = intent.getStringExtra("directMessage")
        val directTitle: String = intent.getStringExtra("directTitle")

        showNotificationDialog(directTitle, directMessage)
    }

    private fun showNotificationDialog(directTitle: String, directMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(directTitle)
        builder.setMessage(directMessage)
        builder.setPositiveButton("CLOSE") { dialog, _ ->
            onBackPressed()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        finish()
    }
}
