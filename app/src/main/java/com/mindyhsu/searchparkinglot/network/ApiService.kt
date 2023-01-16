package com.mindyhsu.searchparkinglot.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mindyhsu.searchparkinglot.BuildConfig
import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.data.LoginResponse
import com.mindyhsu.searchparkinglot.data.UpdateResponse
import com.mindyhsu.searchparkinglot.data.UserUpdateRequestBody
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://noodoe-app-development.web.app/api/"
private const val HEADER = "Content-Type"
private const val HEADER_TYPE = "application/json"
private const val HEADER_NAME_APPLICATION_ID = "X-Parse-Application-Id"
private const val HEADER_NAME_SESSION_TOKEN = "X-Parse-Session-Token"
private const val PATH_OBJECT_ID = "objectId"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

// Log request data
private val logging: HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(
        if (BuildConfig.TIMBER_VISIABLE) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    )
private val client = OkHttpClient.Builder().addInterceptor(
    Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader(HEADER, HEADER_TYPE)
            .build()

        chain.proceed(newRequest)
    }
).addInterceptor(logging).build()

// let Retrofit use Moshi to convert Json into Kotlin Objects
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL).client(client).build()

interface SearchParkingLotApiService {
    @POST("login")
    @Headers("$HEADER: $HEADER_TYPE")
    suspend fun login(
        @Header(HEADER_NAME_APPLICATION_ID) applicationId: String,
        @Body requestBody: LoginRequestBody
    ): LoginResponse

    @PUT("users/{objectId}")
    suspend fun updateUser(
        @Header(HEADER_NAME_APPLICATION_ID) applicationId: String,
        @Header(HEADER_NAME_SESSION_TOKEN) sessionToken: String,
        @Path(PATH_OBJECT_ID) objectId: String,
        @Body requestBody: UserUpdateRequestBody
    ): UpdateResponse
}

object SearchParkingLotApi {
    val retrofitService: SearchParkingLotApiService by lazy {
        retrofit.create(SearchParkingLotApiService::class.java)
    }
}
