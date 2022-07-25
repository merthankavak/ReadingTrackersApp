package com.mkavaktech.readingtrackers.view.main.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mkavaktech.readingtrackers.R
import com.mkavaktech.readingtrackers.core.components.appbar.AppBar
import com.mkavaktech.readingtrackers.core.components.button.HomeFloatActionButton
import com.mkavaktech.readingtrackers.core.components.card.BookInfoCard
import com.mkavaktech.readingtrackers.core.components.card.WelcomeCard
import com.mkavaktech.readingtrackers.core.components.text.CustomTitle
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel
import com.mkavaktech.readingtrackers.view.main.home.viewmodel.HomeViewModel


@ExperimentalMaterial3Api
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.app_name),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(NavigationEnums.StatsView.name) }) {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "Account Icon",
                            tint = Color.Blue.copy(alpha = 0.7f)
                        )
                    }
                },
                onAction = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut().run {
                            navController.navigate(NavigationEnums.LoginView.name)
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Logout, contentDescription = "Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            HomeFloatActionButton { navController.navigate(NavigationEnums.SearchView.name) }
        },
    )
    {
        it
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) { HomeContent(modifier, navController, viewModel) }
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {
    var listOfBooks = emptyList<CustomBookModel>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList()
            .filter { it.userId == currentUser?.uid.toString() }
        Log.d("Books", "HomeContent: $listOfBooks")
    }
    val email = currentUser?.email
    val customUserName = if (!email.isNullOrEmpty()) email.split("@").first() else "NoN"

    Column(
        modifier = modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = modifier.height(60.dp))
        WelcomeCard(modifier, customUserName)
        CustomTitle(label = "Reading Now")
        ReadingNowContent(books = listOf(), navController)
        CustomTitle(label = "My Book List")
        BooksListContent(books = listOfBooks, navController = navController)
    }
}

@ExperimentalMaterial3Api
@Composable
fun BooksListContent(
    books: List<CustomBookModel>,
    navController: NavController,
) {
    val addedBooks =
        books.filter { customBookModel -> customBookModel.startedReading == null && customBookModel.finishedReading == null }

    HorizontalListView(addedBooks) { navController.navigate(NavigationEnums.UpdateView.name + "/$it") }
}

@ExperimentalMaterial3Api
@Composable
fun HorizontalListView(
    books: List<CustomBookModel>,
    viewModel: HomeViewModel = hiltViewModel(),
    onPress: (String) -> Unit
) {
    LazyRow {
        items(books) { book ->
            if (viewModel.data.value.loading == true) LinearProgressIndicator() else if (books.isEmpty()) Surface(
                modifier = Modifier.padding(23.dp)
            ) {
                Text(
                    text = "No books found. Add a Book",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Red.copy(alpha = 0.4f))
                )
            } else BookInfoCard(book = book) { onPress(book.googleBookId.toString()) }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ReadingNowContent(books: List<CustomBookModel>, navController: NavController) {
    val readingNowList = books.filter {
        it.startedReading.toString()
            .isNotEmpty() && it.finishedReading.toString()
            .isEmpty()
    }
    HorizontalListView(readingNowList) { navController.navigate(NavigationEnums.UpdateView.name + "/$it") }
}



