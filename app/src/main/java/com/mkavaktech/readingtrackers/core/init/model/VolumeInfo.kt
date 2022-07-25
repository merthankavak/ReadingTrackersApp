package com.mkavaktech.readingtrackers.core.init.model

data class VolumeInfo(
    val authors: List<String>,
    val categories: List<String>,
    val contentVersion: String,
    val description: String,
    val imageLinks: ImageLinks,
    val infoLink: String,
    val pageCount: Int,
    val publishedDate: String,
    val publisher: String,
    val ratingsCount: Int,
    val subtitle: String,
    val title: String
)