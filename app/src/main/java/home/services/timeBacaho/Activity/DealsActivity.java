package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;

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

import home.services.timeBacaho.Adapter.AdapterDeals;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.Models.ModelDeals;
import home.services.timeBacaho.R;


public class DealsActivity extends AppCompatActivity {

    private RecyclerView dealsRv;
    private SpinKitView progress_circular;
    private ArrayList<ModelDeals> modelDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        dealsRv = findViewById(R.id.dealsRv);
        progress_circular = findViewById(R.id.progress_circular);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DealsActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
       dealsRv.setLayoutManager(linearLayoutManager);
loadDeals();


    }

    private void loadDeals() {

modelDeals = new ArrayList<>();
progress_circular.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Deals,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                           // boolean error = obj.getBoolean("error");
                            //we have the array named hero inside the object
                            //so here we are getting that json array

                            JSONArray dealsArray = obj.getJSONArray("result");
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            for (int i = 0; i < dealsArray.length(); i++) {



                                JSONObject   dealsDetail = dealsArray.getJSONObject(i);
                                String images =  dealsDetail.getString("Image");
                                String url = AppConfig.IMAGE_DEALS_URL+images;

                                modelDeals.add(new ModelDeals(
                                        dealsDetail.getInt("id"),
                                        dealsDetail.getString("dealName"),
                                        dealsDetail.getString("shortDescription"),
                                        dealsDetail.getString("longDescription"),
                                        url,
                                        dealsDetail.getString("Cost"),
                                        dealsDetail.getString("timeDuration")


                                ));

                                progress_circular.setVisibility(View.GONE);
                                        AdapterDeals adapterDeals = new AdapterDeals(DealsActivity.this,modelDeals);
                                        dealsRv.setAdapter(adapterDeals);

                            }





                        } catch (JSONException e) {

                            e.printStackTrace();
                            progress_circular.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(DealsActivity.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        progress_circular.setVisibility(View.GONE);

                    }

                });

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}