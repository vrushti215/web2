/*
* Name: Vrushti Shah (8825494)
*/
package com.example.tripplanner

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tripplanner.database.AppDatabase
import com.example.tripplanner.models.Booking
import com.example.tripplanner.models.Trip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BookTripActivity : AppCompatActivity() {

    private lateinit var tripNameTextView: TextView
    private lateinit var travelerCountSpinner: Spinner
    private lateinit var dateSelectButton: Button
    private lateinit var bookNowButton: Button
    private lateinit var travelerNamesContainer: LinearLayout

    private var date: String = ""
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())

    private val databaseScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_trip)

        tripNameTextView = findViewById(R.id.tripNameTextView)
        travelerCountSpinner = findViewById(R.id.travelerCountSpinner)
        dateSelectButton = findViewById(R.id.dateSelectButton)
        bookNowButton = findViewById(R.id.bookNowButton)
        travelerNamesContainer = findViewById(R.id.travelerNamesContainer)

        val tripId = intent.getIntExtra("trip_id", 0)
        val trip = Trip.defaultTrips.firstOrNull { it.id == tripId } ?: return

        tripNameTextView.text = trip.name

        setupDateSelectButton()
        setupBookNowButton(trip)
    }

    private fun setupDateSelectButton() {
        dateSelectButton.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                date = dateFormat.format(calendar.time)
                dateSelectButton.text = date
                if (travelerCountSpinner.selectedItemPosition > 0) {
                    addTravelerNameInputs(travelerCountSpinner.selectedItem.toString().toInt())
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupBookNowButton(trip: Trip) {
        bookNowButton.setOnClickListener {
            if (date.isEmpty()) {
                Toast.makeText(this, "Please select a date for your trip.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val travelerCount = travelerCountSpinner.selectedItem.toString().toInt()
            var allFieldsFilled = true
            val travelerNames = StringBuilder()

            for (i in 0 until travelerNamesContainer.childCount) {
                val child = travelerNamesContainer.getChildAt(i)
                if (child is EditText) {
                    val name = child.text.toString()
                    if (name.isEmpty()) {
                        allFieldsFilled = false
                        break
                    } else {
                        travelerNames.append(name).append(";")
                    }
                }
            }

            if (!allFieldsFilled) {
                Toast.makeText(this, "Please fill in all traveler names.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val bookingDao = AppDatabase.getDatabase(this).bookingDao()
            val booking = Booking(0, trip.id, date, travelerCount)

            databaseScope.launch {
                bookingDao.insertBooking(booking)
                runOnUiThread {
                    Toast.makeText(this@BookTripActivity, "Booking confirmed for $date.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun addTravelerNameInputs(numberOfTravelers: Int) {
        travelerNamesContainer.removeAllViews()
        for (i in 0 until numberOfTravelers) {
            val fullNameEditText = EditText(this).apply {
                hint = "Traveler ${i + 1} Full Name"
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            }
            travelerNamesContainer.addView(fullNameEditText)
        }
    }
}
