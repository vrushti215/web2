/*
* Name: Vrushti Shah (8825494)
*/
package com.example.tripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.adapters.TripAdapter
import com.example.tripplanner.models.Trip

class MainActivity : AppCompatActivity() {


    private lateinit var tripsRecyclerView: RecyclerView
    private lateinit var tripAdapter: TripAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tripsRecyclerView = findViewById(R.id.recyclerView)
        tripAdapter = TripAdapter(this, Trip.defaultTrips){
            val intent =  Intent(this, TripDetailsActivity::class.java)
            intent.putExtra("trip_id", it.id)
            startActivity(intent)
        }

        tripsRecyclerView.adapter = tripAdapter
        tripsRecyclerView.setHasFixedSize(true)
        tripsRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}