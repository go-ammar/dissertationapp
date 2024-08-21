package com.project.collabexpense.data.remote.models

import com.google.gson.annotations.SerializedName

data class GroupTransactionAdded(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("transactionDate") var transactionDate: Long? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("amountPaid") var amountPaid: Int? = null,
    @SerializedName("paidByUser") var paidByUser: PaidByUser? = PaidByUser()
)


data class PaidByUser(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("dob") var dob: Int? = null

)