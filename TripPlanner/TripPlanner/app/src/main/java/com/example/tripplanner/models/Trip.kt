/*
* Name: Vrushti Shah (8825494)
*/
package com.example.tripplanner.models

data class Trip(
    val id: Int,
    val name: String,
    val location: String,
    val price: Double,
    val rating: Double,
    val image: String
) {
    // Default list of trips
    companion object {
        val defaultTrips = listOf(
            Trip(
                1,
                "Trip to Bali",
                "Bali, Indonesia",
                599.99,
                4.7,
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/b9/ee/23/caption.jpg"
            ),
            Trip(
                2,
                "Countryside Tour",
                "Lancaster, PA",
                149.99,
                4.5,
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/12/9a/00/bd/dutch-wonderland-castle.jpg"
            ),
            Trip(
                3,
                "Beach Camping",
                "Assateague Island, VA",
                99.99,
                4.3,
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2b/94/a4/ce/caption.jpg"
            ),
            Trip(
                4,
                "Skiing in the Alps",
                "Swiss Alps",
                1299.99,
                4.9,
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/4d/44/3f/austrian-alps.jpg"
            ),
            Trip(
                5,
                "Weekend in the Bahamas",
                "The Bahamas",
                499.99,
                4.6,
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2b/4b/c8/19/caption.jpg"
            ),
            Trip(
                6,
                "Costa Rica Getaway",
                "Costa Rica",
                899.99,
                4.8,
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/33/ea/a5/caption.jpg"
            )
        )
    }
}

