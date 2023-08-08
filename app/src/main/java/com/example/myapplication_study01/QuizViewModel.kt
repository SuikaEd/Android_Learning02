package com.example.myapplication_study01

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel(){
    /*
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
     */

    //声明问题序号
    var currentIndex = 0
    var trueNumber = 0

    //声明问题列表
    val questionBank = listOf(
        Question(R.string.question_first, true),
        Question(R.string.question_second, true),
        Question(R.string.question_third, true),
        Question(R.string.question_forth, true),
        Question(R.string.question_fifth, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

}