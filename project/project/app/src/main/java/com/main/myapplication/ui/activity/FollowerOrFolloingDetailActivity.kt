package com.main.myapplication.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.main.myapplication.R
import com.main.myapplication.constant.Constant
import com.main.myapplication.databinding.ActivityFollowerOrFolloingDetailBinding
import com.main.myapplication.ui.BaseActivity
import com.main.myapplication.utils.ResponseUser
import com.main.myapplication.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerOrFolloingDetailActivity : BaseActivity<ActivityFollowerOrFolloingDetailBinding>() {

    private val githubViewModel: GithubViewModel by viewModel()

    var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null) {
            username = intent.getStringExtra(Constant.USERNAME).toString()
            if (!username.equals("")) {
                username.let { getUserDetail(it) }
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    fun getUserDetail(username: String) {
        githubViewModel.getUserDetail(username).observe(this) {
            when (it) {
                is ResponseUser.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
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
                    binding.progressBar.visibility = View.GONE
                }

                is ResponseUser.Failed -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resources.getString(R.string.someting_wents_wrong), Toast.LENGTH_LONG).show()
                    Log.e(TAG, "getUserDetail: " + it.msg)
                }
                else -> {}
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_follower_or_folloing_detail
    }
}
