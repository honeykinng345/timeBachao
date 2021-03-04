package home.services.timeBacaho.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.ProgressDialog;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.LocationFinder;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;
import home.services.timeBacaho.SessionManager;
import home.services.timeBacaho.SuggestionActivity;


public class RegisterActivity extends AppCompatActivity {
    private ImageButton backbtn, gpsBtn;
    private static final int LOCATION_REQUEST_CODE = 100;

    private EditText nameEt, phoneET, emailET, passwordET, cPassword;
    private TextView registersServiceTv;
    private ProgressDialog progressDialog;
    private String[] locationPermission;
    LocationManager locationManager;
    private String accountType = "User";
    RelativeLayout snackbar_action;

    LocationFinder finder;
    Location location;
    List<Address> addresses;
    Geocoder geocoder;

    double longitude = 0.0, latitude = 0.0;

    //session Manager
    private SessionManager session;
    //   private SQLiteHandler db;
    private SQLiteHandler db;
    private Button registerBtn;

    DatabaseHelper db1;

    String token;
    private String status = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ");
        progressDialog.setCancelable(false);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        geocoder = new Geocoder(this);
//init view .....
        backbtn = findViewById(R.id.RbackBtn);
        nameEt = findViewById(R.id.nameEt);
        phoneET = findViewById(R.id.phoneET);
        registersServiceTv = findViewById(R.id.sellerRegisterTv);
        passwordET = findViewById(R.id.Rpassword);
        cPassword = findViewById(R.id.Cpassword);
        emailET = findViewById(R.id.RemailET);
        gpsBtn = findViewById(R.id.gpsBtn);
        registerBtn = findViewById(R.id.regsiterBtn);
        snackbar_action = findViewById(R.id.snackbar_action);
        db1 = new DatabaseHelper(getApplicationContext());
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                         //   Toast.makeText(RegisterActivity.this, "" + token, Toast.LENGTH_SHORT).show();


                        } else {
                        }
                    }
                });
        // Session manager
        session = new SessionManager(getApplicationContext());
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        registersServiceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, ServiceProviderRegisterActivity.class));
            }
        });
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //get Current Location
                if (checkLocationPermission()) {
                    //already Allowed
                    detectLocation();


                } else {
                    //not allowed
                    requestPermissionLocation();


                }

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });


    }

    String fullName, Phone, country, state, city, address, email, password, confrimPassword;
    String regexStr = "^[0-9]$";

    private void inputData() {
        fullName = nameEt.getText().toString().trim();
        Phone = phoneET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        confrimPassword = cPassword.getText().toString().trim();
        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Name Feild Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Phone)) {
            Toast.makeText(this, "phone Feild Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email not proper Format", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email Field Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password Feild Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "password must be 6 Digitise", Toast.LENGTH_SHORT).show();
            return;

        }
         if (latitude == 0.0 || longitude == 0.0){

           Toast.makeText(this," Click Top Right Button to Detect Location ",Toast.LENGTH_SHORT).show();
          return;
         }
        if (!password.equals(confrimPassword)) {
            Toast.makeText(this, "password Not match", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateAccount(fullName, Phone, country, state, city, address, email, password);


    }

    private void CreateAccount(final String fullName, final String phone, final String country, final String state, final String city, final String address, final String email
            , final String password) {
        progressDialog.setMessage("Create Account..");
        progressDialog.show();
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        String accountType = user.getString("accountType");
                        String address = user.getString("address");
                        String city = user.getString("city");
                        String country = user.getString("country");
                        String state = user.getString("state");
                        String latitude = user.getString("latitude");
                        String longitude = user.getString("longitude");
                        String phone = user.getString("phone");
                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", fullName);
                params.put("email", email);
                params.put("password", password);
                params.put("accountType", accountType);
                params.put("address", address);
                params.put("city", city);
                params.put("country", country);
                params.put("state", state);
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                params.put("phone", phone);
                params.put("token", token);
                params.put("status", status);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean checkLocationPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    public void requestPermissionLocation() {
        ActivityCompat.requestPermissions(this, locationPermission, LOCATION_REQUEST_CODE);

    }

    private void detectLocation() {
        Snackbar snackbar = Snackbar
                .make(snackbar_action, "Please Wait...", Snackbar.LENGTH_LONG);
        snackbar.show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);



        if (location == null){
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }



        /*location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);*/
        findAddress();
    }

    private void findAddress() {
try {
    latitude = location.getLatitude();
    longitude = location.getLongitude();
    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
     address = addresses.get(0).getAddressLine(0);
     city = addresses.get(0).getLocality();
    state = addresses.get(0).getAdminArea();
    country = addresses.get(0).getCountryName();
    Toast.makeText(RegisterActivity.this,""+country, Toast.LENGTH_LONG).show();
    Address address = addresses.get(0);

} catch (Exception e){
    e.printStackTrace();
    Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
}


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted) {

                        //permssion granted
                        detectLocation();


                    } else {

                        //permssion deined


                        Snackbar snackbar = Snackbar
                                .make(snackbar_action, "www.journaldev.com", Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }
                }
            }
            break;

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    }





