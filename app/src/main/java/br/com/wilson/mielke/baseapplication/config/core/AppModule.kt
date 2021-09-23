package br.com.wilson.mielke.baseapplication.config.core

import br.com.wilson.mielke.baseapplication.config.api.RESTApi
import br.com.wilson.mielke.baseapplication.viewModel.SecondArchitectureViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { OkHttp3(get() as MicroServiceInterceptor) }
    single { Retrofit(get()).create(RESTApi::class.java) }
    factory { MicroServiceInterceptor() }
    factory { NetworkInteractor(get()) }

    viewModel { SecondArchitectureViewModel(get())}
}