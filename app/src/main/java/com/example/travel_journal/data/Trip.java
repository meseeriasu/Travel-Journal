package com.example.travel_journal.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_table")
public class Trip {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "destination")
    private String destination;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "startDate")
    private String startDate;

    @ColumnInfo(name = "endDate")
    private String endDate;

    @ColumnInfo(name = "rating")
    private float rating;

    public Trip(String name, String destination, String type, int price, String startDate, String endDate, float rating) {
        this.name = name;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
    }

    public String getName(){
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
