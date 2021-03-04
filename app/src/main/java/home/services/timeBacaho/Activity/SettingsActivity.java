package home.services.timeBacaho.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.R;

public class SettingsActivity extends AppCompatActivity {
    ImageButton backBtn;
    SwitchCompat fcmSwitch;
    TextView notificationSatusTv;

    FirebaseAuth firebaseAuth ;
    Boolean isChecked = false;
    public  static  final  String enabledMessage = " Notification is enabled";
    public  static  final  String disabledMessage = " Notification is disable";
    SharedPreferences sp ;
    SharedPreferences.Editor esp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        firebaseAuth = FirebaseAuth.getInstance();
        fcmSwitch = findViewById(R.id.fcmSwitch);
        notificationSatusTv = findViewById(R.id. notificationSatusTv);



        sp = getSharedPreferences("SETTINGS_SP",MODE_PRIVATE);

        //check last selected options true/False;

        isChecked = sp.getBoolean("FCM_ENABLED",false);
        fcmSwitch.setChecked(isChecked);
        if (isChecked){


            //was enabled

            notificationSatusTv.setText(enabledMessage);

        }else {

            // was disabled
            notificationSatusTv.setText(disabledMessage);

        }



        fcmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    suscribeToTopic();
                }else{
                    unsuscribeTopic();
                }
            }
        });

    }

    private  void  suscribeToTopic(){

        FirebaseMessaging.getInstance().subscribeToTopic(AppConfig.FCM_TOPIC).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                esp = sp.edit();

                esp.putBoolean("FCM_ENABLED",true);
                esp.apply();
                Toast.makeText(SettingsActivity.this,""+enabledMessage,Toast.LENGTH_LONG).show();
                notificationSatusTv.setText(enabledMessage);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SettingsActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
    private  void unsuscribeTopic(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(AppConfig.FCM_TOPIC).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

//save setting in share perfernce



                esp = sp.edit();

                esp.putBoolean("FCM_ENABLED",false);
                esp.apply();
                Toast.makeText(SettingsActivity.this,""+disabledMessage,Toast.LENGTH_LONG).show();
                notificationSatusTv.setText(disabledMessage);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SettingsActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
    }
