package com.mkavaktech.readingtrackers.core.init.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.mkavaktech.readingtrackers.core.init.data.DataOrException
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBook: Query
) {
    suspend fun getAllBooksFromDatabase(): DataOrException<List<CustomBookModel>, Boolean, Exception> {
        val dataOrException = DataOrException<List<CustomBookModel>, Boolean, Exception>()
        try {
            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(CustomBookModel::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }
        return dataOrException
    }
}