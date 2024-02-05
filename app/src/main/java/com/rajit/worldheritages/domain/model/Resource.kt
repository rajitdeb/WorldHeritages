package com.rajit.worldheritages.domain.model

// This class is responsible for determining the state of DB Transaction
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T):Resource<T>(data)

    class Error<T>(message: String): Resource<T>(null, message)

}