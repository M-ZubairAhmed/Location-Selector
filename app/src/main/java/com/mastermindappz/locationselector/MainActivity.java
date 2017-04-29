package com.mastermindappz.locationselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.placepickerbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerIntent();
            }
        });
    }

    private void startPlacePickerIntent() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Log.e("MainActivity", e + "");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);

            TextView addressTextBox = (TextView) findViewById(R.id.addresstext);

            String placeDetails = getPlaceDetails(place);

            writePlaceDetails(placeDetails, addressTextBox);
        }
    }

    private String getPlaceDetails(Place place) {
        String name;
        String address;
        if (place.getName() != null) {
            name = place.getName().toString();
        } else {
            name = "No Name found!";
        }

        address = "No Address found!";

        if (place.getAddress() != null) {
            if (!place.getAddress().toString().trim().isEmpty()) {
                address = place.getAddress().toString();
            }
        }

        return ("Name : " + name +
                "\nAddress : " + address +
                "\nLat : " + place.getLatLng().latitude +
                "\nLng : " + place.getLatLng().longitude +
                "\nID : " + place.getId());
    }

    void writePlaceDetails(String placeDetails, TextView addressTextBox) {
        if (placeDetails != null && addressTextBox != null) {
            addressTextBox.setText(placeDetails);
        } else {
            Toast.makeText(this, "There seems to be some problem!", Toast.LENGTH_SHORT).show();
        }
    }
}
