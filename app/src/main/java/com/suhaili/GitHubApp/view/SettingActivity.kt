package com.suhaili.GitHubApp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.databinding.ActivitySettingBinding
import com.suhaili.GitHubApp.db.FavHelper

class SettingActivity : AppCompatActivity() {

    private lateinit var bind: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.settings)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.LayoutSettings,SettingFragment(),SettingFragment::class.java.simpleName)
            .commit()

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)

    }



}