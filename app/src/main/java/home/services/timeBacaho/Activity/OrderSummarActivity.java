package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import home.services.timeBacaho.Adapter.OrderSummaryAdpater;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.Models.ModelCartItem;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;

public class OrderSummarActivity extends AppCompatActivity {
    String date , time, message;
    public TextView alltotalPriceTV,dateTv,timeTv,phoneTv,userAddress,redeemText;
private RecyclerView rVOrderSummary;
private CheckBox termConditionCheckBox;
private DatabaseHelper db1;
private Button checkOutBtn;
private String Mylatitude,mylongitutde,myEmail;
private  String OrderStatus = "In Progress";
    String timeStamp = " ";
    double redeem = 0 ;
    public double allTotalPrice = 0.00;
    SQLiteHandler sqLiteHandler1;
    private ArrayList<ModelCartItem> modelCartlist;
    private OrderSummaryAdpater adapterCartItem;
    public String dilverFees = "300";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summar);

        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        message = getIntent().getStringExtra("message");
        message = getIntent().getStringExtra("message");

        db1 = new DatabaseHelper(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        sqLiteHandler1 = new SQLiteHandler(this);


        rVOrderSummary = findViewById(R.id.rVOrderSummary);
        alltotalPriceTV = findViewById(R.id.alltotalPriceTV);
        termConditionCheckBox = findViewById(R.id.termConditionCheckBox);
        dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv);
        redeemText = findViewById(R.id.redeemText);
        phoneTv = findViewById(R.id.phoneTv);
        userAddress  = findViewById(R.id.userAddress);
        checkOutBtn = findViewById(R.id.checkOutBtn);


        dateTv.setText(date);
        timeTv.setText(time);
        LoadUserInfo();
        loadData();
        loadRedeemPoints();







        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!termConditionCheckBox.isChecked()){
                    Toast.makeText(OrderSummarActivity.this,"Please Check Term & Condition Box",Toast.LENGTH_LONG).show();
                    return;

                }

                if (message.equals("") || message.equals("null")){
                    message = "No Message";
                }
               submitOrder();
            }
        });

    }



    private void submitOrder() {
        progressDialog.setMessage("Your Order Is In Processing");
        progressDialog.show();
        if (redeem == 500){

            timeStamp = "" + System.currentTimeMillis();
            final String TotalAmount = alltotalPriceTV.getText().toString().trim().replace("Rs", "");
            final String phone = phoneTv.getText().toString().trim();



            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_OrderDetail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {




                    updateRedeemValue();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(OrderSummarActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> params = new HashMap<>();


                    params.put("orderId",timeStamp);
                    params.put("orderTime",timeStamp);
                    params.put("myLongitude",mylongitutde);
                    params.put("mylatitude",Mylatitude);
                    params.put("orderCost",TotalAmount);
                    params.put("orderStatus",OrderStatus);
                    params.put("timeday",timeStamp);
                    params.put("message",message);
                    params.put("selectUserdate",date);
                    params.put("timeUserPick",time);
                    params.put("userEmail",myEmail);
                    params.put("Phone",phone);




                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(OrderSummarActivity.this);
            requestQueue.add(stringRequest);

        }else {

            timeStamp = "" + System.currentTimeMillis();
            final String TotalAmount = alltotalPriceTV.getText().toString().trim().replace("Rs", "");
            final String phone = phoneTv.getText().toString().trim();



            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_OrderDetail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    IncreamentRedeemValue();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(OrderSummarActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> params = new HashMap<>();


                    params.put("orderId",timeStamp);
                    params.put("orderTime",timeStamp);
                    params.put("myLongitude",mylongitutde);
                    params.put("mylatitude",Mylatitude);
                    params.put("orderCost",TotalAmount);
                    params.put("orderStatus",OrderStatus);
                    params.put("timeday",timeStamp);
                    params.put("message",message);
                    params.put("selectUserdate",date);
                    params.put("timeUserPick",time);
                    params.put("userEmail",myEmail);
                    params.put("Phone",phone);




                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(OrderSummarActivity.this);
            requestQueue.add(stringRequest);
        }


    }

    private void IncreamentRedeemValue() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_IncreamentRedeemValue,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SaveItem();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(OrderSummarActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("email",myEmail);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);


    }

    private void updateRedeemValue() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_UpdateRedeemPoints,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
SaveItem();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(OrderSummarActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("email",myEmail);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    private void SaveItem() {

        try{
            for (int i=0;i<modelCartlist.size();i++){
                final String pid = modelCartlist.get(i).getPid();
                String id = modelCartlist.get(i).getId();
                final String cost = modelCartlist.get(i).getCost();
                final String price  = modelCartlist.get(i).getPrice();
                final String quantity= modelCartlist.get(i).getQuantity();
                final String name = modelCartlist.get(i).getName();

              //  Toast.makeText(OrderSummarActivity.this,""+price,Toast.LENGTH_LONG).show();



                StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_OrderItems,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Itemname",""+name);
                        params.put("cost",""+cost);
                        params.put("pid",""+pid);
                        params.put("price",""+price);
                        params.put("quantity",""+quantity);
                        params.put("timestamps",""+timeStamp);
                        return params;
                    }
                };
                        // databaseReference.child(timeStamp).child("Items").child(pid).setValue(hashMap);
                RequestQueue requestQueue = Volley.newRequestQueue(OrderSummarActivity.this);
                requestQueue.add(stringRequest);

                //endregion

            }
            deleteData();



        }catch(Exception e){

            Toast.makeText(OrderSummarActivity.this,"Error.."+e ,Toast.LENGTH_SHORT).show();
        }








    }
    private void deleteData() {
progressDialog.dismiss();
        sqLiteHandler1.removeAll();
    /*    Intent intent = new Intent(OrderSummarActivity.this,
                OrderDetailActivity.class);
        startActivity(intent);
        finish();*/
        Intent intent = new Intent(OrderSummarActivity.this, MainUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        OrderSummarActivity.this.finish();
        Toast.makeText(OrderSummarActivity.this,"Oder Submited",Toast.LENGTH_LONG).show();
    }
    private void LoadUserInfo() {


        Cursor res = db1.getAllData();
        while (res.moveToNext()) {
            myEmail = res.getString(2);
            String     addresss= res.getString(5);
            Mylatitude = res.getString(9);
            mylongitutde = res.getString(10);

            String      phone = res.getString(11);


            phoneTv.setText(phone);
            userAddress.setText(addresss);

        }



    }

    private void loadData() {

        modelCartlist = new ArrayList<>();
        Cursor res = sqLiteHandler1.getAllData();
        while (res.moveToNext()){
            String id = res.getString(0);
            String pid= res.getString(1);
            String name  = res.getString(2);
            String price= res.getString(3);
            String cost = res.getString(4);
            String quantity = res.getString(5);
            allTotalPrice = allTotalPrice + Double.parseDouble(cost);



            ModelCartItem cartItem = new ModelCartItem(""+id,
                    ""+pid,
                    ""+name,
                    ""+price,
                    ""+cost,
                    ""+quantity);

            modelCartlist.add(cartItem);




        }
        adapterCartItem = new OrderSummaryAdpater(this,modelCartlist);
        rVOrderSummary.setAdapter(adapterCartItem);








    }
    private void loadRedeemPoints() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_FetchRedeemUserGiftPoints,
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

                                redeem = categories.getDouble("redeem");

                         //       Toast.makeText(OrderSummarActivity.this,""+redeem,Toast.LENGTH_LONG).show();


                                if (redeem == 500){
                                    alltotalPriceTV.setText("Rs"+(allTotalPrice + Double.parseDouble(dilverFees.replace("Rs",""))-redeem));
                                    redeemText.setVisibility(View.VISIBLE);

                                }else{
                                    alltotalPriceTV.setText("Rs"+(allTotalPrice + Double.parseDouble(dilverFees.replace("RS",""))));
                                    redeemText.setVisibility(View.GONE);
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

                        Toast.makeText(OrderSummarActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("email",myEmail);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }




}