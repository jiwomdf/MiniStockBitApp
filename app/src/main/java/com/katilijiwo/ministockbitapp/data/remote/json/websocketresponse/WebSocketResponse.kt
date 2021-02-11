package com.katilijiwo.ministockbitapp.data.remote.json.websocketresponse


import com.google.gson.annotations.SerializedName

data class WebSocketResponse(
    @SerializedName("FLAGS")
    val fLAGS: Double,
    @SerializedName("FROMSYMBOL")
    val fROMSYMBOL: String,
    @SerializedName("HIGH24HOUR")
    val hIGH24HOUR: Double,
    @SerializedName("HIGHDAY")
    val hIGHDAY: Double,
    @SerializedName("HIGHHOUR")
    val hIGHHOUR: Double,
    @SerializedName("LASTMARKET")
    val lASTMARKET: String,
    @SerializedName("LASTTRADEID")
    val lASTTRADEID: String,
    @SerializedName("LASTUPDATE")
    val lASTUPDATE: Double,
    @SerializedName("LASTVOLUME")
    val lASTVOLUME: Double,
    @SerializedName("LASTVOLUMETO")
    val lASTVOLUMETO: Double,
    @SerializedName("LOW24HOUR")
    val lOW24HOUR: Double,
    @SerializedName("LOWDAY")
    val lOWDAY: Double,
    @SerializedName("LOWHOUR")
    val lOWHOUR: Double,
    @SerializedName("MARKET")
    val mARKET: String,
    @SerializedName("MEDIAN")
    val mEDIAN: Double,
    @SerializedName("OPEN24HOUR")
    val oPEN24HOUR: Double,
    @SerializedName("OPENDAY")
    val oPENDAY: Double,
    @SerializedName("OPENHOUR")
    val oPENHOUR: Double,
    @SerializedName("PRICE")
    val pRICE: Double,
    @SerializedName("TOPTIERVOLUME24HOUR")
    val tOPTIERVOLUME24HOUR: Double,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    val tOPTIERVOLUME24HOURTO: Double,
    @SerializedName("TOSYMBOL")
    val tOSYMBOL: String,
    @SerializedName("TYPE")
    val tYPE: String,
    @SerializedName("VOLUME24HOUR")
    val vOLUME24HOUR: Double,
    @SerializedName("VOLUME24HOURTO")
    val vOLUME24HOURTO: Double,
    @SerializedName("VOLUMEDAY")
    val vOLUMEDAY: Double,
    @SerializedName("VOLUMEDAYTO")
    val vOLUMEDAYTO: Double,
    @SerializedName("VOLUMEHOUR")
    val vOLUMEHOUR: Double,
    @SerializedName("VOLUMEHOURTO")
    val vOLUMEHOURTO: Double
)