package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import home.services.timeBacaho.R;

public class DateTimeSecaduleActivity extends AppCompatActivity {

    private Button continueBtn;
    private EditText messageUser;
    private TextView timeTv1,timeTv2,timeTv3,timeTv4,datePicker1;
    private  String savePickTime = "null";
    DatePickerDialog datePickerDialog;
    String UserMessage = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_secadule);

        continueBtn = findViewById(R.id.continueBtn);
        datePicker1 = findViewById(R.id.datePicker1);
        timeTv1 = findViewById(R.id.timeTv1);
        timeTv2= findViewById(R.id.timeTv2);
        timeTv3= findViewById(R.id.timeTv3);
        timeTv4 = findViewById(R.id.timeTv4);
        messageUser = findViewById(R.id.messageUser);

        datePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog1();

            }
        });

        timeTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MorningTime();



            }
        });
        timeTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AfterNoonTime();



            }
        });

        timeTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AfterNoonTimeSecond();



            }
        });

        timeTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EveningTime();




            }
        });



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             sendDataSechduleActivity();
            }
        });
    }

    private void datePickerDialog1() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(DateTimeSecaduleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                datePicker1.setText(""+dayOfMonth+"-"+month+"-"+year);

            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void sendDataSechduleActivity() {

        String datpikcer = datePicker1.getText().toString().trim();
        UserMessage = messageUser.getText().toString().trim();

        if (savePickTime.equals(" ") || savePickTime.equals("null") || savePickTime.equals(null)){

            Toast.makeText(DateTimeSecaduleActivity.this,"Select At Least One Time Slot ",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(datpikcer)){
            Toast.makeText(DateTimeSecaduleActivity.this," Please Select Date..",Toast.LENGTH_LONG).show();
            return;
        }






        Intent intent = new Intent(DateTimeSecaduleActivity.this,OrderSummarActivity.class);
        intent.putExtra("time",savePickTime);
        intent.putExtra("date",datpikcer);
        intent.putExtra("message",UserMessage);
        startActivity(intent);



    }

    private void EveningTime() {

        timeTv4.setTextColor(getResources().getColor(R.color.colorPrimary));
        timeTv4.setBackgroundResource(R.drawable.button_bg_stroke_primary);

        timeTv1.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv1.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv2.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv2.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv3.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv3.setBackgroundResource(R.drawable.button_bg_stroke);
        savePickTime = "06:00 PM-08:00 PM";
    }

    private void AfterNoonTimeSecond() {

        timeTv3.setTextColor(getResources().getColor(R.color.colorPrimary));
        timeTv3.setBackgroundResource(R.drawable.button_bg_stroke_primary);

        timeTv1.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv1.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv2.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv2.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv4.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv4.setBackgroundResource(R.drawable.button_bg_stroke);
        savePickTime = "03:00 PM-6:00 PM";

    }

    private void AfterNoonTime() {

        timeTv2.setTextColor(getResources().getColor(R.color.colorPrimary));
        timeTv2.setBackgroundResource(R.drawable.button_bg_stroke_primary);

        timeTv1.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv1.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv3.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv3.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv4.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv4.setBackgroundResource(R.drawable.button_bg_stroke);
        savePickTime = "12:00 Pm-03:00 PM";

    }

    private void MorningTime() {
        timeTv1.setTextColor(getResources().getColor(R.color.colorPrimary));
        timeTv1.setBackgroundResource(R.drawable.button_bg_stroke_primary);

        timeTv2.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv2.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv3.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv3.setBackgroundResource(R.drawable.button_bg_stroke);
        timeTv4.setTextColor(getResources().getColor(R.color.colorgray02));
        timeTv4.setBackgroundResource(R.drawable.button_bg_stroke);
        savePickTime = "09:00 Am-12:00 PM";
    }


}