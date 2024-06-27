package com.example.recipesearchapp.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class result(
    val id:Int,
    val title:String,
    val image:String):Parcelable

data class ResultResponse(
    val results:List<result>
)