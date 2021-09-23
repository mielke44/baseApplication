package br.com.wilson.mielke.baseapplication.config.api

import br.com.wilson.mielke.baseapplication.config.models.EventModel
import br.com.wilson.mielke.baseapplication.config.models.Response
import br.com.wilson.mielke.baseapplication.config.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST

interface RESTApi {
    @GET("events")
    suspend fun getEvents() : ArrayList<EventModel>

    @GET("events/{id}")
    suspend fun  getSingleEvent(@Path("id") id: Int) : EventModel

    @POST("checkin")
    suspend fun sendUserCheckIn(@Body body: User) : Response
}