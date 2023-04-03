package com.halowidy.githubusers.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.halowidy.githubusers.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME ="extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            showLoading(true)
            if (it != null) {
                showLoading(false)
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} followers"
                    tvFollowing.text = "${it.following} following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(ivProfile)
                }

            }
        }
        val sectionPagerAdapter = sectionPager(this, supportFragmentManager, bundle)
        binding.apply {
           viewPage.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPage)
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