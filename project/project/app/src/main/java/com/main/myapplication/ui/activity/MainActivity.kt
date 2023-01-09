package com.main.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import com.main.myapplication.R
import com.main.myapplication.constant.Constant
import com.main.myapplication.databinding.ActivityMainBinding
import com.main.myapplication.model.Item
import com.main.myapplication.ui.BaseActivity
import com.main.myapplication.ui.adapter.UserListAdapterNew
import com.main.myapplication.utils.AdapterClickListener
import com.main.myapplication.utils.ResponseSearch
import com.main.myapplication.utils.hide
import com.main.myapplication.utils.show
import com.main.myapplication.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val githubViewModel: GithubViewModel by viewModel()
    private var searchedUserName = ""

    private val usersItemList: ArrayList<Item> = ArrayList()

    private val adapter: UserListAdapterNew =
        UserListAdapterNew(usersItemList, object : AdapterClickListener() {
            override fun getclickedUser(item: Item) {
                super.getclickedUser(item)

                startActivity(
                    Intent(
                        this@MainActivity,
                        UserDetailActiivty::class.java
                    ).putExtra(Constant.USERNAME, item.login)
                )
            }
        })

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.e(TAG, "onTextChanged: " + s)
            searchedUserName = s.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvUserList.adapter = adapter
        adapter.updateAllData(usersItemList)

        getUserList(searchedUserName)

        binding.edtSearch.addTextChangedListener(textWatcher)
        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getUserList(searchedUserName)
                return@OnEditorActionListener true
            }
            false
        })

        binding.refresh.setOnRefreshListener {
            getUserList(searchedUserName)
        }
    }

    fun getUserList(username: String) {
        githubViewModel.getSearchData(username).observe(this) {
            when (it) {
                is ResponseSearch.Loading -> {
                    if (!binding.refresh.isRefreshing) binding.progressBar.show()
                }

                is ResponseSearch.Success<*> -> {
                    usersItemList.clear()
                    val res = it.data
                    Log.e(TAG, "getUserList: ")
                    if (res?.items != null && res.items.isNotEmpty()) {
                        binding.rvUserList.show()
                        binding.txtEmptyText.hide()
                        usersItemList.addAll(res.items)
                        adapter.updateAllData(usersItemList)
                    } else {
                        binding.txtEmptyText.show()
                        binding.rvUserList.hide()
                    }
                    if (!binding.refresh.isRefreshing) binding.progressBar.hide()
                    else binding.refresh.isRefreshing = false
                }

                is ResponseSearch.Failed -> {
                    if (!binding.refresh.isRefreshing) binding.progressBar.hide()
                    else binding.refresh.isRefreshing = false
                    binding.txtEmptyText.show()
                    binding.rvUserList.hide()
                    Toast.makeText(this, resources.getString(R.string.someting_wents_wrong), Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}