package com.katilijiwo.ministockbitapp.data.remote.json.websocketrequest


import com.google.gson.annotations.SerializedName

data class WebSocketRequest(
    @SerializedName("action")
    val action: String,
    @SerializedName("subs")
    val subs: List<String>
)