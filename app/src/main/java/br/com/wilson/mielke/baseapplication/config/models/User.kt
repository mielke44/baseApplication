package br.com.wilson.mielke.baseapplication.config.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("eventId")
    var eventId: String? = "0",
    @SerializedName("name")
    var name: String? = null
): Parcelable