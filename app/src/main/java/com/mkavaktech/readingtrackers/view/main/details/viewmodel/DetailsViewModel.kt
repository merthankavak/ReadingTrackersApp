package com.mkavaktech.readingtrackers.view.main.details.viewmodel

import androidx.lifecycle.ViewModel
import com.mkavaktech.readingtrackers.core.init.data.Resource
import com.mkavaktech.readingtrackers.core.init.model.Item
import com.mkavaktech.readingtrackers.core.init.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId)
    }
}