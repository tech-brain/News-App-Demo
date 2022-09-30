package com.newsappmvvm.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CategoryModel(
    @SerializedName("category")
    val category: String?="",
    @SerializedName("selected")
    var selected: Boolean?=false
) : Parcelable