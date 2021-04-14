package com.suhaili.GitHubApp.view


import androidx.appcompat.widget.SearchView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.adapter.GitHubAdapter
import com.suhaili.GitHubApp.customview.OnItemClickCallback
import com.suhaili.GitHubApp.databinding.ActivityMainBinding
import com.suhaili.GitHubApp.model.GitModel
import com.suhaili.GitHubApp.modelview.MainViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var bind: ActivityMainBinding
    private lateinit var MainView: MainViewModel
    private lateinit var RecAdapter: GitHubAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        MainView = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        RecAdapter = GitHubAdapter()
        RecAdapter.notifyDataSetChanged()
        bind.RCData.layoutManager = LinearLayoutManager(this)
        bind.RCData.adapter = RecAdapter
        MainView.getAllData(this)
        LoadingProgrees(true)
        observeData()
        bind.searchUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != "") {
                    MainView.getLiveData().value?.clear()
                    datafound(newText)
                    LoadingProgrees(true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        LoadingProgrees(false)
                    }, 5000)
                } else if (newText == "") {
                    MainView.getLiveData().value?.clear()
                    showAllData()
                    LoadingProgrees(true)
                }
                return false
            }
        })

        RecAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(model: GitModel) {
                val moveDetail = Intent(applicationContext, DetailActivity::class.java)
                moveDetail.putExtra(LEMPAR, model)
                moveDetail.putExtra(DetailActivity.FROM, "main")
                startActivity(moveDetail)
                finish()
            }
        })

    }

    private fun observeData() {
        MainView.getLiveData().observe(this, { items ->
            if (items != null) {
                RecAdapter.setData(items)
                LoadingProgrees(false)
            }
        })
    }

    private fun showAllData() {
        MainView.getAllData(this)
    }

    private fun datafound(c: String?) {
        MainView.findPeople(c!!, this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opsi, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorituser -> {
                startActivity(Intent(this@MainActivity,FavoritUserActivity::class.java))
            }
            R.id.set -> {
                startActivity(Intent(this@MainActivity,SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun LoadingProgrees(status: Boolean) {
        if (status) {
            bind.progressCircularmain.visibility = View.VISIBLE
        } else {
            bind.progressCircularmain.visibility = View.INVISIBLE
        }
    }

    companion object {
        val LEMPAR = "data"
    }

}