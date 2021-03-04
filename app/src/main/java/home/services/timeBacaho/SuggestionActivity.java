package home.services.timeBacaho;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import home.services.timeBacaho.Activity.MainUserActivity;
import home.services.timeBacaho.Activity.ReviewActivity;

public class SuggestionActivity extends AppCompatActivity {

    @BindView(R.id.relavtive1)
    RelativeLayout relavtive1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.subject)
    EditText subject;
    @BindView(R.id.message)
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.tv2, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv2:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:03174945474"));
                startActivity(intent);
                break;
            case R.id.submit:

                submitData(name.getText().toString().trim(),phone.getText().toString().trim(),subject.getText().toString().trim(),message.getText().toString().trim());
                break;
        }
    }

    private void submitData(String name, String phone, String subject, String message) {

        if (name.isEmpty()){
            return;
        }
        if (phone.isEmpty()){
            return;
        }

        if (subject.isEmpty()){
            return;
        }
        if (message.isEmpty()){
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_contact_US, response -> {

            Toast.makeText(SuggestionActivity.this,"Thanks for Contact us", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SuggestionActivity.this, MainUserActivity.class));



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SuggestionActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();


                params.put("name",name);
                params.put("phone",phone);
                params.put("subject",subject);
                params.put("message", message);



                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);

        


    }
}
