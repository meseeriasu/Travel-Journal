package com.example.travel_journal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_journal.MainActivity;
import com.example.travel_journal.R;
import com.example.travel_journal.data.DataSource;
import com.example.travel_journal.data.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rv = root.findViewById(R.id.home_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        List<Trip> data = DataSource.getItems(10);
        rv.setAdapter(new ItemAdapter(data));
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getActivity(), AddTripActivity.class);
               startActivity(intent);
            }
        });
        return root;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView destination;
        private final TextView price;
        private final RatingBar ratingBar;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trip_item_name);
            destination = itemView.findViewById(R.id.trip_item_destination);
            price = itemView.findViewById(R.id.trip_item_price);
            ratingBar = itemView.findViewById(R.id.trip_item_rating);
        }

        void bind(@NonNull final Trip trip) {
            name.setText(trip.getName());
            destination.setText(trip.getDestination());
            price.setText(Float.toString(trip.getPrice()));
            ratingBar.setRating(trip.getRating());
        }
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        @NonNull
        private final List<Trip> items;

        ItemAdapter(@NonNull List<Trip> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.trip_item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}