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


        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        geocoder = new Geocoder(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {

                        latitude = location.getLatitude();
                        longituted = location.getLongitude();
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                        Address address = addresses.get(0);
                        String address1 = addresses.get(0).getAddressLine(0);
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();

                        Toast.makeText(SuggestionActivity.this,""+address1,Toast.LENGTH_LONG).show();
                     //   Toast.makeText(SuggestionActivity.this,""+latitude,Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {

            latitude = location.getLatitude();
            longituted = location.getLongitude();
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
            String address1 = addresses.get(0).getAddressLine(0);
            Address address = addresses.get(0);
            Toast.makeText(SuggestionActivity.this,""+address1,Toast.LENGTH_LONG).show();

        //    Toast.makeText(SuggestionActivity.this,""+latitude,Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
