package com.suhaili.gitconsumer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhaili.gitconsumer.R
import com.suhaili.gitconsumer.adapter.FavoritAdapter
import com.suhaili.gitconsumer.customview.ClickCallBack
import com.suhaili.gitconsumer.databinding.ActivityFavoritUserBinding
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.CONTENT_URI
import com.suhaili.gitconsumer.model.FavoritModel
import com.suhaili.gitconsumer.modelview.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritUserActivity : AppCompatActivity() {
    private lateinit var model: FavoritAdapter
    private lateinit var bind: ActivityFavoritUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFavoritUserBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.favorite)
        bind.listFav.layoutManager = LinearLayoutManager(this)
        model = FavoritAdapter()
        bind.listFav.adapter = model

        loadProgress(true)
        getAllDatanya()
        model.setClickBack(object : ClickCallBack {
            override fun ItemClicked(fav: FavoritModel) {
                val Intent = Intent(applicationContext, DetailActivity::class.java)
                Intent.putExtra(MainActivity.LEMPAR, fav)
                Intent.putExtra(DetailActivity.FROM, "favorit")
                startActivity(Intent)
                finish()
            }

        })

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

    fun getAllDatanya() {
        GlobalScope.launch(Dispatchers.Main) {

            val data = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                Log.d("Lookme","$cursor")
                MappingHelper.CursorToArrayList(cursor!!)
            }
            val result = data.await()
            Log.d("TOLONG DILIHAT","${result.get(0).username}")
            if (result.size > 0) {
                model.setList(result)
                loadProgress(false)
            }else{
                Toast.makeText(applicationContext,"Nothing User You Liked",Toast.LENGTH_SHORT).show()
                loadProgress(false)
            }
        }
    }

    private fun loadProgress(stat: Boolean) {
        if (stat) {
            bind.progressCircularmain.visibility = View.VISIBLE
        } else {
            bind.progressCircularmain.visibility = View.INVISIBLE
        }
    }
}