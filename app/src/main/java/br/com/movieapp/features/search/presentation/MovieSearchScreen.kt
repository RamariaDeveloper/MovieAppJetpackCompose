package br.com.movieapp.features.search.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.features.search.presentation.components.SearchContent
import br.com.movieapp.features.search.presentation.state.MovieSearchState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@Composable
fun MovieSearchScreen(
    uiState: MovieSearchState,
    onEvent: (MovieSearchEvent) -> Unit,
    onFetch: (String) -> Unit,
    navigateToDetailMovie: (Int) -> Unit
) {
    val pagingMovies = uiState.movies.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search_movies),
                        color = white
                    )
                },
                backgroundColor = black
            )
        },
        content = { paddingValues ->
            SearchContent(
                paddingValues = paddingValues,
                pagingMovies = pagingMovies,
                query = uiState.query,
                onSearch = {
                    onFetch(it)
                },
                onEvent = {
                    onEvent(it)
                },
                onDetails = { movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}