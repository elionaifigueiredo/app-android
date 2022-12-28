package com.example.aula.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team (
    @SerializedName("nome")
    val nome: String,
    @SerializedName("forca")
    val stars: Int,
    @SerializedName("imagem")
    val image: String,
    // leva o score para o random da classe main
    var score: Int?
    ): Parcelable