package com.example.travel_journal.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TripRepository {
    private TripDao tripDao;
    private LiveData<List<Trip>> allTrips;

    TripRepository(Application application){
        TripRoomDatabase database = TripRoomDatabase.getDatabase(application);
        tripDao = database.tripDao();
        allTrips = tripDao.getAllTrips();
    }

    LiveData<List<Trip>> getAllTrips(){
        return allTrips;
    }

    void insert(Trip trip){
        TripRoomDatabase.databaseWriteExecutor.execute(()->{
            tripDao.insertTrip(trip);
        });
    }
}
