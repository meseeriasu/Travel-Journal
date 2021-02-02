package com.example.travel_journal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_journal.R;

public class TripDetailsActivity extends AppCompatActivity {

    private static final String TAG = "Add_trip_activity";
    private TextView tripName;
    private TextView tripDestination;
    private TextView tripType;
    private TextView tripPrice;
    private TextView tripStartDate;
    private TextView tripEndDate;
    private RatingBar tripRating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trip_details);
        initViews();
        initText();
    }

    private void initText() {
        Intent intent = getIntent();
        String aux = "Trip name: ";
        aux += intent.getStringExtra("name");
        tripName.setText(aux);
        aux = "Trip destination: ";
        aux += intent.getStringExtra("destination");
        tripDestination.setText(aux);
        aux = "Trip type: ";
        aux += intent.getStringExtra("type");
        tripType.setText(aux);
        aux = "Trip price: ";
        aux += intent.getIntExtra("price",0);
        tripPrice.setText(aux);
        aux = "Tripe start date: ";
        aux += intent.getStringExtra("startDate");
        tripStartDate.setText(aux);
        aux = "Trip end date: ";
        aux += intent.getStringExtra("endDate");
        tripEndDate.setText(aux);
        float rating = intent.getFloatExtra("rating",0);
        tripRating.setRating(rating);
    }

    private void initViews() {
        tripName = findViewById(R.id.trip_details_name);
        tripDestination = findViewById(R.id.trip_details_destination);
        tripType = findViewById(R.id.trip_details_type);
        tripPrice = findViewById(R.id.trip_details_price);
        tripStartDate = findViewById(R.id.trip_details_start_date);
        tripEndDate = findViewById(R.id.trip_details_end_date);
        tripRating = findViewById(R.id.trip_details_rating);
    }
}
