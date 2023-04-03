package com.halowidy.githubusers.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.halowidy.githubusers.User
import com.halowidy.githubusers.databinding.ActivityMainBinding
import com.halowidy.githubusers.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: UserVewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
               Intent(this@MainActivity, DetailUserActivity::class.java).also{
                   it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                   startActivity(it)
               }
            }
        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserVewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

        btnSearch.setOnClickListener {
                searchUser()
            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }


        private fun searchUser(){
                binding.apply {
                    val query = etQuery.text.toString()
                    if (query.isEmpty()) return
                    showLoading(true)
                    viewModel.setSearchUser(query)
                }
            }

       private fun showLoading(state : Boolean){
            if(state){
                binding.progressBarUser.visibility = View.VISIBLE
            }else{
                binding.progressBarUser.visibility = View.GONE
            }

        }
    }
