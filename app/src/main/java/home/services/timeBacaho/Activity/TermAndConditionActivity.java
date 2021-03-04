package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import home.services.timeBacaho.R;

public class TermAndConditionActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);
        textView = findViewById(R.id.textView);

        fetchTermANdCOdtions();
    }

    private void fetchTermANdCOdtions() {

        String string = "";
        try {
            InputStream inputStream = getAssets().open("myText.txt");

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);

            Toast.makeText(this,"test",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText(string);

    }
}