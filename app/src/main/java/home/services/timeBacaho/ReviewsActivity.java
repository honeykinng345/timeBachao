package home.services.timeBacaho;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.services.timeBacaho.Activity.ReviewActivity;
import home.services.timeBacaho.Adapter.ReviewsAdapter;
import home.services.timeBacaho.Models.Review;

public class ReviewsActivity extends AppCompatActivity {

    @BindView(R.id.recylerView)
    RecyclerView recylerView;

    private ArrayList<Review> reviewArrayList;
    private ReviewsAdapter reviewsAdapter;
    Review review;
    Gson gson ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        gson = new Gson();
        RecevingData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recylerView.setLayoutManager(linearLayoutManager);
        reviewArrayList = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviewArrayList);
        recylerView.setAdapter(reviewsAdapter);
    }

    private void RecevingData() {


        String tag_string_req = "req_login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_reviewsAll,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //Toast.makeText(getContext(),""+response,Toast.LENGTH_LONG).show();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray jba = obj.getJSONArray("result");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jba.length(); i++) {

                                review = gson.fromJson(String.valueOf(jba.getJSONObject(i)), Review.class);

                                //school.setLicenecePicture("url"+school.getLicenecePicture());

                                //JSONObject sv = services.getJSONObject(i);

                                //String Demand = sv.getString("Demand");
                                //tring salary = sv.getString("salary");


                                //school = new School(Sid,Sname,url);
                                reviewArrayList.add(review);
                                reviewsAdapter.notifyDataSetChanged();

                                //showingJobAdapter.notifyDataSetChanged();
                                //getting product object from json array
                                //  JSONObject categories = heroArray.getJSONObject(i);

                                //adding the product to product list

                            }
                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                       // Toast.makeText(ReviewActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        // spinKitView.setVisibility(View.GONE);


                    }

                });

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
}