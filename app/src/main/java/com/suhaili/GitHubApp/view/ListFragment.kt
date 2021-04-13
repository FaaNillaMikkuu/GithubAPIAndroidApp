package com.suhaili.GitHubApp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhaili.GitHubApp.adapter.GitHubAdapter
import com.suhaili.GitHubApp.customview.OnItemClickCallback
import com.suhaili.GitHubApp.databinding.FragmentListBinding
import com.suhaili.GitHubApp.model.GitModel
import com.suhaili.GitHubApp.modelview.MainViewFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFrag : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _bind: FragmentListBinding? = null
    private val bind get() = _bind!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

   private lateinit var MainView: MainViewFragment
   private lateinit var RecAdapter: GitHubAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentListBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainView = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewFragment::class.java)
        RecAdapter = GitHubAdapter()
        RecAdapter.notifyDataSetChanged()
        val number = arguments?.getInt(PARAMS_SECTION)
        val username = arguments?.getString(USER)
        val Follower = arguments?.getString(FOLLOWER)
        val Following = arguments?.getString(FOLLOWING)
        LoadingProgrees(true)
        if (number == 1) {
            if (username != null && Following != "0") {
                MainView.getFollowing(username, requireActivity().applicationContext)
                observeData()
            } else {
                bind.progressCircularmain.visibility = View.INVISIBLE
            }

        } else if (number == 2) {
            if (username != null && Follower != "0") {
                MainView.getFollow(username, requireActivity().applicationContext)
                observeData()
            } else {
                bind.progressCircularmain.visibility = View.INVISIBLE
            }

        }

        bind.recycler.layoutManager = LinearLayoutManager(activity)
        bind.recycler.adapter = RecAdapter

        RecAdapter.setOnItemClickCallback(object : OnItemClickCallback{
            override fun onItemClicked(model: GitModel) {
                val moveDetail = Intent(activity, DetailActivity::class.java)
                moveDetail.putExtra(MainActivity.LEMPAR, model)
                moveDetail.putExtra(DetailActivity.FROM,"main")
                startActivity(moveDetail)
            }
        })

    }

    private fun observeData() {
        MainView.getLivedata().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                RecAdapter.setData(items)
                LoadingProgrees(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

    private fun LoadingProgrees(status: Boolean) {
        if (status) {
            bind.progressCircularmain.visibility = View.VISIBLE
        } else {
            bind.progressCircularmain.visibility = View.INVISIBLE
        }
    }

    companion object {

        private val PARAMS_SECTION = "ViewChange"
        private val USER = "key"
        private val FOLLOWER = "follower"
        private val FOLLOWING = "following"


        @JvmStatic
        fun newInstance(index: Int, user: String, val1: String, val2: String) =
                ListFrag().apply {
                    arguments = Bundle().apply {
                        putInt(PARAMS_SECTION, index)
                        putString(USER, user)
                        putString(FOLLOWER, val1)
                        putString(FOLLOWING, val2)
                    }
                }
    }


}