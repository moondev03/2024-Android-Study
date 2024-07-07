package com.example.kotlinflow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflow.model.GithubData
import com.example.kotlinflow.repository.GithubRepository
import com.example.kotlinflow.utils.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(
    private val githubRepository: GithubRepository
): ViewModel() {
    private var _githubRepositories: MutableStateFlow<ApiState<GithubData>> = MutableStateFlow(ApiState.Loading())
    var githubRepositories: StateFlow<ApiState<GithubData>> = _githubRepositories

    fun requestGithubRepositories(query: String) = viewModelScope.launch {
        _githubRepositories.value = ApiState.Loading()

        githubRepository.getRepositories(query)
            .catch { error ->
                _githubRepositories.value = ApiState.Error("${error.message}")
            }
            .collect { values ->
                _githubRepositories.value = values
            }
    }
}