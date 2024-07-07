package com.example.kotlinflow.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinflow.databinding.ActivityMainBinding
import com.example.kotlinflow.repository.GithubRepository
import com.example.kotlinflow.utils.ApiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "123"
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private val viewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory(GithubRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        getGithubRepositories("retrofit")
    }

    private fun getGithubRepositories(query: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    requestGithubRepositories(query)
                    githubRepositories.collect {
                        when (it) {
                            is ApiState.Success -> {
                                it.data?.let { data ->
                                    Log.i(TAG, "data - incomplete_results : ${data.incompleteResults}")
                                    val list = data.items
                                    for (i in list.indices) {
                                        Log.i(TAG, "license : ${list[i].license}")
                                    }
                                }
                            }
                            is ApiState.Error -> {
                                Log.e(TAG, "## 에러 : ${it.message}")
                            }
                            is ApiState.Loading -> {}
                        }
                    }
                }
            }
        }
    }
}