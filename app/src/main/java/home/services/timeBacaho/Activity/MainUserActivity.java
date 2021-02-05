package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.services.timeBacaho.Adapter.AdapterCartItem;
import home.services.timeBacaho.Adapter.AdapterOrderUser;
import home.services.timeBacaho.Adapter.mainPageServicesAdapter;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.Models.MainPageServicesModelClass;
import home.services.timeBacaho.Models.ModelCartItem;
import home.services.timeBacaho.Models.ModelOrderUser;
import home.services.timeBacaho.R;

import home.services.timeBacaho.ReviewsActivity;
import home.services.timeBacaho.SQLiteHandler;
import home.services.timeBacaho.SessionManager;
import p32929.androideasysql_library.EasyDB;

public class MainUserActivity extends AppCompatActivity {
    static final String CHANNEL_ID = "technopoints_id";
    static final String CHANNEL_NAME = "technopoints name";
    static final String CHANNEL_DESC = "technopoints desc";
    SQLiteHandler sqLiteHandler1;
    TextView topName ,tabproductTv,tabOrderTv,phoneTv,emailTv,noOrders,cardCountTv;
DatabaseHelper db1;
private  mainPageServicesAdapter mainPageServicesAdapter;
private  MainPageServicesModelClass mainPageServicesModelClass;
 private    ImageButton logoutBt,cartBtn,editBtn;
    FloatingActionButton NavOpenBtn;
    private SessionManager session;
    private List<MainPageServicesModelClass> catList;
    private ArrayList<ModelCartItem> modelCartlist;
    private AdapterCartItem adapterCartItem;

    String name,email,phone;
    public String dilverFees = "300";

    boolean doubleBackToExitPressedOnce = false;

    ProgressBar progressBar;
    private RelativeLayout productsLR,OrderRL;
    private RecyclerView serviceRV,Deals;
    private ArrayList<ModelOrderUser> OrderUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        topName = findViewById(R.id.topName);
        logoutBt = findViewById(R.id.logoutBtn);
        productsLR = findViewById(R.id.productsLR);
        OrderRL= findViewById(R.id.ordersLR);
        tabproductTv= findViewById(R.id.tabproductTv);
        tabOrderTv = findViewById(R.id.tabOrderTv);
        NavOpenBtn = findViewById(R.id.NavOpenBtn);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        serviceRV = findViewById(R.id.serviceRV);
        progressBar = findViewById(R.id.progressBar);
      Deals = findViewById(R.id.Deals);
        noOrders = findViewById(R.id.noOrders);
        cartBtn = findViewById(R.id.cartBtn);
        cardCountTv = findViewById(R.id.cardCountTv);
        editBtn= findViewById(R.id.editBtn);

        // Session manager
        session = new SessionManager(getApplicationContext());
        db1 = new DatabaseHelper(getApplicationContext());

