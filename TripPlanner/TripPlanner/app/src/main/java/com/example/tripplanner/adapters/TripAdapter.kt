/*
* Name: Vrushti Shah (8825494)
*/

package com.example.tripplanner.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripplanner.R
import com.example.tripplanner.models.Trip

class TripAdapter(
    private val context: Context,
    private var trips: List<Trip>,
    private val onTripClicked: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.bind(context, trip)
    }

    override fun getItemCount(): Int = trips.size

    fun updateTrips(newTrips: List<Trip>) {
        trips = newTrips
        notifyDataSetChanged()
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tripNameTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.tripLocationTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.tripPriceTextView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.tripRatingTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.tripImageView)

        fun bind(context: Context, trip: Trip) {
            nameTextView.text = trip.name
            locationTextView.text = trip.location
            priceTextView.text = "$${trip.price}"
            ratingTextView.text = "Rating: ${trip.rating}"

            Glide.with(context).load(trip.image).into(imageView)

            itemView.setOnClickListener {
                onTripClicked(trip)
            }
        }
    }
}