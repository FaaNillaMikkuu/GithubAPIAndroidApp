package com.suhaili.submissiontwobfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suhaili.submissiontwobfaa.R
import com.suhaili.submissiontwobfaa.databinding.ListItemBinding
import com.suhaili.submissiontwobfaa.model.GitModel

class GitHubAdapter : RecyclerView.Adapter<GitHubAdapter.TargetItem>() {
    val data = ArrayList<GitModel>()

    fun setData(items: ArrayList<GitModel>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TargetItem(val bind: ListItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(model: GitModel) {
            with(bind) {

                bind.name.text = model.name
                bind.username.text = model.username

                Glide.with(itemView.context)
                        .load(model.avatar)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(bind.picAvatar)



                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(model) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(model: GitModel)
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