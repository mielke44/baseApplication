package br.com.wilson.mielke.baseapplication.viewModel

import androidx.lifecycle.*
import br.com.wilson.mielke.baseapplication.config.core.NetworkInteractor
import br.com.wilson.mielke.baseapplication.config.models.EventModel
import br.com.wilson.mielke.baseapplication.config.models.Response
import br.com.wilson.mielke.baseapplication.config.models.User
import kotlinx.coroutines.launch

class SecondArchitectureViewModel(private val network: NetworkInteractor) : ViewModel() {

    var eventList: LiveData<List<EventModel>> = MutableLiveData()
    var singleEvent: LiveData<EventModel> = MutableLiveData()
    var postState: LiveData<Response> = MutableLiveData()
    private var checkedEvents = mutableListOf<Int>()
    private var user: User = User()

    init {
        getEvents()
        getSingleEvent(1)
    }

    private fun getEvents(){
        viewModelScope.launch{
            eventList = liveData { network.getEvents().fold(
                onSuccess = {
                    emit(it)
                },
                onFailure = {
                    emit(listOf(generateSingleErrorEvent()))
                }
            )}
        }
    }

    fun getSingleEvent(id: Int) {
        viewModelScope.launch {
            singleEvent = liveData{ network.getSingleEvents(id).fold(
                onSuccess = {
                    emit(it)
                },
                onFailure = {
                    emit(generateSingleErrorEvent())
                }
            )}
        }
    }


    fun sendCheckingUser(id: String){
        checkedEvents.add(id.toInt())
        user.let {
            it.eventId = id
            viewModelScope.launch {
                postState = liveData {
                    network.sendUserCheckIn(it).fold(
                        onSuccess = { responseCode ->
                            emit(responseCode)
                        },
                        onFailure = {
                            emit(Response(""))
                        }
                    )
                }
            }
        }
    }

    fun isEventChecked(id: Int) : Boolean = checkedEvents.contains(id)

    fun isUserUpdated() : Boolean = !user.email.isNullOrEmpty() && !user.name.isNullOrEmpty()

    fun saveUserData(name: String, mail: String){
        //Security is not necessary for data is volatile here, once the app is dead, this vanishes; Also the user is private to VM.
        user = User(email = mail,name = name)
    }

    private fun generateSingleErrorEvent(): EventModel{
        return EventModel(
            1534784400000,
            "Ocorreu um erro!",
            "0",
            "https://p4.wallpaperbetter.com/wallpaper/159/71/731/errors-minimalism-typography-red-wallpaper-preview.jpg",
            0.0,
            0.0,
            emptyList(),
            00.00,
            "Ocorreu um erro!",
        )
    }
}