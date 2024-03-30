/*
* Name: Vrushti Shah (8825494)
*/
package com.example.tripplanner.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tripplanner.models.Booking

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking): Long

    @Query("SELECT * FROM bookings")
    fun getAllBookings(): LiveData<List<Booking>>
}
