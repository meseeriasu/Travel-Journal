package com.example.travel_journal.ui.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_journal.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity {

    private static final String TAG = "Edit_trip_activity";

    private TextInputLayout destinationName;
    private TextView tripName;
    private EditText editDestinationName;
    private RadioGroup tripTypeGroup;
    private TextView textCost;
    private SeekBar priceBar;
    private RatingBar ratingBar;
    private Button saveButton;
    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog picker;
    private String nameTrip;

    final Calendar myCalendar = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_trip);
        initViews();
        initText();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(EditTripActivity.this,
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
                picker = new DatePickerDialog(EditTripActivity.this,
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent replyIntent = new Intent();
                    String destination = editDestinationName.getText().toString();
                    int selectedId = tripTypeGroup.getCheckedRadioButtonId();
                    RadioButton tripTypeRadio = findViewById(selectedId);
                    String tripType = tripTypeRadio.getText().toString();
                    String startDateString = startDate.getText().toString();
                    String endDateString = endDate.getText().toString();
                    float rating = ratingBar.getRating();
                    replyIntent.putExtra("name", nameTrip);
                    replyIntent.putExtra("destination", destination);
                    replyIntent.putExtra("tripType", tripType);
                    replyIntent.putExtra("price", priceBar.getProgress());
                    replyIntent.putExtra("startDate", startDateString);
                    replyIntent.putExtra("endDate", endDateString);
                    replyIntent.putExtra("rating", rating);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                } else
                    Log.d(TAG, ":add:data is not valid");
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
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

    private void initText(){
        Intent intent = getIntent();
        String tName = "Edit ";
        nameTrip = intent.getStringExtra("name");
        tName+= nameTrip;
        tripName.setText(tName);
        String tDestination = intent.getStringExtra("destination");
        editDestinationName.setText(tDestination);
        String tType = intent.getStringExtra("type");
        if(tType.equals("City Break")){
            tripTypeGroup.check(R.id.edit_radio_city_break);
        } else if(tType.equals("Sea Side")){
            tripTypeGroup.check(R.id.edit_radio_sea);
        } else {
            tripTypeGroup.check(R.id.edit_radio_mountains);
        }
        priceBar.setProgress(intent.getIntExtra("price", 0));
        String tStartDate = intent.getStringExtra("startDate");
        String tEndDate = intent.getStringExtra("endDate");
        if(tStartDate != null){
            startDate.setText(tStartDate);
        }
        if(tEndDate != null){
            endDate.setText(tEndDate);
        }
        float tRating = intent.getFloatExtra("rating",0);
        ratingBar.setRating(tRating);
    }

    private void initViews(){
        tripName = findViewById(R.id.edit_title);
        destinationName = findViewById(R.id.edit_input_layout_trip_destination);
        editDestinationName = findViewById(R.id.edit_input_text_destination);
        tripTypeGroup = findViewById(R.id.edit_radio_group);
        textCost = findViewById(R.id.edit_text_cost);
        priceBar = findViewById(R.id.edit_price);
        ratingBar = findViewById(R.id.edit_rating);
        saveButton = findViewById(R.id.edit_save);
        startDate = findViewById(R.id.edit_start_date);
        endDate = findViewById(R.id.edit_end_date);
        saveButton = findViewById(R.id.edit_save);
    }
}
