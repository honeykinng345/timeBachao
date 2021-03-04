package home.services.timeBacaho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class SuggestionActivity extends AppCompatActivity {
    Location location;
    double describeContents;
    List<Address> addresses;
    Geocoder geocoder;

double latitude ,longituted = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);



    }

    }
