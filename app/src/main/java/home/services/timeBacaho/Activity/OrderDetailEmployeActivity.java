package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import home.services.timeBacaho.Adapter.AdapterOrderItems;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.Models.ModelOrderItem;
import home.services.timeBacaho.R;

public class OrderDetailEmployeActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private TextView orderIdTv,dateTv,timeTv,statusTv,nameTv,itemsTv,AddressTv,priceTv,phoneTv;
    private RecyclerView itemRV;
    private DatabaseHelper db1;

  private  String id;

  private  String email;
    private    ArrayList<ModelOrderItem> modelOrderItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_employe);

db1= new DatabaseHelper(getApplicationContext());
        backBtn = findViewById(R.id.backBtn);
        orderIdTv= findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv);
        statusTv = findViewById(R.id.statusTv);
        nameTv= findViewById(R.id.nameTv);
        itemsTv = findViewById(R.id.itemsTv);
        AddressTv = findViewById(R.id.AddressTv);
        itemRV = findViewById(R.id.itemRV);
        priceTv = findViewById(R.id.priceTv);
        phoneTv = findViewById(R.id.phoneTv);

        Intent intent = getIntent();
       id   =  intent.getStringExtra("id");

       backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
        fetchUserInfo();
        loadOrders();
        loadOrderDetailItems();





    }

    private void fetchUserInfo() {


        Cursor res = db1.getAllData();
        while (res.moveToNext()) {

            email = res.getString(2);

        }


    }
    private void loadOrders() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SendNewOrderToEmploye,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            boolean error = obj.getBoolean("error");
                            //we have the array named hero inside the object
                            //so here we are getting that json array

                            JSONArray heroArray = obj.getJSONArray("result");
                            // User successfully stored in MySQL
                            // Now store the user in sqlite

                            for (int i = 0; i < heroArray.length(); i++) {

                                //getting product object from json array
                                JSONObject categories = heroArray.getJSONObject(i);
                                String clientName = categories.getString("clientName");
                                String orderStatus = categories.getString("status");
                                String    ordrid = categories.getString("orderId");
                                String   address = categories.getString("clientAddress");
                                String   phone = categories.getString("clientPhone");
                                String   date = categories.getString("dateService");
                                String   time = categories.getString("timeService");
                                String   message = categories.getString("message");


                                String cost = categories.getString("cost");





                                orderIdTv.setText(""+ordrid);
                                dateTv.setText(""+date);
                                timeTv.setText(""+time);
                                AddressTv.setText(""+address);
                                statusTv.setText(orderStatus);
                                phoneTv.setText(""+phone);
                                nameTv.setText(""+clientName);
                                priceTv.setText("Rs"+cost+"["+"Including Service Fees"+"]");



                            }








                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(OrderDetailEmployeActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


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
    private void loadOrderDetailItems() {


        modelOrderItems = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_FetchOrderDetailItems,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            // boolean error = obj.getBoolean("error");
                            //we have the array named hero inside the object
                            //so here we are getting that json array

                            JSONArray heroArray = obj.getJSONArray("result");
                            // User successfully stored in MySQL
                            // Now store the user in sqlite

                            for (int i = 0; i < heroArray.length(); i++) {

                                //getting product object from json array
                                JSONObject orderDetailItems = heroArray.getJSONObject(i);

                                modelOrderItems.add(new ModelOrderItem(

                                        orderDetailItems.getInt("id"),
                                        orderDetailItems.getString("Itemname"),
                                        orderDetailItems.getString("cost"),
                                        orderDetailItems.getInt("pid"),
                                        orderDetailItems.getString("price"),
                                        orderDetailItems.getInt("quantity"),
                                        orderDetailItems.getString("timestamps")




                                ));
                                AdapterOrderItems adapterOrderItems = new AdapterOrderItems(OrderDetailEmployeActivity.this,modelOrderItems);
                                itemRV.setAdapter(adapterOrderItems);
                                int count = 0;
                                if (adapterOrderItems != null) {
                                    count = adapterOrderItems.getItemCount();
                                    itemsTv.setText(""+count);

                                }


                            }








                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(OrderDetailEmployeActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("timestamps",id);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}