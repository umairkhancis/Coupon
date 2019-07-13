package com.noorifytech.coupon.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.noorifytech.coupon.R
import com.noorifytech.coupon.ui.lpv.ItemListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private var currentState = R.id.navigation_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (item.itemId == R.id.navigation_home && currentState != R.id.navigation_home) {
            showHomeScreen()
            return@OnNavigationItemSelectedListener true
        }
        else if (item.itemId == R.id.navigation_dashboard && currentState != R.id.navigation_dashboard) {
            return@OnNavigationItemSelectedListener true
        }
        else if (item.itemId == R.id.navigation_notifications && currentState != R.id.navigation_notifications) {
            return@OnNavigationItemSelectedListener true
        }
        false
    }

    private fun showHomeScreen() {
        val intent = Intent(this, ItemListActivity::class.java)
        startActivity(intent)
    }
}
