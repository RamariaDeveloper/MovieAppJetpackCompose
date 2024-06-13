package br.com.movieapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.features.popular.data.mapper.toMovie
import br.com.movieapp.features.popular.domain.source.MoviePopularRemoteDataSource
import br.com.movieapp.framework.domain.model.Movie
import okio.IOException

class MoviePaggingSource(
    private val remoteDataSource: MoviePopularRemoteDataSource
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1
            val response = remoteDataSource.getPopularMovies(page = pageNumber)
            val movies = response.results

            LoadResult.Page(
                data = movies.toMovie(),
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (movies.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}