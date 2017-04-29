package com.mastermindappz.locationselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;
    String

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.placepickerbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });
    }

    private void startPlacePickerActivity() {
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

            getPlaceDetails(place);

            String name;
            String address;
            if (place.getName() != null) {
                name = place.getName().toString();
            } else {
                name = "No Name found!";
            }

            if (place.getAddress() != null) {
                address = place.getAddress().toString();
            } else {
                address = "No Address found!";
            }

            getPlaceDetails(name, address, place.getLatLng().latitude, place.getLatLng().longitude, place.getId());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    String getPlaceDetails(String name, String address, double lat, double lng, String id) {
        return ("Name : " + name + "\nAddress : " + address + "\nLat : " + lat + "\nLng : " + lng + "\nID : " + id);
    }

    void writePlaceDetails() {
        TextView addresstext = (TextView) findViewById(R.id.addresstext);
        addresstext.setText(getPlaceDetails());
    }
}
