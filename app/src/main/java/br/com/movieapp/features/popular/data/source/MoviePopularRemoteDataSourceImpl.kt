package br.com.movieapp.features.popular.data.source

import br.com.movieapp.features.popular.domain.source.MoviePopularRemoteDataSource
import br.com.movieapp.remote.MovieService
import br.com.movieapp.remote.model.MovieResponse

class MoviePopularRemoteDataSourceImpl constructor(
    private val service: MovieService
) : MoviePopularRemoteDataSource {
    override suspend fun getPopularMovies(page: Int): MovieResponse {
        return service.getPopularMovies(page = page)
    }
}