package com.example.travel_journal.data;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Trip> getItems(int itemCount) {
        List<Trip> list = new ArrayList<>(itemCount);
        for (int i = 1; i <= itemCount; i++) {
            list.add(new Trip("name " + i, "destination" + i, "type" + i, i, "start", "end", 3));
        }
        return list;
    }
}
