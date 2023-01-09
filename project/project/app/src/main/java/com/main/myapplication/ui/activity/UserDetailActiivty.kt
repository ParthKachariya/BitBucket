package com.main.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.main.myapplication.R
import com.main.myapplication.constant.Constant
import com.main.myapplication.databinding.ActivityUserDetailActiivtyBinding
import com.main.myapplication.model.UserFollowersModelItem
import com.main.myapplication.ui.BaseActivity
import com.main.myapplication.ui.adapter.UserFollowerAdapter
import com.main.myapplication.utils.*
import com.main.myapplication.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailActiivty : BaseActivity<ActivityUserDetailActiivtyBinding>(), View.OnClickListener {

    private val githubViewModel: GithubViewModel by viewModel()

    private val userFollowerList: ArrayList<UserFollowersModelItem> = ArrayList()
    private val userFollowingList: ArrayList<UserFollowersModelItem> = ArrayList()

    var username = ""

    private val adapterFollower: UserFollowerAdapter =
        UserFollowerAdapter(userFollowerList, object : AdapterClickListener() {
            override fun getclickedFollower(item: UserFollowersModelItem) {
                super.getclickedFollower(item)

                startActivity(
                    Intent(
                        this@UserDetailActiivty,
                        FollowerOrFolloingDetailActivity::class.java
                    ).putExtra(Constant.USERNAME, item.login)
                )
            }
        })

    private val adapterFollowing: UserFollowerAdapter =
        UserFollowerAdapter(userFollowingList, object : AdapterClickListener() {
            override fun getclickedFollower(item: UserFollowersModelItem) {
                super.getclickedFollowing(item)
                startActivity(
                    Intent(
                        this@UserDetailActiivty,
                        FollowerOrFolloingDetailActivity::class.java
                    ).putExtra(Constant.USERNAME, item.login)
                )
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        click()

        binding.rvFollowersList.adapter = adapterFollower
        binding.rvFollowingList.adapter = adapterFollowing

        binding.rvFollowersList.show()
        binding.rvFollowingList.hide()

        if (intent != null) {
            username = intent.getStringExtra(Constant.USERNAME).toString()
            if (!username.equals("")) {
                username.let { getUserDetail(it) }
                username.let { getUserFollowers(it) }
                username.let { getUserFollowing(it) }
            } else {
                Toast.makeText(this, "Someting went's wrong!", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position
                Log.e(TAG, "onTabSelected: " + pos)
                if (pos == 0) {
                    if (userFollowerList.size > 0) {
                        binding.rvFollowersList.show()
                        binding.rvFollowingList.hide()
                        binding.txtEmptyText.hide()
                    } else {
                        binding.rvFollowersList.hide()
                        binding.rvFollowingList.hide()
                        binding.txtEmptyText.show()
                    }
                } else if (pos == 1) {
                    if (userFollowingList.size > 0) {
                        binding.rvFollowersList.hide()
                        binding.rvFollowingList.show()
                        binding.txtEmptyText.hide()
                    } else {
                        binding.rvFollowersList.hide()
                        binding.rvFollowingList.hide()
                        binding.txtEmptyText.show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun click() {
        binding.ivBack.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_user_detail_actiivty
    }

    fun getUserDetail(username: String) {
        githubViewModel.getUserDetail(username).observe(this) {
            when (it) {
                is ResponseUser.Loading -> {
                    binding.progressBar.show()
                }

                is ResponseUser.Success<*> -> {
                    Log.e(TAG, "getUserDetail: " + it.data)
                    binding.txtUserName.text = it.data?.name
                    binding.txtGitName.text = it.data?.repos_url
                    binding.txtFollowers.text = it.data?.followers.toString()
                    binding.txtFollowing.text = it.data?.following.toString()

                    Glide.with(this)
                        .load(it.data?.avatar_url)
                        .into(binding.ivUserImage)
                    binding.progressBar.hide()
                }

                is ResponseUser.Failed -> {
                    binding.progressBar.hide()
                    Toast.makeText(this, resources.getString(R.string.someting_wents_wrong), Toast.LENGTH_LONG).show()
                    Log.e(TAG, "getUserDetail: " + it.msg)
                }
                else -> {}
            }
        }
    }

    fun getUserFollowers(username: String) {
        githubViewModel.getUserFollowers(username).observe(this) {
            when (it) {
                is ResponseFollowers.Loading -> {
                    binding.progressBar.show()
                }

                is ResponseFollowers.Success<*> -> {
                    Log.e(TAG, "getUserFollowers: " + it.data)
                    userFollowerList.clear()
                    val res = it.data
                    if (res != null && res.isNotEmpty()) {
                        userFollowerList.addAll(res)
                        Log.e(TAG, "getUserFollowers: " + userFollowerList.size)
                        adapterFollower.updateAllData(userFollowerList)
                    }
                    binding.progressBar.hide()
                }

                is ResponseFollowers.Failed -> {
                    binding.progressBar.hide()
                    Toast.makeText(this, resources.getString(R.string.someting_wents_wrong), Toast.LENGTH_LONG).show()
                    Log.e(TAG, "getUserFollowers: " + it.msg)
                }
                else -> {}
            }
        }
    }

    fun getUserFollowing(username: String) {
        githubViewModel.getUserFollowing(username).observe(this) {
            when (it) {
                is ResponseFollowers.Loading -> {
                    binding.progressBar.show()
                }

                is ResponseFollowers.Success<*> -> {
                    Log.e(TAG, "getUserFollowing: " + it.data)
                    userFollowingList.clear()
                    val res = it.data
                    if (res != null && res.isNotEmpty()) {
                        userFollowingList.addAll(res)
                        adapterFollowing.updateAllData(userFollowingList)
                    }
                    binding.progressBar.hide()
                }

                is ResponseFollowers.Failed -> {
                    Toast.makeText(this, resources.getString(R.string.someting_wents_wrong), Toast.LENGTH_LONG).show()
                    Log.e(TAG, "getUserFollowing: " + it.msg)
                    binding.progressBar.hide()
                }
                else -> {}
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivBack -> {
                finish()
            }
        }
    }
}