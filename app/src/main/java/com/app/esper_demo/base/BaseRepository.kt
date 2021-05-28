package com.app.esper_demo.base

import com.app.esper_demo.utils.ApiResponseWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    protected suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ApiResponseWrapper<T> {
        return withContext(dispatcher) {
            try {
                ApiResponseWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ApiResponseWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = getErrorMessage(throwable.response()?.errorBody())
                        ApiResponseWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        //handle according to use case
                        ApiResponseWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String? {
        return try {
            val jsonObject = JSONObject(responseBody?.string())
            val errorObject = jsonObject.getJSONObject("error")
            errorObject.getString("message")
        } catch (e: Exception) {
            e.message
        }
    }
}