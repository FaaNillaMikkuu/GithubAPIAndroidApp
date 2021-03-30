package com.suhaili.submissiontwobfaa.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StyleRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suhaili.submissiontwobfaa.R
import com.suhaili.submissiontwobfaa.adapter.TabAdapter
import com.suhaili.submissiontwobfaa.databinding.ActivityDetailBinding
import com.suhaili.submissiontwobfaa.model.GitModel
import java.lang.StringBuilder

class DetailActivity : AppCompatActivity() {
    companion object {
        @StyleRes
        private val JudulTab = intArrayOf(
            R.string.follow,
            R.string.follower
        )
    }

    lateinit var viewAdap: ViewPager2
    lateinit var bind: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val detail = StringBuilder()
        val dataintent = intent.getParcelableExtra<GitModel>(MainActivity.LEMPAR) as GitModel
        detail.append(resources.getString(R.string.detail))
        detail.append(" ")
        detail.append(dataintent.username)
        supportActionBar?.title = detail.toString()
        Glide.with(this)
            .load(dataintent.avatar)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(bind.pic)

        bind.username.text = dataintent.username
        bind.name.text = dataintent.name
        bind.repo.text = dataintent.repo
        bind.lokasi.text = dataintent.location
        bind.company.text = dataintent.company
        bind.diikutin.text = dataintent.follower
        bind.ikutin.text = dataintent.following
        viewAdap = bind.viewPagernya

        val tabadapter = TabAdapter(this, dataintent.username,dataintent.follower,dataintent.following)
        viewAdap.adapter = tabadapter
        val tabTittle: TabLayout = bind.tabLayout
        TabLayoutMediator(tabTittle, viewAdap) { tab, position ->
            tab.text = resources.getString(JudulTab[position])
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}