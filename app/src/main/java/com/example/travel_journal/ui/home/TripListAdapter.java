package com.example.travel_journal.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_journal.R;
import com.example.travel_journal.data.Trip;

import org.w3c.dom.Text;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    class TripViewHolder extends RecyclerView.ViewHolder {

        private final TextView tripItemName;
        private final TextView tripItemDestination;
        private final TextView tripItemPrice;
        private final RatingBar tripRatingBar;

        private TripViewHolder(View itemView) {
            super(itemView);
            tripItemName = itemView.findViewById(R.id.trip_item_name);
            tripItemDestination = itemView.findViewById(R.id.trip_item_destination);
            tripItemPrice = itemView.findViewById(R.id.trip_item_price);
            tripRatingBar = itemView.findViewById(R.id.trip_item_rating);
        }

        void bind(@NonNull final Trip trip) {
            tripItemName.setText(trip.getName());
            tripItemDestination.setText(trip.getDestination());
            tripItemPrice.setText(Float.toString(trip.getPrice()));
            tripRatingBar.setRating(trip.getRating());
        }
    }

    private final LayoutInflater inflater;
    private List<Trip> trips;

    TripListAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position){
        holder.bind(trips.get(position));
    }

    void setTrips(List<Trip> newTrips){
        trips = newTrips;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(trips != null)
            return trips.size();
        return 0;
    }
}
