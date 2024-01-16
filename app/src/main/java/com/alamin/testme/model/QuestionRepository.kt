package com.alamin.testme.model

import android.util.Log
import com.alamin.testme.model.data.NetworkResponse
import com.alamin.testme.model.data.Question
import com.alamin.testme.model.data.QuestionResponse
import com.alamin.testme.model.network.APIInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.http.Query
import javax.inject.Inject

private const val TAG = "QuestionRepository"
class QuestionRepository @Inject constructor(private val apiInterface: APIInterface) {

    private val _questionResponse = MutableSharedFlow<NetworkResponse<List<Question>>>()
    val questionResponse = _questionResponse.asSharedFlow()


    suspend fun requestQuestion(amount:Int,
                        categoryId:Int,
                        difficulty:String,
                       type:String){
        _questionResponse.emit(NetworkResponse.Loading())
        try {
            val response = apiInterface.questions(amount,categoryId,difficulty,type)

            if (response.isSuccessful){

                Log.d(TAG, "requestQuestion: ${response.body()} ")

                if (response.body() != null){
                    _questionResponse.emit(NetworkResponse.Success(response.body()!!.questions))
                }else{
                    _questionResponse.emit(NetworkResponse.Error("No Data Found"))
                }

            }else{
                 _questionResponse.emit(NetworkResponse.Error("Failed to Load (${response.code()})"))
            }
        }catch (e:Exception){
            _questionResponse.emit(NetworkResponse.Error("Exception"))

        }
    }

}