        sqLiteHandler1 = new SQLiteHandler(this);
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false);
        serviceRV.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainUserActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        Deals.setLayoutManager(linearLayoutManager);
        catList = new ArrayList<>();
        mainPageServicesAdapter = new mainPageServicesAdapter(this,catList);
        serviceRV.setAdapter(mainPageServicesAdapter);
        CartCount();

        CheckUserValid();
        showProductUi();
        fetchUserInfo();
        loadOrders();


        //Toast.makeText(MainUserActivity.this,""+email,Toast.LENGTH_LONG).show();
        tabproductTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductUi();
            }
        });
        tabOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOrderUI();

            }
        });

        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                   db1.removeAll();
                Intent intent = new Intent(MainUserActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });


        NavOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBottomNav();
            }
        });


        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCartDialoug();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainUserActivity.this,ProfileEditUserActivity.class));
            }
        });

    }

    public void CartCount() {
        int count = sqLiteHandler1.getAllData().getCount();

        if (count<0){
            //hide cartCountTv
            cardCountTv.setVisibility(View.GONE);
        }else{

            cardCountTv.setVisibility(View.VISIBLE);
            cardCountTv.setText(""+count);//concatinate with string cz int vale can not fix in textView

        }
    }

    public double allTotalPrice = 0.00;
    public  TextView sTotalTv,dTotalTv,alltotalPriceTV;

    private void ShowCartDialoug() {

        modelCartlist = new ArrayList<>();


        View view = LayoutInflater.from(this).inflate(R.layout.show_cart_items,null);


        sTotalTv = view.findViewById(R.id.sTotalTv);
        dTotalTv = view.findViewById(R.id.dTotalTv);
        alltotalPriceTV = view.findViewById(R.id.totalPrice);
        Button checkOutBtn = view.findViewById(R.id.checkOutBtn);
        RecyclerView   cartItemRv = view.findViewById(R.id.cartItemRv);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainUserActivity.this);
        builder.setView(view);

        ;


        //get All records from sqlite Db
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
        adapterCartItem = new AdapterCartItem(this,modelCartlist);
        cartItemRv.setAdapter(adapterCartItem);



        dTotalTv.setText("Rs"+dilverFees);
        sTotalTv.setText("Rs"+String.format("%.2f",allTotalPrice));
        alltotalPriceTV.setText("Rs"+(allTotalPrice + Double.parseDouble(dilverFees.replace("RS",""))));
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                allTotalPrice = 0.00;
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modelCartlist.size()==0){
                    Toast.makeText(MainUserActivity.this,"Cart Is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                proceedOrder();

            }
        });

    }

    private void proceedOrder() {

        startActivity(new Intent(MainUserActivity.this,DateTimeSecaduleActivity.class));
    }

    private void CheckUserValid() {
if (!session.isLoggedIn()){
    Intent intent = new Intent(MainUserActivity.this,
           LoginActivity.class);
    startActivity(intent);
    finish();

}else{
    loadServices();


}


    }










    private void loadServices() {



        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Main_Services,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           // Toast.makeText(MainUserActivity.this,""+response,Toast.LENGTH_LONG).show();

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray services = obj.getJSONArray("result");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < services.length(); i++) {

                                JSONObject sv = services.getJSONObject(i);
                              int Sid = sv.getInt("Sid");
                               String Sname = sv.getString("Sname");
                               String SImage = sv.getString("SImage");
                                String mainImage = AppConfig.IMAGE_MAIN_SERVICE_URL+SImage;


mainPageServicesModelClass = new MainPageServicesModelClass(Sid,Sname,mainImage);
catList.add(mainPageServicesModelClass);
mainPageServicesAdapter.notifyDataSetChanged();
progressBar.setVisibility(View.GONE);
                            }

                            //converting the string to json array object


                            //creating adapter object and setting it to recyclerview
                            mainPageServicesAdapter adapter = new mainPageServicesAdapter(MainUserActivity.this, catList);
                            serviceRV.setAdapter(adapter);
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainUserActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();

                        progressBar.setVisibility(View.INVISIBLE);

                    }

                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(MainUserActivity.this).add(stringRequest);



    }

    @SuppressLint("SetTextI18n")
    private void fetchUserInfo() {


        Cursor res = db1.getAllData();
        while (res.moveToNext()) {
             name = res.getString(1);
           email = res.getString(2);
            phone = res.getString(11);

        }
        topName.setText("" + name);
        emailTv.setText(""+email);
        phoneTv.setText(""+phone);

        loadOrders();
    }

    private void OpenBottomNav() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainUserActivity.this);
        //infilate view For Bottom Sheet
        View view =  LayoutInflater.from(MainUserActivity.this).inflate(R.layout.bottom_navigsation,null);
        bottomSheetDialog.setContentView(view);

        Button dealsBtn,redeemBtn,reviews;
        dealsBtn = view.findViewById(R.id.dealsBtn);
        redeemBtn = view.findViewById(R.id.redeemBtn);
        reviews = view.findViewById(R.id.reviews);
        bottomSheetDialog.show();

        dealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainUserActivity.this,DealsActivity.class));
            }
        });
        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainUserActivity.this,RedeemActivity.class));
            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainUserActivity.this, ReviewsActivity.class));
            }
        });

    }

    private void showOrderUI() {

        productsLR.setVisibility(View.GONE);
        OrderRL.setVisibility(View.VISIBLE);



        tabOrderTv.setTextColor(getResources().getColor(R.color.colorblack));
        tabOrderTv.setBackgroundResource(R.drawable.react04);

        tabproductTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabproductTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showProductUi() {
        productsLR.setVisibility(View.VISIBLE);
        OrderRL.setVisibility(View.GONE);


        tabproductTv.setTextColor(getResources().getColor(R.color.colorblack));
        tabproductTv.setBackgroundResource(R.drawable.react04);

        tabOrderTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabOrderTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    private void loadOrders() {


        noOrders.setVisibility(View.GONE);

        OrderUserList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_FetchOrderDetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            OrderUserList.clear();

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("result");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {

                                //getting product object from json array
                                JSONObject   ordersDetail = heroArray.getJSONObject(i);

                                //adding the product to product list
                                OrderUserList.add(new ModelOrderUser(

                                        ordersDetail.getInt("id"),
                                        ordersDetail.getString("orderId"),
                                        ordersDetail.getString("orderTime"),
                                        ordersDetail.getString("myLongitude"),
                                        ordersDetail.getString("mylatitude"),
                                        ordersDetail.getString("orderCost"),
                                        ordersDetail.getString("orderStatus"),
                                        ordersDetail.getString("timeday"),
                                        ordersDetail.getString("selectUserdate"),
                                        ordersDetail.getString("timeUserPick"),
                                        ordersDetail.getString("userEmail"),
                                        ordersDetail.getInt("Phone")
                                ));

                            }
                            //creating adapter object and setting it to recyclerview
                            AdapterOrderUser adapter = new AdapterOrderUser( MainUserActivity.this,OrderUserList);
                            Deals.setAdapter(adapter);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainUserActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("userEmail",email);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
