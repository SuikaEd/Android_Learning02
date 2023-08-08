package com.example.myapplication_study01

import androidx.annotation.StringRes

    //question类封装问题文本和问题答案
data class Question(@StringRes val textResId: Int, val answer: Boolean)