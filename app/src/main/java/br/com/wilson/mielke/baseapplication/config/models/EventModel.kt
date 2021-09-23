package br.com.wilson.mielke.baseapplication.config.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventModel(
    @SerializedName("date")
    var date: Long,
    @SerializedName("description")
    var description: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("people")
    var people: List<User>,
    @SerializedName("price")
    var price: Double,
    @SerializedName("title")
    var title: String
): Parcelable