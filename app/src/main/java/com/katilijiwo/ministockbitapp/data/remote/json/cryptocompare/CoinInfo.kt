package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName

data class CoinInfo(
    @SerializedName("Algorithm")
    val algorithm: String,
    @SerializedName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerializedName("BlockNumber")
    val blockNumber: Double,
    @SerializedName("BlockReward")
    val blockReward: Double,
    @SerializedName("BlockTime")
    val blockTime: Double,
    @SerializedName("DocumentType")
    val documentType: String,
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Id")
    val id: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Internal")
    val `internal`: String,
    @SerializedName("MaxSupply")
    val maxSupply: Double,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NetHashesPerSecond")
    val netHashesPerSecond: Double,
    @SerializedName("ProofType")
    val proofType: String,
    @SerializedName("Rating")
    val rating: Rating,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("Url")
    val url: String
)