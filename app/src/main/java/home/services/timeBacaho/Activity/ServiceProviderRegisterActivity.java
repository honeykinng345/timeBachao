package home.services.timeBacaho.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;
import home.services.timeBacaho.SessionManager;

public class ServiceProviderRegisterActivity extends AppCompatActivity  implements LocationListener {

    private ImageButton backbtn, gpsBtn;

    private EditText nameEt, phoneET, countryEt, stateEt, cityEt, adressEt, emailET, passwordET, cPassword;
    private TextView registersServiceTv;
    private ProgressDialog progressDialog;

    private String accountType = "Seller";
    private double latitude , longlitude ;
    private LocationManager locationManager;
    //permission Constants
    private static final int LOCATION_REQUEST_CODE = 100;
    //permission Array
    private String[] locationPermission;
    //session Manager
    private SessionManager session;
    //   private SQLiteHandler db;
    private SQLiteHandler db;
    private Button registerBtn;
 private    String token;
    private  String status = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ");
        progressDialog.setCancelable(false);


        backbtn = findViewById(R.id.RbackBtn);
        nameEt = findViewById(R.id.nameEt);
        phoneET = findViewById(R.id.phoneET);
        countryEt = findViewById(R.id.countryEt);
        stateEt = findViewById(R.id.stateEt);
        cityEt = findViewById(R.id.cityEt);

        adressEt = findViewById(R.id.adrdresET);
        passwordET = findViewById(R.id.Rpassword);
        cPassword = findViewById(R.id.Cpassword);
        emailET = findViewById(R.id.RemailET);
        gpsBtn = findViewById(R.id.gpsBtn);
        registerBtn = findViewById(R.id.regsiterBtn);


        //init permission Array
        locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        // Session manager
        session = new SessionManager(getApplicationContext());
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){ token = task.getResult().getToken();

                        } else {

                        }
                    }
                });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Check if user is already logged in or not

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
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

    }


    String fullName, Phone, country, state, city, address, email, password, confrimPassword;
    String regexStr = "^[0-9]$";

    private void inputData() {

        fullName = nameEt.getText().toString().trim();

        Phone = phoneET.getText().toString().trim();

        country = countryEt.getText().toString().trim();
        state = stateEt.getText().toString().trim();
        city = cityEt.getText().toString().trim();
        address = adressEt.getText().toString().trim();
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
        if (!password.equals(confrimPassword)) {
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


                        // Inserting row in users table
                       // db.addUser(name, email, uid,accountType,address,city,country,state,latitude,longitude,phone,created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered.!", Toast.LENGTH_LONG).show();

                      showDialoug();
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
                params.put("longitude", String.valueOf(longlitude));
                params.put("phone", phone);
                params.put("token", token);
                params.put("status", status);



                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialoug() {
        LottieAnimationView anim;
        TextView thanksTv,titleTv;
        View view = LayoutInflater.from(this).inflate(R.layout.row_thanks,null);
        ImageButton imageButton = view.findViewById(R.id.backBtn);
        thanksTv = view.findViewById(R.id.thanksTv);
        titleTv = view.findViewById(R.id.titleTv);
        anim = (LottieAnimationView)view.findViewById(R.id.checkAnimation);
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceProviderRegisterActivity.this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        anim.setVisibility(View.VISIBLE);
        thanksTv.setVisibility(View.VISIBLE);
        titleTv.setText("Thanks For Registration");

        dialog.show();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void detectLocation() {


        Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }
    private boolean checkLocationPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==(PackageManager.PERMISSION_GRANTED);
        return result;

    }

    public void requestPermissionLocation(){

        ActivityCompat.requestPermissions(this,locationPermission,LOCATION_REQUEST_CODE);

    }


    @Override
    public void onLocationChanged(Location location) {

        //location detected

        latitude = location.getLatitude();
        longlitude = location.getLongitude();
        findAdress();
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
        Toast.makeText(this,"Please turn on your Location...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                        Toast.makeText(this, "Location Neccassary...", Toast.LENGTH_SHORT).show();


                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
