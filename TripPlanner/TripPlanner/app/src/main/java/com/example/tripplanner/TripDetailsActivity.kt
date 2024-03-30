/*
* Name: Vrushti Shah (8825494)
*/
package com.example.tripplanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.tripplanner.models.Trip
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class TripDetailsActivity : AppCompatActivity() {

    private lateinit var tripNameTextView: TextView
    private lateinit var tripLocationTextView: TextView
    private lateinit var tripPriceTextView: TextView
    private lateinit var tripRatingTextView: TextView
    private lateinit var tripImageView: ImageView
    private lateinit var bookNowButton: Button

    private lateinit var saveButton: Button
    private lateinit var openInMapsButton: Button

    private lateinit var trip: Trip


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trip_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tripId = intent.getIntExtra("trip_id", 0)
        trip = Trip.defaultTrips.first { it.id == tripId }


        tripNameTextView = findViewById(R.id.tripNameTextView)
        tripLocationTextView = findViewById(R.id.tripLocationTextView)
        tripPriceTextView = findViewById(R.id.tripPriceTextView)
        tripRatingTextView = findViewById(R.id.tripRatingTextView)
        tripImageView = findViewById(R.id.tripImageView)
        bookNowButton = findViewById(R.id.bookNowButton)
        saveButton = findViewById(R.id.saveButton)
        openInMapsButton = findViewById(R.id.openInMapsButton)

        tripNameTextView.text = trip.name
        tripLocationTextView.text = trip.location
        tripPriceTextView.text = "$${trip.price}"
        tripRatingTextView.text = trip.rating.toString()

        Glide.with(this)
            .load(trip.image)
            .into(tripImageView)

        bookNowButton.setOnClickListener {
            val intent = Intent(this, BookTripActivity::class.java)
            intent.putExtra("trip_id", trip.id)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            // Get file name to save

            //open file chooser
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE, "TripDetails.txt") // Suggest a filename.
            }
            startActivityForResult(intent, 1)

        }

        openInMapsButton.setOnClickListener {
            // open the location in maps web page
            val location = trip.location
            val url = "https://www.google.com/maps/search/?api=1&query=$location"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            // save the trip details to the file

            if (resultCode == RESULT_OK) {
                val uri = data?.data

                if (uri == null) {
                    Toast.makeText(this@TripDetailsActivity, "Error saving event to file", Toast.LENGTH_SHORT).show()
                    return
                }

                try {
                    contentResolver.openFileDescriptor(uri, "w")?.use { pfd ->
                        FileOutputStream(pfd.fileDescriptor).use { fos ->
                            OutputStreamWriter(fos).use { writer ->
                                val fileContents = "Trip Name: ${trip.name}\nTrip Location: ${trip.location}\nTrip Price: ${trip.price}\nTrip Rating: ${trip.rating}"
                                writer.write(fileContents)
                            }
                        }
                    }
                } catch (e: IOException) {
                    // Handle the error
                    Toast.makeText(this@TripDetailsActivity, "Error saving event to file", Toast.LENGTH_SHORT).show()
                    Log.e("Utils", "Error saving event to file", e)
                }


            }

        }
    }
}