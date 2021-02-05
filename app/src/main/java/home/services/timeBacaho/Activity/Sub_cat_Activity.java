package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import home.services.timeBacaho.Adapter.AdapterCartItemsExtra;
import home.services.timeBacaho.Adapter.subCategoriesAdaper;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.Helper;
import home.services.timeBacaho.Models.ModelCartItem;
import home.services.timeBacaho.Models.categories;
import home.services.timeBacaho.Models.subCategories;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;


import static java.lang.String.*;

public class Sub_cat_Activity extends AppCompatActivity {

    private List<subCategories> subList;
   String catgeories_id ,tvName;
  TextView titlTv,cardCountTv;
  SpinKitView spinKitView;
    private RecyclerView recyclerView;
    ImageButton cartBtn;
    double redeem = 0;
    public String dilverFees = "300";
    private ArrayList<ModelCartItem> modelCartlist;
 EditText searchProductEt;
    private SQLiteHandler sqLiteHandler;

    subCategoriesAdaper adapter;
    private  String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat_);
        Intent intent = getIntent();
        catgeories_id =  intent.getStringExtra("cid");
        tvName=  intent.getStringExtra("title");

        cartBtn= findViewById(R.id.cartBtn);

       spinKitView = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
       spinKitView.setIndeterminateDrawable(doubleBounce);

        titlTv = findViewById(R.id.titlTv);
        cardCountTv = findViewById(R.id.cardCountTv);
        recyclerView = findViewById(R.id.productsRV);
        searchProductEt = findViewById(R.id.searchProductEt);
        titlTv.setText(tvName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Sub_cat_Activity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        sqLiteHandler = new SQLiteHandler(Sub_cat_Activity.this);

        CartCount();
        loadSubcategories();
        loadRedeemPoints();


        cartBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ShowCartDialoug();
    }
});
        //initializing the productlist
        //this method will fetch and parse json
        //to display it in recyclerview



         searchProductEt.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
             }

             @Override
             public void afterTextChanged(Editable s) {


                 search(s.toString());
             }
         });







    }

    private void search(String text) {


        ArrayList<subCategories> filterdNames = new ArrayList<>();

        for (subCategories c : subList){

            if (c.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(c);
            }else {
                Toast.makeText(Sub_cat_Activity.this," No Record Found",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();

            }


        }

        adapter.filterList(filterdNames);




    }

    @SuppressLint("SetTextI18n")
    public void CartCount() {

        int count = sqLiteHandler.getAllData().getCount();

        if (count<0){
            //hide cartCountTv
            cardCountTv.setVisibility(View.GONE);
        }else{

            cardCountTv.setVisibility(View.VISIBLE);
            cardCountTv.setText(""+count);//concatinate with string cz int vale can not fix in textView

        }

    }

    public double allTotalPrice = 0.00;
    public  TextView sTotalTv,dTotalTv,alltotalPriceTV,redeemText;
    SQLiteHandler sqLiteHandler1;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void ShowCartDialoug() {
        sqLiteHandler1 = new SQLiteHandler(this);
        modelCartlist = new ArrayList<>();


        View view = LayoutInflater.from(this).inflate(R.layout.show_cart_items,null);


        sTotalTv = view.findViewById(R.id.sTotalTv);
        dTotalTv = view.findViewById(R.id.dTotalTv);
        alltotalPriceTV = view.findViewById(R.id.totalPrice);
        redeemText = view.findViewById(R.id.redeemText);

        Button checkOutBtn = view.findViewById(R.id.checkOutBtn);
        RecyclerView   cartItemRv = view.findViewById(R.id.cartItemRv);

        AlertDialog.Builder builder = new AlertDialog.Builder(Sub_cat_Activity.this);
        builder.setView(view);




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
        AdapterCartItemsExtra adapterCartItemsExtra= new AdapterCartItemsExtra(this,modelCartlist);
        cartItemRv.setAdapter(adapterCartItemsExtra);



        dTotalTv.setText("Rs"+dilverFees);
        sTotalTv.setText("Rs"+ format("%.2f",allTotalPrice));

        if (redeem == 500){
            alltotalPriceTV.setText("Rs"+(allTotalPrice + Double.parseDouble(dilverFees.replace("Rs",""))-redeem));
            redeemText.setVisibility(View.VISIBLE);
        }else{
            alltotalPriceTV.setText("Rs"+(allTotalPrice + Double.parseDouble(dilverFees.replace("RS",""))));
            redeemText.setVisibility(View.GONE);
        }

        final AlertDialog dialog = builder.create();
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

                    dialog.dismiss();
                    showDialoug();
                   // Toast.makeText(Sub_cat_Activity.this,"Cart Is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    dialog.dismiss();
                    proceedOrder();

                }



            }
        });
    }

    private void showDialoug() {
        LottieAnimationView anim;
        TextView cartEmptyTv,titleTv;
        View view = LayoutInflater.from(this).inflate(R.layout.row_thanks,null);
        ImageButton imageButton = view.findViewById(R.id.backBtn);
        cartEmptyTv = view.findViewById(R.id.cartEmptyTv);
        titleTv = view.findViewById(R.id.titleTv);
        anim = (LottieAnimationView)view.findViewById(R.id.emptyCartAnim);
        AlertDialog.Builder builder = new AlertDialog.Builder(Sub_cat_Activity.this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        titleTv.setText("Your cart Is Empty");
        anim.setVisibility(View.VISIBLE);
        cartEmptyTv.setVisibility(View.VISIBLE);

        dialog.show();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialog.dismiss();
            }
        });
    }

    private void proceedOrder() {

         Intent intent = new Intent(Sub_cat_Activity.this,DateTimeSecaduleActivity.class);
         startActivity(intent);
         //finish();
       // startActivity(new Intent(Sub_cat_Activity.this,DateTimeSecaduleActivity.class));
    }

    private void loadSubcategories() {

       spinKitView.setVisibility(View.VISIBLE);
        subList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_subcat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("result");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {

                                //getting product object from json array
                                JSONObject categories = heroArray.getJSONObject(i);
                                String images =  categories.getString("Simage");
                                String url = AppConfig.IMAGE_SUB_CAT_SERVICE_URL+images;
                                //adding the product to product list
                                subList.add(new subCategories(
                                   categories.getInt("id"),
                                        categories.getInt("cid"),
                                        categories.getString("title"),
                                        categories.getString("description"),
                                        categories.getString("duration"),
                                        categories.getString("Discountprices"),
                                        url,
                                        categories.getString("OrigonalPrice"),
                                        categories.getString("discountNotes"),
                                        categories.getString("discountAvaliabel")



                                ));
                                spinKitView.setVisibility(View.GONE);
                            }
                            //creating adapter object and setting it to recyclerview
                     adapter = new subCategoriesAdaper(Sub_cat_Activity.this, subList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                          spinKitView.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Sub_cat_Activity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                       spinKitView.setVisibility(View.GONE);


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("cid", catgeories_id );


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
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

                               // Toast.makeText(Sub_cat_Activity.this,""+redeem,Toast.LENGTH_LONG).show();


                            }








                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Sub_cat_Activity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("email", Helper.user_id(getApplicationContext()));


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }




}
