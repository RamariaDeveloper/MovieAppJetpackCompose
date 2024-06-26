package br.com.movieapp.features.detail.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.features.detail.domain.repository.MovieDetailsRepository
import br.com.movieapp.features.detail.domain.source.MovieDetailRemoteDataSource
import br.com.movieapp.framework.domain.model.Movie
import br.com.movieapp.framework.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return remoteDataSource.getMoviesDetails(movieId = movieId)
    }

    override suspend fun getMovieSimilar(
        movieId: Int,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getSimilarMoviesPagingSource(movieId = movieId)
            }
        ).flow
    }
}