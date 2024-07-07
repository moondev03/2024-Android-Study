package com.example.kotlinflow.repository

import com.example.kotlinflow.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

abstract class BaseFlowResponse {

    protected fun <T> getResult(call: suspend () -> Response<T>): Flow<ApiState<T & Any>> = flow {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it))
                } ?: emit(ApiState.Error("Empty response body"))
            } else {
                try {
                    emit(ApiState.Error(response.errorBody()?.string() ?: "Unknown error"))
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(ApiState.Error("Error reading error body"))
                }
            }
        } catch (e: Exception) {
            emit(ApiState.Error(e.message ?: "An unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}