package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import home.services.timeBacaho.Adapter.AdapterOrderItems;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.Models.ModelOrderItem;
import home.services.timeBacaho.R;

public class OrderDetailActivity extends AppCompatActivity {
 private  String email ;
 private ImageButton reviews;
    String itmName;
    private TextView orderIdTv,dateTv,statusTv,shopnameTv,itemsTv,AddressTv,priceTv;
  private  String id ;
    private RecyclerView itemRV;
    private    ArrayList<ModelOrderItem> modelOrderItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderIdTv= findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        statusTv = findViewById(R.id.statusTv);

        itemsTv = findViewById(R.id.itemsTv);
        AddressTv = findViewById(R.id.AddressTv);
        itemRV = findViewById(R.id.itemRV);
        priceTv = findViewById(R.id.priceTv);
        reviews = findViewById(R.id.reviews);
        email =  getIntent().getStringExtra("email");
        id =  getIntent().getStringExtra("id");

        loadOrders();
        loadOrderDetailItems();

reviews.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendtoReviewsActiivity();
    }
});
    }

    private void sendtoReviewsActiivity() {

       Intent intent = new Intent(OrderDetailActivity.this,ReviewActivity.class);
       intent.putExtra("name",itmName);
       startActivity(intent);




    }


    private void loadOrders() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_UsersOrderListDetail,
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

                          String    ordrid = categories.getString("orderId");
                                String date = categories.getString("orderId");
                                String orderStatus = categories.getString("orderStatus");
                                String cost = categories.getString("orderCost");
                                String myLongitude = categories.getString("myLongitude");
                                String mylatitude = categories.getString("mylatitude");


                                switch (orderStatus) {
                                    case "In progress":
                                        statusTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                                        break;
                                    case "Completed":
                                        statusTv.setTextColor(getResources().getColor(R.color.colorGreen));
                                        break;
                                    case "Canceled":
                                        statusTv.setTextColor(getResources().getColor(R.color.colorred));
                                        break;
                                }
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(date));
                                String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();


                                orderIdTv.setText(""+ordrid);
                                dateTv.setText(""+formatTime);
                                statusTv.setText(orderStatus);
                                priceTv.setText("Rs"+cost+"-["+"Including Service Fees"+"]");

                                findLocation(mylatitude,myLongitude);

                            }








                            } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(OrderDetailActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("id",id);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void findLocation(String mylatitude, String myLongitude) {

        double lat = Double.parseDouble(mylatitude);

        double longlitude = Double.parseDouble(myLongitude);

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {

            addresses = geocoder.getFromLocation(lat,longlitude,1);
            String address = addresses.get(0).getAddressLine(0);//get Complete Address
            AddressTv.setText(address);



        }catch (Exception e){

            Toast.makeText(this,""+e,Toast.LENGTH_SHORT).show();
        }
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
                               itmName = orderDetailItems.getString("Itemname");
                                Toast.makeText(OrderDetailActivity.this,""+itmName,Toast.LENGTH_LONG).show();

                                modelOrderItems.add(new ModelOrderItem(

                                        orderDetailItems.getInt("id"),


                                      itmName,
                                        orderDetailItems.getString("cost"),
                                        orderDetailItems.getInt("pid"),
                                        orderDetailItems.getString("price"),
                                        orderDetailItems.getInt("quantity"),
                                        orderDetailItems.getString("timestamps")




                                ));
                                AdapterOrderItems adapterOrderItems = new AdapterOrderItems(OrderDetailActivity.this,modelOrderItems);
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

                        Toast.makeText(OrderDetailActivity.this,
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