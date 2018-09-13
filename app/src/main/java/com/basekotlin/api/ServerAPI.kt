package com.basekotlin.api

import br.com.wellingtoncosta.amdk.domain.model.UsersResponse
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import rx.Observable

/**
 * Created by HP on 07/09/2018.
 */
interface ServerAPI {

    @FormUrlEncoded
    @POST("v1/login")
    fun loginApp(@Field("email") email: String,
                 @Field("password") password: String,
                 @Field("device_token") device_token: String,
                 @Field("device_mac_address") device_mac_address: String,
                 @Field("user_type") user_type: String): Observable<UsersResponse>


    @FormUrlEncoded
    @POST("v1/logout")
    fun logoutApp(@Field("device_mac_address") device_mac_address: String): Observable<UsersResponse>
}