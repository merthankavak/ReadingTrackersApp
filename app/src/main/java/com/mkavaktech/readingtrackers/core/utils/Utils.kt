package com.mkavaktech.readingtrackers.core.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel

import java.text.DateFormat

fun saveToFirebase(book: CustomBookModel, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    if (book.toString().isNotEmpty()) dbCollection.add(book)
        .addOnSuccessListener { documentRef ->
            val docId = documentRef.id
            dbCollection.document(docId)
                .update(hashMapOf("id" to docId) as Map<String, Any>)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) navController.popBackStack()
                }.addOnFailureListener { Log.w("Error", "SaveToFirebase:  Error: ", it) }
        }
}

fun formatDate(timestamp: Timestamp): String {
    return DateFormat.getDateInstance().format(timestamp.toDate()).toString()
        .split(",")[0] // March 12
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG)
        .show()
}