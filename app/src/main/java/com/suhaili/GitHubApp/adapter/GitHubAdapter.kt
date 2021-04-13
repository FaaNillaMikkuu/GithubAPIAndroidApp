package com.suhaili.GitHubApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.customview.OnItemClickCallback
import com.suhaili.GitHubApp.databinding.ListItemBinding
import com.suhaili.GitHubApp.model.GitModel

class GitHubAdapter : RecyclerView.Adapter<GitHubAdapter.TargetItem>() {
    val data = ArrayList<GitModel>()

    fun setData(items: ArrayList<GitModel>) {
        if(this.data.size > 0){
            this.data.clear()
            data.addAll(items)
            notifyDataSetChanged()
        }else{
            data.addAll(items)
            notifyDataSetChanged()
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TargetItem(private val bind: ListItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(model: GitModel) {
            with(bind) {

                bind.name.text = model.name
                bind.username.text = model.username

                Glide.with(itemView.context)
                        .load(model.avatar)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(picAvatar)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(model) }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetItem {
        return TargetItem(
                ListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: TargetItem, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}