package com.halowidy.githubusers.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.halowidy.githubusers.R
import com.halowidy.githubusers.databinding.FragmentFollowBinding
import com.halowidy.githubusers.ui.UserAdapter

class FollowerFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        viewModel.setListFollower(username)
        viewModel.getListfollower().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state : Boolean){
        if(state){
            binding.progressBarUser.visibility = View.VISIBLE
        }else{
            binding.progressBarUser.visibility = View.GONE
        }

    }
}

