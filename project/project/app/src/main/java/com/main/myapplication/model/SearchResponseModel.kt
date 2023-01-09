package com.main.myapplication.model

data class SearchResponseModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)