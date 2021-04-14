package com.suhaili.GitHubApp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.customview.ClickCallBack
import com.suhaili.GitHubApp.databinding.ListItemBinding
import com.suhaili.GitHubApp.model.FavoritModel

class FavoritAdapter: RecyclerView.Adapter<FavoritAdapter.itemTarget>() {
    val DataFavorit = ArrayList<FavoritModel>()
    fun setList(model: ArrayList<FavoritModel>) {
        if (DataFavorit.size > 0) {
            DataFavorit.clear()
            this.DataFavorit.addAll(model)
            notifyDataSetChanged()
        } else {
            this.DataFavorit.addAll(model)
            notifyDataSetChanged()
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    private var click : ClickCallBack? = null

    fun setClickBack(item : ClickCallBack){
        this.click = item
    }

    inner class itemTarget(private val bind: ListItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(model: FavoritModel) {
            with(bind) {
                Glide.with(itemView.context)
                    .load(model.avatar)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(picAvatar)

                bind.name.text = model.name
                bind.username.text = model.username

                itemView.setOnClickListener{click?.ItemClicked(model)}
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemTarget {
        return itemTarget(
           ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: itemTarget, position: Int) {
        holder.binding(DataFavorit[position])
    }

    override fun getItemCount(): Int =
        DataFavorit.size
}


