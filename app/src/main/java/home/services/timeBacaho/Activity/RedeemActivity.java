package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.R;

public class RedeemActivity extends AppCompatActivity {
private TextView redeemBalance ;

private DatabaseHelper db1;
private  String email;
private   String   redeem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddem);

        db1 = new DatabaseHelper(getApplicationContext());

        redeemBalance = findViewById(R.id.redeemBalance);

    fetchUserInfo();
      loadRedeemPoints();

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

                               redeem = categories.getString("redeem");
                               Toast.makeText(RedeemActivity.this,""+email,Toast.LENGTH_LONG).show();




                            }


redeemBalance.setText(""+"Rs"+":"+redeem);





                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RedeemActivity.this,
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
    private void fetchUserInfo() {


        Cursor res = db1.getAllData();
        while (res.moveToNext()) {

            email = res.getString(2);

        }

    }

}