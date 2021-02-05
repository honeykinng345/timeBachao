package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.R;

public class ReviewActivity extends AppCompatActivity {
    private FloatingActionButton reviewbtn;
    private EditText comments;
    private RatingBar rateBar;
  private   DatabaseHelper db1;
private  String name , email;
private  String status = "0";
String message;
String itemNAme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        db1 = new DatabaseHelper(getApplicationContext());
        reviewbtn = findViewById(R.id.reviewbtn);
        comments = findViewById(R.id.comments);
        rateBar = findViewById(R.id.rateBar);

       itemNAme = getIntent().getStringExtra("name");

       Toast.makeText(ReviewActivity.this,""+itemNAme,Toast.LENGTH_LONG).show();

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReview();
            }
        });
        fetchUserName();

    }

    private void fetchUserName() {

        Cursor res = db1.getAllData();
        while (res.moveToNext()) {
            name = res.getString(1);
            email = res.getString(2);


        }
    }

    private void sendReview() {

     final String   timeStamp = "" + System.currentTimeMillis();
        message = comments.getText().toString().trim();

        final float rating = rateBar.getRating();
        if (message.equals("") || message.equals("null")){
            message = "No Message";
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_ADDReview, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ReviewActivity.this,"Thanks for your  Review ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ReviewActivity.this,MainUserActivity.class));
                comments.setText(" ");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ReviewActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();


                params.put("userEmail",email);
                params.put("rating",String.valueOf(rating));
                params.put("comment",message);
                params.put("status", status);
                params.put("userName",name);
                params.put("timeday",timeStamp);
                params.put("itemName",itemNAme);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}