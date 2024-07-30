package com.project.collabexpense.data.remote.models

data class Budget(

    val id: Long = 0,

    var category: String,

    var months: String,

    var amount: Int,

    val userId: String,

    )
