package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import home.services.timeBacaho.Adapter.AdapterNewOrdersEmploye;
import home.services.timeBacaho.Adapter.AdapterOrderHistoryEmploye;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.Models.ModelNewOrderEmploye;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SessionManager;

public class ServiceProviderMain extends AppCompatActivity {
    private RelativeLayout newOrderRl,ordersHistoryRL;
    private RecyclerView   newOrderRV,orderRV;
    private TextView tabNewOrderTv,tabOrderHistoryTv,topName,phoneTv,emailTv;
    private ImageButton logoutBtn,editBtn;
    private DatabaseHelper db1;
    private SessionManager sessionManager;
    private  ArrayList<ModelNewOrderEmploye> newOrderEmployes;
    private  ArrayList<ModelNewOrderEmploye> orderHistoryEmploye;


    private SpinKitView spin_kit;
    private  String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_main);
        topName = findViewById(R.id.topName);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        tabNewOrderTv= findViewById(R.id.tabNewOrderTv);
        tabOrderHistoryTv = findViewById(R.id.tabOrderHistoryTv);
        newOrderRl = findViewById(R.id.newOrderRl);
        ordersHistoryRL = findViewById(R.id.ordersHistoryRL);
        logoutBtn = findViewById(R.id.logoutBtn);
        newOrderRV= findViewById(R.id.newOrderRV);
        orderRV= findViewById(R.id.orderRV);
        spin_kit= findViewById(R.id.spin_kit);
        editBtn= findViewById(R.id.editBtn);

        db1 = new DatabaseHelper(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceProviderMain.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        newOrderRV.setLayoutManager(linearLayoutManager);


        showNewOrderUi();
        fetchUserInfo();
        CheckUserValid();
        loadOrdersHistoryEmploye();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        tabNewOrderTv.setOnClickListener(v -> showNewOrderUi());
        tabOrderHistoryTv.setOnClickListener(v -> showOrderHistoryUi());
        editBtn.setOnClickListener(v -> startActivity(new Intent(ServiceProviderMain.this,ProfileEditUserActivity.class)));
        Toast.makeText(ServiceProviderMain.this,""+email,Toast.LENGTH_LONG).show();

    }

    private void logoutUser() {


        db1.removeAll();
        Intent intent = new Intent(ServiceProviderMain.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showOrderHistoryUi() {

        ordersHistoryRL.setVisibility(View.VISIBLE);
        newOrderRl.setVisibility(View.GONE);


        tabOrderHistoryTv.setTextColor(getResources().getColor(R.color.colorblack));
        tabOrderHistoryTv.setBackgroundResource(R.drawable.react04);

       tabNewOrderTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabNewOrderTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showNewOrderUi() {


        newOrderRl.setVisibility(View.VISIBLE);
        ordersHistoryRL.setVisibility(View.GONE);


       tabNewOrderTv.setTextColor(getResources().getColor(R.color.colorblack));
       tabNewOrderTv.setBackgroundResource(R.drawable.react04);

        tabOrderHistoryTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabOrderHistoryTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    @SuppressLint("SetTextI18n")
    private void fetchUserInfo() {


        Cursor res = db1.getAllData();
        while (res.moveToNext()) {
          String  name = res.getString(1);
          email = res.getString(2);
           String phone = res.getString(11);
            topName.setText("" + name);
            emailTv.setText(""+email);
            phoneTv.setText(""+phone);
        }


    }

    private void CheckUserValid() {
        if (!sessionManager.isLoggedIn()){
            Intent intent = new Intent(ServiceProviderMain.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();

        }else{
            loadNewOrders();


        }


    }

    private void loadNewOrders() {

        spin_kit.setVisibility(View.VISIBLE);
        newOrderEmployes = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SendNewOrderToEmploye,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

newOrderEmployes.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray newOrderArray = obj.getJSONArray("result");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < newOrderArray.length(); i++) {

                                //getting product object from json array
                                JSONObject   newOrders = newOrderArray.getJSONObject(i);

                                //adding the product to product list
                                newOrderEmployes.add(new ModelNewOrderEmploye(

                                        newOrders.getInt("id"),
                                        newOrders.getString("EmployeEmail"),
                                        newOrders.getString("clientName"),
                                        newOrders.getString("status"),
                                        newOrders.getString("orderId"),
                                        newOrders.getString("clientCity"),
                                        newOrders.getString("clientCountry"),
                                        newOrders.getString("clientAddress"),
                                        newOrders.getInt ("clientPhone"),
                                        newOrders.getString("dateService"),
                                        newOrders.getString("timeService"),
                                        newOrders.getString("message"),
                                        newOrders.getString("cost"),
                                        newOrders.getString("created_us")


                                ));


spin_kit.setVisibility(View.GONE);

                            }
                            //creating adapter object and setting it to recyclerview
                            AdapterNewOrdersEmploye adapter = new AdapterNewOrdersEmploye( ServiceProviderMain.this,newOrderEmployes);
                           newOrderRV.setAdapter(adapter);

                        } catch (JSONException e) {

                            e.printStackTrace();
                            spin_kit.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ServiceProviderMain.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();

                        spin_kit.setVisibility(View.GONE);
                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("email",email);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);



    }
    private void loadOrdersHistoryEmploye() {



        spin_kit.setVisibility(View.VISIBLE);
       orderHistoryEmploye = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SendOrderHistoryToEmploye,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                           orderHistoryEmploye.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray newOrderArray = obj.getJSONArray("result");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < newOrderArray.length(); i++) {

                                //getting product object from json array
                                JSONObject   newOrders = newOrderArray.getJSONObject(i);

                                //adding the product to product list
                                orderHistoryEmploye.add(new ModelNewOrderEmploye(

                                        newOrders.getInt("id"),
                                        newOrders.getString("EmployeEmail"),
                                        newOrders.getString("clientName"),
                                        newOrders.getString("status"),
                                        newOrders.getString("orderId"),
                                        newOrders.getString("clientCity"),
                                        newOrders.getString("clientCountry"),
                                        newOrders.getString("clientAddress"),
                                        newOrders.getInt ("clientPhone"),
                                        newOrders.getString("dateService"),
                                        newOrders.getString("timeService"),
                                        newOrders.getString("message"),
                                        newOrders.getString("cost"),
                                        newOrders.getString("created_us")


                                ));


                                spin_kit.setVisibility(View.GONE);

                            }
                            //creating adapter object and setting it to recyclerview
                            AdapterOrderHistoryEmploye adapter = new AdapterOrderHistoryEmploye( ServiceProviderMain.this, orderHistoryEmploye);
                            orderRV.setAdapter(adapter);

                        } catch (JSONException e) {

                            e.printStackTrace();
                            spin_kit.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ServiceProviderMain.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();

                        spin_kit.setVisibility(View.GONE);
                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("email",email);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);


    }
}
