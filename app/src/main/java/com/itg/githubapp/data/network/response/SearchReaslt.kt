package com.itg.githubapp.data.network.response

import com.google.gson.annotations.SerializedName


data class SearchReaslt(
    @SerializedName("total_count") val login: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: List<Repository>
)