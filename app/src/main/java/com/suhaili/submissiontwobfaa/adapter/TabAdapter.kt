package com.suhaili.submissiontwobfaa.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suhaili.submissiontwobfaa.view.ListFrag

class TabAdapter(activity: AppCompatActivity, val user: String,val stat1 : String,val stat2 : String) : FragmentStateAdapter(activity) {


    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return ListFrag.newInstance(position + 1, user,stat1,stat2)
    }

}