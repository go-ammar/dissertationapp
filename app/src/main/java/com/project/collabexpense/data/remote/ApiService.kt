package com.project.collabexpense.data.remote

import com.project.collabexpense.data.remote.dto.MyDataDto
import com.project.collabexpense.data.remote.dto.TestLocal
import com.project.collabexpense.data.remote.models.AuthResponse
import com.project.collabexpense.data.remote.models.Budget
import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.data.remote.models.GroupDetails
import com.project.collabexpense.data.remote.models.GroupTransactionAdded
import com.project.collabexpense.data.remote.models.MonthlyCategorySpend
import com.project.collabexpense.data.remote.models.Transaction
import com.project.collabexpense.data.remote.models.UserInfo
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("endpoint")
    suspend fun getData(): List<MyDataDto>

    @GET("all-todo")
    suspend fun testLocal(): List<TestLocal>

    @GET("budget")
    suspend fun getBudgets(): List<Budget>

    @POST("budget/create")
    suspend fun addBudget(@Body requestBody: RequestBody): Budget

    @POST("auth")
    suspend fun login(@Body requestBody: RequestBody): AuthResponse

    @POST("user")
    suspend fun signUp(@Body requestBody: RequestBody): UserInfo

    @GET("user/details")
    suspend fun getUserDetails(): UserInfo

    @POST("user/update")
    suspend fun updateUser(@Body body: RequestBody): UserInfo

    @GET("budget/categories")
    suspend fun getUserBudgetCategories(): List<String>

    @GET("transaction")
    suspend fun getUserTransactionHistory(): List<Transaction>

    @GET("budget/monthly-spend")
    suspend fun getBudgetsWithAllData(): List<MonthlyCategorySpend>

    @POST("transaction")
    suspend fun addTransaction(
        @Body body: RequestBody
    ): Transaction

    @GET("groups/user")
    suspend fun getUserGroups(
    ): List<GroupData>

    @POST("groups/create")
    suspend fun createGroup(
        @Body requestBody: RequestBody
    ): GroupData


    @GET("groups/{id}")
    suspend fun getGroupDetails(
        @Path("id") groupId: Long
    ): GroupDetails

    @POST("groups/transactions")
    suspend fun addGroupTransaction(
        @Body requestBody: RequestBody
    ): GroupTransactionAdded

}