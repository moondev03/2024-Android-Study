package com.example.kotlinflow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinflow.repository.GithubRepository

class MainViewModelFactory(
    private val githubRepository: GithubRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GithubRepository::class.java).newInstance(githubRepository)
    }
}