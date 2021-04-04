package com.suhaili.submissiontwobfaa.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.suhaili.submissiontwobfaa.R
import com.suhaili.submissiontwobfaa.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.title = resources.getString(R.string.about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this)
                .load(R.drawable.picprofile)
                .apply(RequestOptions().override(150, 150))
                .into(viewBind.profilepic)
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