package com.example.firebasecursogaston.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.firebasecursogaston.R
import com.example.firebasecursogaston.core.Resource
import com.example.firebasecursogaston.core.hide
import com.example.firebasecursogaston.core.show
import com.example.firebasecursogaston.data.model.Post
import com.example.firebasecursogaston.data.remote.home.HomeScreenDataSource
import com.example.firebasecursogaston.databinding.FragmentHomeScreenBinding
import com.example.firebasecursogaston.domain.home.HomeScreenRepoImpl
import com.example.firebasecursogaston.presentation.home.HomeScreenViewModel
import com.example.firebasecursogaston.presentation.home.HomeScreenViewModelFactory
import com.example.firebasecursogaston.ui.home.adapter.HomeScreenAdapter
import com.example.firebasecursogaston.ui.home.adapter.OnPostClickListener

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), OnPostClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
                HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    if(result.data.isEmpty()) {
                        binding.emptyContainer.show()
                        return@Observer
                    }else{
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data, this)
                }

                is Resource.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onLikeButtonClick(post: Post, liked: Boolean) {
        viewModel.registerLikeButtonState(post.id, liked).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("Like Transaction","in progress...")
                }

                is Resource.Success -> {
                    Log.d("Like Transaction","Success")
                }

                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
