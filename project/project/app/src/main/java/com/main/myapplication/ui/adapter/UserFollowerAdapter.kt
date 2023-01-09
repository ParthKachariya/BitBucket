package com.main.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.main.myapplication.databinding.UserlistItemBinding
import com.main.myapplication.model.UserFollowersModelItem
import com.main.myapplication.utils.AdapterClickListener

class UserFollowerAdapter(
    var list: ArrayList<UserFollowersModelItem>,
    private val listener: AdapterClickListener
) :
    RecyclerView.Adapter<UserFollowerAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: UserlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(userItem: UserFollowersModelItem) {
            binding.txtUserName.text = userItem.login
            binding.txtUrl.text = userItem.url

            Glide.with(binding.root.context)
                .load(userItem.avatar_url)
                .into(binding.ivUserImage)

            binding.cvMain.setOnClickListener {
                listener.getclickedFollower(userItem)
            }
        }
    }

    fun updateAllData(item: ArrayList<UserFollowersModelItem>) {
        list = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}