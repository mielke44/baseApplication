package br.com.wilson.mielke.baseapplication.config.core

import br.com.wilson.mielke.baseapplication.config.api.RESTApi
import br.com.wilson.mielke.baseapplication.config.models.EventModel
import br.com.wilson.mielke.baseapplication.config.models.Response
import br.com.wilson.mielke.baseapplication.config.models.User

class NetworkInteractor(private val api: RESTApi) {

    suspend fun getEvents(): Result<List<EventModel>> {
        return runCatching { api.getEvents() }
    }

    suspend fun getSingleEvents(eventId: Int): Result<EventModel> {
        return runCatching { api.getSingleEvent(eventId) }
    }

    suspend fun sendUserCheckIn(user: User): Result<Response> {
        return kotlin.runCatching { api.sendUserCheckIn(user) }
    }
}