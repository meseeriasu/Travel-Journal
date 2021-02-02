package com.example.travel_journal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_journal.MainActivity;
import com.example.travel_journal.R;
import com.example.travel_journal.data.Trip;
import com.example.travel_journal.data.TripViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    public static final int NEW_TRIP_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TRIP_ACTIVITY_REQUEST_CODE = 2;
    private static final String TAG = "Home_trip_activity";

    private TripViewModel tripViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rv = root.findViewById(R.id.home_rv);
        final TripListAdapter adapter = new TripListAdapter(getContext());
        rv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        tripViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                adapter.setTrips(trips);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                startActivityForResult(intent, NEW_TRIP_ACTIVITY_REQUEST_CODE);
            }
        });
        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), TripDetailsActivity.class);
                List<Trip> trips = tripViewModel.getAllTrips().getValue();
                Trip trip = trips.get(position);
                intent.putExtra("name", trip.getName());
                intent.putExtra("destination", trip.getDestination());
                intent.putExtra("type", trip.getType());
                intent.putExtra("price", trip.getPrice());
                intent.putExtra("startDate", trip.getStartDate());
                intent.putExtra("endDate", trip.getEndDate());
                intent.putExtra("rating", trip.getRating());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Intent intent = new Intent(getContext(), EditTripActivity.class);
                List<Trip> trips = tripViewModel.getAllTrips().getValue();
                Trip trip = trips.get(position);
                intent.putExtra("name", trip.getName());
                intent.putExtra("destination", trip.getDestination());
                intent.putExtra("type", trip.getType());
                intent.putExtra("price", trip.getPrice());
                intent.putExtra("startDate", trip.getStartDate());
                intent.putExtra("endDate", trip.getEndDate());
                intent.putExtra("rating", trip.getRating());
                startActivityForResult(intent, EDIT_TRIP_ACTIVITY_REQUEST_CODE);
            }
        }));
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TRIP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i(TAG, ":new trip added");
            Trip trip = new Trip(data.getStringExtra("name"),
                    data.getStringExtra("destination"),
                    data.getStringExtra("tripType"),
                    data.getIntExtra("price", 0),
                    data.getStringExtra("startDate"),
                    data.getStringExtra("endDate"),
                    data.getFloatExtra("rating", 0));
            if(checkDuplicate(trip)) {
                tripViewModel.insert(trip);
                Toast.makeText(getActivity(),"Trip added!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getActivity(),"There is already a trip with this name!", Toast.LENGTH_LONG).show();
        }

        if( requestCode == EDIT_TRIP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i(TAG, ":edit trip");
            Trip trip = new Trip(data.getStringExtra("name"),
                    data.getStringExtra("destination"),
                    data.getStringExtra("tripType"),
                    data.getIntExtra("price",0),
                    data.getStringExtra("startDate"),
                    data.getStringExtra("endDate"),
                    data.getFloatExtra("rating",0));
            tripViewModel.update(trip);
            Toast.makeText(getActivity(),"Trip edited!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkDuplicate(Trip trip) {
        List<Trip> tripsDuplicate = tripViewModel.getAllTrips().getValue();
        for(int i=0;i<tripsDuplicate.size();i++){
            if(tripsDuplicate.get(i).getName().equals(trip.getName()))
                return false;
        }
        return true;
    }
}