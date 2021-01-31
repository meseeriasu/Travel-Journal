package com.example.travel_journal.ui.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_journal.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private static final String TAG = "Add_trip_activity";

    private TextInputLayout tripName;
    private TextInputLayout destinationName;
    private EditText editTripName;
    private EditText editDestinationName;
    private RadioGroup tripTypeGroup;
    private TextView textCost;
    private SeekBar priceBar;
    private RatingBar ratingBar;
    private Button saveButton;
    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog picker;

    final Calendar myCalendar = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.travel_journal.R.layout.fragment_add_trip);
        initViews();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        priceBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int progress=priceBar.getProgress();
        textCost.setText("Price (EUR) : " + progress);
        saveButton.setOnClickListener(view->{
            if(validate()){
                finish();
            }
            else
                Log.d(TAG,":add:data is not valid");
        });
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            textCost.setText("Price (EUR) : " + progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private boolean validate() {
        boolean isValid = true;
        if(editTripName.getText().toString().isEmpty()){
            tripName.setError(getString(R.string.add_name_error));
            isValid = false;
        } else {
            clearError(tripName);
        }
        if(editDestinationName.getText().toString().isEmpty()){
            destinationName.setError(getString(R.string.add_destination_error));
            isValid = false;
        } else {
            clearError(destinationName);
        }
        return isValid;
    }

    private void clearError(TextInputLayout layout) {
        if (layout != null) {
            layout.setError(null);
        }
    }

    private void initViews() {
        tripName = findViewById(R.id.add_input_layout_trip_name);
        destinationName = findViewById(R.id.add_input_layout_trip_destination);
        editTripName = findViewById(R.id.add_input_text_trip_name);
        editDestinationName = findViewById(R.id.add_input_text_destination);
        tripTypeGroup = findViewById(R.id.radioGroup);
        textCost = findViewById(R.id.add_text_cost);
        priceBar = findViewById(R.id.add_price);
        ratingBar = findViewById(R.id.add_rating);
        saveButton = findViewById(R.id.add_save);
        startDate = findViewById(R.id.add_start_date);
        endDate = findViewById(R.id.add_end_date);
    }
}
