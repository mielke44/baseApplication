# BaseApplication

Kotlin based Challeng app focused on a RESTApi and a event checkin experience.

Supports SDK 19 to 30 (KitKat - Android 12) (developed and tested on Android 10 Samsung M51)

* No persistent data, as it was not required for the challenge.
* Used MVVM for the extended lifecyle of ViewModels and a low complexity of processing data.
* Used AndroidX for support in some layouts and resources that were used
* Glide to load images for it's ease of use
* Koin for Dependency Injection because it works without much effort with MVVM
* Retrofit and OkHttp3 to make api requests (2 GET and 1 POST)
* ViewBinding as it's the current best way to reference views and compontents
* AppCompat for multiple SDK support 
* Material Design for some guidelines and most of resources
* Some animations and components of my own design for code replicability
* Coroutines for asynchronous calls and better use of ViewModelScope
* RecyclerView to keep memoryLeaks to a minimum
* Some code guidelines from previous projects
* Resources outside of Material Design were taken copyright free from the internet
* Using google maps api in order to show event locations
* App is not published in the store
* Night theme based
