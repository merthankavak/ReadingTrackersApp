package com.mkavaktech.readingtrackers.view.main.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mkavaktech.readingtrackers.core.components.appbar.AppBar
import com.mkavaktech.readingtrackers.core.components.card.BookStatsCard
import com.mkavaktech.readingtrackers.core.components.card.WelcomeCard
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel
import com.mkavaktech.readingtrackers.view.main.home.viewmodel.HomeViewModel


@ExperimentalMaterial3Api
@Composable
fun StatsView(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Book Stats",
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(NavigationEnums.HomeView.name) }) {
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
        ) { StatsContent(Modifier, viewModel) }
    }
}

@ExperimentalMaterial3Api
@Composable
fun StatsContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    var books: List<CustomBookModel>
    val currentUser = FirebaseAuth.getInstance().currentUser
    val customUserName =
        if (!currentUser?.email.isNullOrEmpty()) currentUser?.email?.split("@")?.first() else "NoN"

    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        books =
            if (!viewModel.data.value.data.isNullOrEmpty()) viewModel.data.value.data!!.filter {
                (it.userId == currentUser?.uid)
            } else emptyList()
        Spacer(modifier = modifier.height(60.dp))
        WelcomeCard(modifier = modifier, customUserName = customUserName!!)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RectangleShape, elevation = CardDefaults.cardElevation(5.dp),
        ) {
            val readBooksList: List<CustomBookModel> =
                if (!viewModel.data.value.data.isNullOrEmpty()) books.filter { (it.userId == currentUser?.uid) && (it.finishedReading != null) } else emptyList()
            val readingBooks =
                books.filter { (it.startedReading != null && it.finishedReading == null) }

            Column(
                modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Your Stats", style = MaterialTheme.typography.bodyMedium)
                Divider()
                Text(text = "You're reading: ${readingBooks.size} books")
                Text(text = "You've read: ${readBooksList.size} books")
            }
        }

        if (viewModel.data.value.loading == true) LinearProgressIndicator() else {
            Divider()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), contentPadding = PaddingValues(16.dp)
            ) {
                val readBooks: List<CustomBookModel> =
                    if (!viewModel.data.value.data.isNullOrEmpty()) viewModel.data.value.data!!.filter { (it.userId == currentUser?.uid) && (it.finishedReading != null) } else emptyList()
                items(items = readBooks) { BookStatsCard(book = it) }
            }
        }
    }
}

