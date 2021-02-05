package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import home.services.timeBacaho.R;

public class SettingsActivity extends AppCompatActivity {
    ImageButton backBtn;
    SwitchCompat fcmSwitch;
    TextView notificationSatusTv;

    //FirebaseAuth firebaseAuth ;
    Boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}