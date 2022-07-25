package com.mkavaktech.readingtrackers.core.init.model

data class BookModel(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)