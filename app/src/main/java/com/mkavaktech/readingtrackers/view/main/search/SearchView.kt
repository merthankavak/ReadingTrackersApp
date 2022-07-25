package com.mkavaktech.readingtrackers.view.main.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mkavaktech.readingtrackers.core.components.appbar.AppBar
import com.mkavaktech.readingtrackers.core.components.card.BookListCard
import com.mkavaktech.readingtrackers.core.components.textfield.SearchTextField
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.view.main.search.viewmodel.SearchViewModel


@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Search Book",
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
        ) { SearchContent(modifier, navController, viewModel) }
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun SearchContent(modifier: Modifier, navController: NavController, viewModel: SearchViewModel) {
    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        Spacer(modifier = modifier.height(60.dp))
        SearchTextField { viewModel.searchBooks(it) }
        Spacer(modifier = modifier.height(20.dp))
        BookList(modifier, navController, viewModel)
    }
}

@ExperimentalMaterial3Api
@Composable
fun BookList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val listOfBooks = viewModel.list
    if (viewModel.isLoading) LinearProgressIndicator() else LazyColumn {
        items(listOfBooks) {
            BookListCard(modifier, it, navController)
        }
    }
}


