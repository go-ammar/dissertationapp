package com.project.collabexpense.data.remote.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GroupDetails(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("transactions") var transactions: ArrayList<Transactions> = arrayListOf()

) : Serializable

data class UserShares(

    @SerializedName("shareAmount") var shareAmount: Int? = null,
    @SerializedName("isPaid") var isPaid: Boolean? = null,
    @SerializedName("userEmail") var userEmail: String? = null,
    @SerializedName("userName") var userName: String? = null

) : Serializable

data class Transactions(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("transactionDate") var transactionDate: Long? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("amountPaid") var amountPaid: Int? = null,
    @SerializedName("paidByUserEmail") var paidByUserEmail: String? = null,
    @SerializedName("groupId") var groupId: Int? = null,
    @SerializedName("userShares") var userShares: ArrayList<UserShares> = arrayListOf()

) : Serializable
