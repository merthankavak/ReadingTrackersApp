package com.mkavaktech.readingtrackers.view.main.update

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mkavaktech.readingtrackers.R
import com.mkavaktech.readingtrackers.core.components.appbar.AppBar
import com.mkavaktech.readingtrackers.core.components.button.RoundedButton
import com.mkavaktech.readingtrackers.core.components.card.BookUpdateCard
import com.mkavaktech.readingtrackers.core.components.card.RatingCard
import com.mkavaktech.readingtrackers.core.components.textfield.CommentTextField
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.core.init.data.DataOrException
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel
import com.mkavaktech.readingtrackers.core.utils.formatDate
import com.mkavaktech.readingtrackers.core.utils.showToast
import com.mkavaktech.readingtrackers.view.main.home.viewmodel.HomeViewModel

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun UpdateView(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Update Book",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = Color.Blue.copy(alpha = 0.7f)
                        )
                    }
                },
            )
        }) {
        it
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) { UpdateContent(navController, bookItemId, viewModel) }
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun UpdateContent(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeViewModel
) {
    val bookInfo = produceState<DataOrException<List<CustomBookModel>, Boolean, Exception>>(
        initialValue = DataOrException(data = emptyList(), true, Exception(""))
    ) { value = viewModel.data.value }.value

    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        if (bookInfo.loading == true) {
            LinearProgressIndicator()
            bookInfo.loading = false
        } else {
            ShowBookInfo(bookInfo = viewModel.data.value, bookItemId = bookItemId)
            ShowCommentArea(
                book = viewModel.data.value.data?.first { mBook -> mBook.googleBookId == bookItemId }!!,
                navController as NavHostController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBookInfo(
    bookInfo: DataOrException<List<CustomBookModel>,
            Boolean, Exception>, bookItemId: String
) {
    Row {
        if (bookInfo.data != null) Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            BookUpdateCard(modifier = Modifier, bookData = bookInfo.data!!.first { book ->
                book.googleBookId == bookItemId
            })
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ShowCommentArea(
    book: CustomBookModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val notesText = remember { mutableStateOf("") }
    val isStartedReading = remember { mutableStateOf(false) }
    val isFinishedReading = remember { mutableStateOf(false) }
    val ratingVal = remember { mutableStateOf(0) }

    CommentTextField(
        defaultValue = book.notes.toString().ifEmpty { "No Comments." }
    ) { note -> notesText.value = note }
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = { isStartedReading.value = true },
            enabled = book.startedReading == null
        ) {
            if (book.startedReading == null) {
                if (!isStartedReading.value) Text(text = "Start Reading") else Text(
                    text = "Started Reading!",
                    modifier = Modifier.alpha(0.6f),
                    color = Color.Red.copy(alpha = 0.5f)
                )
            } else Text("Started on: ${formatDate(book.startedReading!!)}")
        }
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(
            onClick = { isFinishedReading.value = true },
            enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value) Text(text = "Mark as Read") else Text(text = "Finished Reading!")
            } else Text(text = "Finished on: ${formatDate(book.finishedReading!!)}")
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))
    Spacer(modifier = Modifier.height(5.dp))
    book.rating?.toInt().let { RatingCard(rating = it!!) { rating -> ratingVal.value = rating } }
    Spacer(modifier = Modifier.height(15.dp))
    Row {
        val changedNotes = book.notes != notesText.value
        val changedRating = book.rating?.toInt() != ratingVal.value
        val isFinishedTimeStamp =
            if (isFinishedReading.value) Timestamp.now() else book.finishedReading
        val isStartedTimeStamp =
            if (isStartedReading.value) Timestamp.now() else book.startedReading
        val bookUpdate =
            changedNotes || changedRating || isStartedReading.value || isFinishedReading.value
        val bookToUpdate = hashMapOf(
            "finished_reading_at" to isFinishedTimeStamp,
            "started_reading_at" to isStartedTimeStamp,
            "rating" to ratingVal.value,
            "notes" to notesText.value
        ).toMap()
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            ShowAlertDialog(
                message = stringResource(id = R.string.sure) + "\n" +
                        stringResource(id = R.string.action), openDialog
            ) {
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            openDialog.value = false
                            navController.navigate(NavigationEnums.HomeView.name)
                        }
                    }
            }
        }
        RoundedButton(label = "Update") {
            if (bookUpdate) FirebaseFirestore.getInstance()
                .collection("books")
                .document(book.id!!)
                .update(bookToUpdate)
                .addOnCompleteListener {
                    showToast(context, "Book Updated Successfully!")
                    navController.navigate(NavigationEnums.HomeView.name)
                }.addOnFailureListener { Log.w("Error", "Error updating document", it) }
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.1f))
        RoundedButton(modifier = Modifier, "Delete") { openDialog.value = true }
    }
}

@Composable
fun ShowAlertDialog(
    message: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Book") },
            text = { Text(text = message) },
            confirmButton = { TextButton(onClick = { onYesPressed.invoke() }) { Text(text = "Yes") } },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) { Text(text = "No") }
            })
    }
}


