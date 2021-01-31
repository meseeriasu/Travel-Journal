package com.example.travel_journal.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TripViewModel extends AndroidViewModel {

    private TripRepository tripRepository;
    private LiveData<List<Trip>> allTrips;

    public TripViewModel(Application application){
        super(application);
        tripRepository = new TripRepository(application);
        allTrips = tripRepository.getAllTrips();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return allTrips;
    }

    public void insert(Trip trip){
        tripRepository.insert(trip);
    }
}
