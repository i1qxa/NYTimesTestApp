package com.example.nytimesapp.data.local.reviews

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ReviewsDao {

    @Query("SELECT * FROM ReviewItemDB")
    fun getAllReviews():PagingSource<Int,ReviewItemDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListReviews(listReviews:List<ReviewItemDB>)

    @Query("DELETE FROM ReviewItemDB")
    suspend fun clearReviewsDB()

    @Transaction
    suspend fun updateReviewsData(newListReviews:List<ReviewItemDB>){
        clearReviewsDB()
        insertListReviews(newListReviews)
    }

}