package com.android.infyassignment

import androidx.room.Room
import com.android.infyassignment.data.db.AppDatabase
import com.android.infyassignment.data.network.APIInterface
import com.android.infyassignment.repository.ViewModelRepository
import com.android.infyassignment.utilities.BASE_URL
import com.android.infyassignment.utilities.DATA_BASE_NAME
import com.android.infyassignment.viewmodel.FactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATA_BASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().getFactDao() }
    single { createWebService() }
    single { ViewModelRepository(apiInterface = get(), factDao = get()) }
    viewModel {
        FactsViewModel(viewModelRepository = get())
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