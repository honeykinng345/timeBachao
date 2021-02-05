package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.services.timeBacaho.Adapter.CategoriesAdapter;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.Models.categories;
import home.services.timeBacaho.R;

public class CatgoriesActivity extends AppCompatActivity {

  private   TextView titlTv;
  private   RecyclerView catRV;

  EditText filterProductEdt;

    private List<categories> catList;
    private String catgeories_id,serviceName;

    CategoriesAdapter adapter;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catgories);
        titlTv = findViewById(R.id.titlTv);
        catRV = findViewById(R.id.catRV);
        filterProductEdt = findViewById(R.id.filterProductTv);
        progressBar = findViewById(R.id.progressBar);

        filterProductEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });


        Intent intent = getIntent();
        catgeories_id =  intent.getStringExtra("cid");
       serviceName=  intent.getStringExtra("ServiceTitle");

       titlTv.setText(serviceName);




        int resId = R.anim.layout_animation_up_to_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        catRV.setLayoutAnimation(animation);





        //this method will fetch and parse json
        //to display it in recyclerview
        loadCategories();






    }

    private void filter(String text) {



        ArrayList<categories> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (categories s : catList) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }

        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    private void loadCategories() {

        //initializing the productlist
        catList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Cat,
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
                              int  cid =   categories.getInt("cid");
                                String name =categories.getString("name");

                                String image =        categories.getString("image");
                                String url = AppConfig.IMAGE_CATEGORIES_SERVICE_URL+image;
                                //adding the product to product list
                           /*     catList.add(new categories(



                                ));*/

                          catList.add(new categories(cid,name,url));
                                progressBar.setVisibility(View.GONE);
                            }
                            //creating adapter object and setting it to recyclerview
                            adapter = new CategoriesAdapter(CatgoriesActivity.this, catList);
                            catRV.setAdapter(adapter);
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(CatgoriesActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("ServiceID", catgeories_id );


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
