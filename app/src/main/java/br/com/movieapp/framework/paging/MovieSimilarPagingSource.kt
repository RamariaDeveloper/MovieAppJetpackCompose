package br.com.movieapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.features.detail.domain.source.MovieDetailRemoteDataSource
import br.com.movieapp.features.popular.data.mapper.toMovie
import br.com.movieapp.framework.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MovieSimilarPagingSource(
    private val remoteDataSource: MovieDetailRemoteDataSource,
    private val movieId: Int
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {

            val pageNumber = params.key ?: 1
            val response = remoteDataSource.getMovieSimilar(page = pageNumber, movieId = movieId)

            val movies = response.results

            LoadResult.Page(
                data = movies.toMovie(),
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (movies.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val LIMIT = 20
    }

}