package com.example.copy

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Okio
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.FileNotFoundException

object KimMessageSender {
  fun sendMessageToKim(
    resource: Resources,
    throwable: String
  ) {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch(Dispatchers.IO) {
      try {
        val source = try {
          Okio.source(resource.assets.open("ap.json"))
        } catch (e: FileNotFoundException) {
          null
        }
        val config: ApkConfig? = source?.use { innerSource ->
          Okio.buffer(innerSource).use {
            Gson().fromJson(it.readUtf8(), ApkConfig::class.java)
          }
        }
        val client = Retrofit.Builder().baseUrl("https://kim-robot.kwaitalk.com")
          .addConverterFactory(GsonConverterFactory.create()).build()
        val body = RequestBody.create(
          MediaType.parse("application/json; charset=utf-8"),
          MarkDownMessage(
            markDown = MarkDown(
              "branch: ${config?.branch_name} \n```\n $throwable \n```"
            )
          ).let {
            Gson().toJson(it)
          })
        client.create(RobotSend::class.java).sendMessage(body = body)
      } catch (e: Throwable) {
        e.printStackTrace()
      } finally {
        scope.cancel()
      }
    }
  }

}

interface RobotSend {
  @POST("api/robot/send")
  suspend fun sendMessage(
    @Query("key") robot_id: String = "b8e53736-3091-4762-b101-241b49e82578",
    @Body body: RequestBody
  ): Response<JSONObject>
}

data class MarkDownMessage(
  @SerializedName("msgType") val msgType: String = "markdown",
  @SerializedName("markdown") val markDown: MarkDown
)

data class MarkDown(@SerializedName("content") val content: String)