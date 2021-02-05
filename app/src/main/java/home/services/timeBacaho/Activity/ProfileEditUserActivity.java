package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SessionManager;

public class ProfileEditUserActivity extends AppCompatActivity implements LocationListener {
    private Button updateButton;
    private ImageButton backBtn,gpsBtn;
    private EditText nameEt,phoneET,countryEt,stateEt,cityEt,adressEt;
    private ProgressDialog progressDialog;
    private double latitude = 0.0, longlitude = 0.0;
    //permission Constants
    private static  final  int LOCATION_REQUEST_CODE = 100;
    private String[]  locationPermission;

private   String  email;
    private DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_user);

        backBtn = findViewById(R.id.backBtn);
        nameEt = findViewById(R.id.nameEt);
        phoneET = findViewById(R.id.phoneET);
        countryEt = findViewById(R.id.countryEt);
        stateEt = findViewById(R.id.stateEt);
        cityEt = findViewById(R.id.cityEt);
        adressEt = findViewById(R.id.adrdresET);
        gpsBtn = findViewById(R.id.gpsBtn);
        updateButton = findViewById(R.id.updateBtn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ");
        sessionManager = new SessionManager(getApplicationContext());
        databaseHelper = new DatabaseHelper(getApplicationContext());

        //init permission Array
        locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        checkUser();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputData();

            }
        });
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //detect Location
                //detect Curren Location

                if (checkLocationPermission()){
                    //already Allowed
                    detectLocation();



                }else {

                    //not allowed
                    requestPermissionLocation();


                }
            }
        });

    }
    private String fullName,Phone,country,state,city,address;


    private void inputData() {


        fullName = nameEt.getText().toString().trim();

        Phone = phoneET.getText().toString().trim();

        country = countryEt.getText().toString().trim();
        state = stateEt.getText().toString().trim();
        city = cityEt.getText().toString().trim();
        address = adressEt.getText().toString().trim();

/*
         if (latitude == 0.0 || longlitude == 0.0){

          Toast.makeText(this," Click Top Right Button to Detect Location ",Toast.LENGTH_SHORT).show();
           return;

         }

 */
        updateProfile();

    }

    private void updateProfile() {

        progressDialog.setMessage(" Update Information");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_UpdateUser_Information, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



              addDataToSqlite();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ProfileEditUserActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String , String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                params.put("email",email);
                params.put("name",fullName);
                params.put("address",address);
                params.put("city ", city);
                params.put("state",state);
                params.put("latitude",String.valueOf(latitude));
                params.put("longitude",String.valueOf(longlitude));
                params.put("phone",Phone);


                return params;

            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void addDataToSqlite() {

       String templati = String.valueOf(latitude);
       String templongi = String.valueOf(longlitude);

        databaseHelper.updateData(email,fullName,address,city,country,state,templati,templongi,Phone);


        progressDialog.dismiss();
        Toast.makeText(ProfileEditUserActivity.this,"Information Is Updated Successful",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileEditUserActivity.this,MainUserActivity.class));
        finish();
    }

    private void checkUser() {

        if (!sessionManager.isLoggedIn()){
            Intent intent = new Intent(ProfileEditUserActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }else
            {

            loadUserInfo();
        }

    }
    private boolean checkLocationPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==(PackageManager.PERMISSION_GRANTED);
        return result;

    }

    public void requestPermissionLocation(){

        ActivityCompat.requestPermissions(this,locationPermission,LOCATION_REQUEST_CODE);

    }

    @SuppressLint("SetTextI18n")
    private void loadUserInfo() {

        Cursor res = databaseHelper.getAllData();
        while (res.moveToNext()) {
       String     name = res.getString(1);
       email = res.getString(2);
        String    ADDRESS = res.getString(5);
        String    CITY = res.getString(6);
        String   COUNTRY = res.getString(7);
        String    STATE = res.getString(8);
         String   phone = res.getString(11);


            nameEt.setText("" + name);
            phoneET.setText(""+phone);
            countryEt.setText(""+COUNTRY);
            adressEt.setText(""+ADDRESS);
           stateEt.setText(""+STATE);
            cityEt.setText(""+CITY);

        }




    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longlitude = location.getLongitude();
        findAdress();
    }
    private void detectLocation() {

        Toast.makeText(this,"Please wait...",Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);


    }
    private void findAdress() {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(latitude,longlitude,1);
            String address = addresses.get(0).getAddressLine(0);//get Complete Address
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            adressEt.setText(address);
            cityEt.setText(city);
            stateEt.setText(state);
            countryEt.setText(country);

        }catch (Exception e){
            Toast.makeText(this,""+e,Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this,"Please turn on your GPS...",Toast.LENGTH_SHORT).show();
    }
}