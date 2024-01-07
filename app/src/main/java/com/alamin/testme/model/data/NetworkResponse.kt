package com.alamin.testme.model.data

sealed class NetworkResponse<T>(val data: T? = null,val message:String? = null){

    class Loading<T>():NetworkResponse<T>()
    class Empty<T>():NetworkResponse<T>()
    class Success<T>(data: T?):NetworkResponse<T>(data = data)
    class Error<T>(message:String?):NetworkResponse<T>(message = message)

}