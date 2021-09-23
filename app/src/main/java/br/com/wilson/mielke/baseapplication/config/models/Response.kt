package br.com.wilson.mielke.baseapplication.config.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response (
    @SerializedName("code")
    var code: String
): Parcelable