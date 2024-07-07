package com.example.kotlinflow.repository

import com.example.kotlinflow.model.GithubData
import com.example.kotlinflow.remote.GithubService
import com.example.kotlinflow.remote.RetrofitClient
import com.example.kotlinflow.utils.ApiState
import kotlinx.coroutines.flow.Flow

class GithubRepository: BaseFlowResponse() {
    private val githubClient = RetrofitClient.getInstance().create(GithubService::class.java)

    suspend fun getRepositories(queryString: String): Flow<ApiState<GithubData>> {
        return getResult { githubClient.getRepositories(query = queryString) }
    }
}