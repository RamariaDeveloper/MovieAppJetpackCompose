package br.com.movieapp.framework.utils

import java.lang.Exception

sealed class ResultData<out T> {

    object loading: ResultData<Nothing>()
    data class Success<out T>(val data: T?): ResultData<T>()
    data class Failure(val e: Exception?): ResultData<Nothing>()
}