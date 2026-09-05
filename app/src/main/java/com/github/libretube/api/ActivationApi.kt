package com.github.libretube.api

import com.github.libretube.api.obj.ActivationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ActivationApi {

    @FormUrlEncoded
    @POST("panel/api/check_code.php")
    suspend fun checkCode(
        @Field("code") code: String,
        @Field("device_id") deviceId: String,
        @Field("device_name") deviceName: String
    ): ActivationResponse
}
