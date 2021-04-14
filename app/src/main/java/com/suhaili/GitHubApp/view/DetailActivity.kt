package com.suhaili.GitHubApp.view

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StyleRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.adapter.TabAdapter
import com.suhaili.GitHubApp.databinding.ActivityDetailBinding
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.AVATAR_URL
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.COMPANY
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.CONTENT_URI
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.FOLLOWER
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.FOLLOWING
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.LOCATION
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.NAME
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.REPOSITORY
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.USERNAME
import com.suhaili.GitHubApp.model.FavoritModel
import com.suhaili.GitHubApp.model.GitModel
import com.suhaili.GitHubApp.modelview.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class DetailActivity : AppCompatActivity() {
    companion object {
        @StyleRes
        private val JudulTab = intArrayOf(
                R.string.follow,
                R.string.follower
        )
        const val FROM = "from"
        const val FAVORIT = "favorit"
        const val MAIN = "main"
    }

    private lateinit var viewAdap: ViewPager2
    private lateinit var bind: ActivityDetailBinding
    private lateinit var favModel: FavoritModel
    private lateinit var tempid: String
    private lateinit var TabAdapter: TabAdapter
    private lateinit var tempUser: String
    private lateinit var FavoritMod: FavoritModel
    private lateinit var MainModel: GitModel
    private lateinit var uriWithID: Uri
    private var statFab: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val detail = StringBuilder()
        val stat = intent.extras
        val cek = stat?.getString(FROM)

        when (cek) {
            FAVORIT -> {
                FavoritMod = stat?.getParcelable<FavoritModel>(MainActivity.LEMPAR) as FavoritModel
                detail.append(resources.getString(R.string.detail))
                detail.append(" ")
                detail.append(FavoritMod.username)
                supportActionBar?.title = detail.toString()
                Glide.with(this)
                        .load(FavoritMod.avatar)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(bind.pic)
                bind.username.text = FavoritMod.username
                bind.name.text = FavoritMod.name
                bind.repo.text = FavoritMod.repo
                bind.lokasi.text = FavoritMod.location
                bind.company.text = FavoritMod.company
                bind.diikutin.text = FavoritMod.follower
                bind.ikutin.text = FavoritMod.following
                viewAdap = bind.viewPagernya

                tempUser = FavoritMod.username.toString().trim()
                cekdata()
                favModel = FavoritModel()
                favModel.username = FavoritMod.username.toString()
                favModel.avatar = FavoritMod.avatar.toString()
                favModel.location = FavoritMod.location.toString()
                favModel.repo = FavoritMod.repo.toString()
                favModel.company = FavoritMod.company.toString()
                favModel.following = FavoritMod.following.toString()
                favModel.follower = FavoritMod.follower.toString()
                favModel.name = FavoritMod.name.toString()


                bind.addfavorit?.setOnClickListener {
                    if (statFab) {
                        bind.addfavorit?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        delFav(tempUser)
                    } else {
                        bind.addfavorit?.setImageResource(R.drawable.ic_baseline_favorite_24)
                        addFav(favModel, favModel.username.toString())
                    }
                }
                TabAdapter = TabAdapter(this, FavoritMod.username!!, FavoritMod.follower!!, FavoritMod.following!!)
            }
            MAIN -> {
                MainModel = stat?.getParcelable<GitModel>(MainActivity.LEMPAR) as GitModel
                detail.append(resources.getString(R.string.detail))
                detail.append(" ")
                detail.append(MainModel.username)
                supportActionBar?.title = detail.toString()
                Glide.with(this)
                        .load(MainModel.avatar)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(bind.pic)
                bind.username.text = MainModel.username
                bind.name.text = MainModel.name
                bind.repo.text = MainModel.repo
                bind.lokasi.text = MainModel.location
                bind.company.text = MainModel.company
                bind.diikutin.text = MainModel.follower
                bind.ikutin.text = MainModel.following
                viewAdap = bind.viewPagernya
                tempUser = MainModel.username.toString().trim()
                cekdata()
                favModel = FavoritModel()
                favModel.username = MainModel.username.toString()
                favModel.avatar = MainModel.avatar.toString()
                favModel.location = MainModel.location.toString()
                favModel.repo = MainModel.repo.toString()
                favModel.company = MainModel.company.toString()
                favModel.following = MainModel.following.toString()
                favModel.follower = MainModel.follower.toString()
                favModel.name = MainModel.name.toString()


                bind.addfavorit?.setOnClickListener {
                    if (statFab) {
                        bind.addfavorit?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        delFav(tempUser)
                    } else {
                        bind.addfavorit?.setImageResource(R.drawable.ic_baseline_favorite_24)
                        addFav(favModel, favModel.username.toString())
                    }
                }

                TabAdapter = TabAdapter(this, MainModel.username!!, MainModel.follower!!, MainModel.following!!)
            }

        }


        viewAdap.adapter = TabAdapter
        val tabTittle: TabLayout = bind.tabLayout
        TabLayoutMediator(tabTittle, viewAdap)
        { tab, position ->
            tab.text = resources.getString(JudulTab[position])
        }.attach()

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

    private fun addFav(model: FavoritModel, user: String) {
        val content = ContentValues()
        content.put(USERNAME, model.username)
        content.put(NAME, model.name)
        content.put(AVATAR_URL, model.avatar)
        content.put(FOLLOWER, model.follower)
        content.put(FOLLOWING, model.following)
        content.put(LOCATION, model.location)
        content.put(COMPANY, model.company)
        content.put(REPOSITORY, model.repo)
        contentResolver.insert(CONTENT_URI, content)
        statFab = true
        Snackbar.make(bind.viewPagernya, "Add $user to Favorite List Successfull", Snackbar.LENGTH_SHORT).show()
    }

    private fun delFav(user: String) {
        GlobalScope.launch(Dispatchers.Main) {

            val data = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.CursorToArrayList(cursor!!)
            }
            val result = data.await()
            val favmodel = FavoritModel()
            if (result.size > 0) {
                for (i in 0 until result.size) {
                    if (result.get(i).username == user) {
                        favmodel._id = result.get(i)._id
                        favmodel.username = result.get(i).username
                        favmodel.name = result.get(i).name
                        favmodel.location = result.get(i).location
                        favmodel.repo = result.get(i).repo
                        favmodel.company = result.get(i).company
                        favmodel.following = result.get(i).following
                        favmodel.follower = result.get(i).follower
                        favmodel.avatar = result.get(i).avatar
                        break
                    }
                }
            }
            uriWithID = Uri.parse(CONTENT_URI.toString() + "/" + favmodel?._id)
            contentResolver.delete(uriWithID, null, null)
            statFab = false
            Snackbar.make(bind.viewPagernya, "Delete $user From Favorite List SuccesFull", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun cekdata() {
        GlobalScope.launch(Dispatchers.Main) {
            val proses = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.CursorToArrayList(cursor!!)
            }
            val result = proses.await()
            if (result.size > 0) {
                for (i in 0 until result.size) {
                    if (result.get(i).username == tempUser) {
                        tempid = result.get(i)._id.toString()
                        statFab = true
                        break
                    }
                }
                if (statFab) {
                    bind.addfavorit?.setImageResource(R.drawable.ic_baseline_favorite_24)

                } else {
                    bind.addfavorit?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
    }
}


