package com.katilijiwo.ministockbitapp.data.remote.websocket

import android.util.Log
import com.google.gson.Gson
import com.katilijiwo.ministockbitapp.data.remote.json.websocketrequest.WebSocketRequest
import com.katilijiwo.ministockbitapp.data.remote.json.websocketresponse.WebSocketResponse
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class CryptoCompareWebSocket(
    private val array: ArrayList<String>
): WebSocketListener() {

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

    private val ACTION = "SubAdd"
    private val TAG = "WebSocket"

    override fun onOpen(webSocket: WebSocket, response: Response) {
        val obj = WebSocketRequest(
            action = ACTION,
            subs = array
        )
        val gson = Gson()
        val json = gson.toJson(obj)
        webSocket.send(json)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "Reciving : $text")

        val gson = Gson()
        val results: WebSocketResponse = gson.fromJson(text, WebSocketResponse::class.java)

        Log.d(TAG, results.toString())
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d(TAG, "Reciving : ${bytes.hex()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
        Log.d(TAG, "Closing : $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d(TAG, "Error: " + t.message)
    }
}