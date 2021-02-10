package com.unity.mynativeapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private var isUnityLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
        setIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null || intent.extras == null) return
        if (intent.extras!!.containsKey("setColor")) {
            val v = findViewById<View>(R.id.button2)
            when (intent.extras!!.getString("setColor")) {
                "yellow" -> v.setBackgroundColor(Color.YELLOW)
                "red" -> v.setBackgroundColor(Color.RED)
                "blue" -> v.setBackgroundColor(Color.BLUE)
                else -> {
                }
            }
        }
    }

    fun btnLoadUnity(view: View) {
        isUnityLoaded = true
        val intent = Intent(this, MainUnityActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) isUnityLoaded = false
    }

    private fun unloadUnity(doShowToast: Boolean) {
        if (isUnityLoaded) {
            val intent = Intent(this, MainUnityActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            intent.putExtra("doQuit", true)
            startActivity(intent)
            isUnityLoaded = false
        } else if (doShowToast) showToast("Show Unity First")
    }

    fun btnUnloadUnity(view: View) {
        unloadUnity(true)
    }

    private fun showToast(message: String) {
        val text: CharSequence = message
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}