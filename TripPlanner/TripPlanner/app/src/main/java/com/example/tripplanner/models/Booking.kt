/*
* Name: Vrushti Shah (8825494)
*/
package com.example.tripplanner.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val tripId: Int,
    val date: String,
    val travelerCount: Int
)