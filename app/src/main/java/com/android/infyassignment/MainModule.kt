package com.android.infyassignment

import androidx.room.Room
import com.android.infyassignment.data.db.AppDatabase
import com.android.infyassignment.data.network.APIInterface
import com.android.infyassignment.repository.ViewModelRepository
import com.android.infyassignment.utilities.BASE_URL
import com.android.infyassignment.utilities.DATA_BASE_NAME
import com.android.infyassignment.viewModel.FactsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, DATA_BASE_NAME).build() }
    single { get<AppDatabase>().getFactDao() }
    single { ViewModelRepository(get(),get()) }
    single { createWebService() }
    viewModel {
        FactsViewModel(get())
    }
}

fun createWebService(): APIInterface {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    return retrofit.create(APIInterface::class.java)
}