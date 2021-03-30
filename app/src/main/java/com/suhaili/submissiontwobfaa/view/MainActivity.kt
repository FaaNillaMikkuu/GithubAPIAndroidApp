package com.suhaili.submissiontwobfaa.view


import androidx.appcompat.widget.SearchView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhaili.submissiontwobfaa.R
import com.suhaili.submissiontwobfaa.adapter.GitHubAdapter
import com.suhaili.submissiontwobfaa.databinding.ActivityMainBinding
import com.suhaili.submissiontwobfaa.model.GitModel
import com.suhaili.submissiontwobfaa.modelview.MainViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        val LEMPAR = "data"
    }

    lateinit var bind: ActivityMainBinding
    lateinit var MainView: MainViewModel
    lateinit var RecAdapter: GitHubAdapter
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

        LoadingProgrees(true)
        MainView.getAllData()
        observeData()

        bind.searchUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    LoadingProgrees(true)
                    datafound(newText!!)
                }else{
                    LoadingProgrees(true)
                    showAllData()
                }
                return false
            }
        })

        RecAdapter.setOnItemClickCallback(object : GitHubAdapter.OnItemClickCallback {
            override fun onItemClicked(model: GitModel) {
                val moveDetail = Intent(applicationContext, DetailActivity::class.java)
                moveDetail.putExtra(LEMPAR, model)
                startActivity(moveDetail)
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
        MainView.getAllData()
        observeData()

    }

    private fun datafound(c: String?) {
        MainView.findPeople(c!!)
        observeData()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opsi, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingBahasa -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.me -> {
                startActivity(Intent(this, AboutActivity::class.java))
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


}