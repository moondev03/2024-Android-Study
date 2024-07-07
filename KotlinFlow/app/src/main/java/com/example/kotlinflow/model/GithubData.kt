package com.example.kotlinflow.model

import com.google.gson.annotations.SerializedName

data class GithubData(
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GithubItem>,
    @SerializedName("total_count") val totalCount: Int
)
