package home.services.timeBacaho;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import home.services.timeBacaho.Activity.DealsActivity;
import home.services.timeBacaho.Adapter.AdapterDeals;
import home.services.timeBacaho.Models.ModelDeals;

public class ServicesNotification extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public ServicesNotification() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void  FetchData(){
         final ArrayList<ModelDeals>  dealsArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Deals,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            boolean error = obj.getBoolean("error");
                            //we have the array named hero inside the object
                            //so here we are getting that json array

                            JSONArray dealsArray = obj.getJSONArray("result");
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            for (int i = 0; i < dealsArray.length(); i++) {


                                JSONObject   dealsDetail = dealsArray.getJSONObject(i);

                                dealsArrayList.add(new ModelDeals(
                                        dealsDetail.getInt("id"),
                                        dealsDetail.getString("dealName"),
                                        dealsDetail.getString("shortDescription"),
                                        dealsDetail.getString("longDescription"),
                                        dealsDetail.getString("Image"),
                                        dealsDetail.getString("Cost"),
                                        dealsDetail.getString("timeDuration")


                                ));



                            }





                        } catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();

                    }

                });

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}
