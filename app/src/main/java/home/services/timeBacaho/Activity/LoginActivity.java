package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.R;

import home.services.timeBacaho.SessionManager;


public class LoginActivity extends AppCompatActivity {

    EditText  emailIt,passwordET;
    private SessionManager session;

    TextView noAccountTv ;
    Button loginBtn;

    String accountType;

    DatabaseHelper db1;



    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ");
        progressDialog.setCanceledOnTouchOutside(false);
        emailIt = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        noAccountTv = findViewById(R.id.noAccountTv);

        // Session manager
        session = new SessionManager(getApplicationContext());
        db1   = new DatabaseHelper(getApplicationContext());
        authentication();
        noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUser();
            }
        });



    }
    // Check if user is already logged in or not
    private void authentication() {


        Cursor res = db1.getAllData();
        if (session.isLoggedIn()){
            while (res.moveToNext()) {
                String account1 = res.getString(4);


                if (account1.equals("Seller")){
                    Intent intent = new Intent(LoginActivity.this,
                            ServiceProviderMain.class);
                    startActivity(intent);
                    finish();
                }
                else{


                    Intent intent = new Intent(LoginActivity.this,
                            MainUserActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }


    }

    private void logUser() {
        final String email,password;
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        email = emailIt.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Inavlid Email",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password Feild Empty",Toast.LENGTH_SHORT).show();
        }


        progressDialog.setMessage("Logging In");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
               progressDialog.dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        accountType = user.getString("accountType");
                        String address = user.getString("address");
                        String city = user.getString("city");
                        String country = user.getString("country");
                        String state = user.getString("state");
                        String latitude = user.getString("latitude");
                        String longitude = user.getString("longitude");
                        String phone = user.getString("phone");



                        addUser(name, email, uid,accountType,address,city,country,state,latitude,longitude,phone,created_at);


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);



    }

    private void addUser(String name, String email, String uid, String accountType, String address, String city, String country, String state, String latitude, String longitude, String phone, String created_at) {


     boolean b= db1.insertData(name, email, uid,accountType,address,city,country,state,latitude,longitude,phone,created_at);

     if (b){
         Toast.makeText(LoginActivity.this,"add",Toast.LENGTH_LONG).show();
     }else {
         Toast.makeText(LoginActivity.this,"failed",Toast.LENGTH_LONG).show();
     }

        checKUser();

    }

    private void checKUser() {

        progressDialog.setMessage("Checking User.. ");
        progressDialog.show();
        if (accountType.equals("Seller")){
            Intent intent = new Intent(LoginActivity.this,
                   ServiceProviderMain.class);
            startActivity(intent);
            finish();
        }
        else{


            Intent intent = new Intent(LoginActivity.this,
                    MainUserActivity.class);
            startActivity(intent);
            finish();
        }




    }
}
