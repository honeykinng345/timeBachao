package home.services.timeBacaho.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SessionManager;

public class activity_splash_scr extends AppCompatActivity {
    private ImageView logoSplash;
    private TextView companyName;

    private Animation anim1, anim2, anim3;
    static final String CHANNEL_ID = "technopoints_id";
    static final String CHANNEL_NAME = "technopoints name";
    static final String CHANNEL_DESC = "technopoints desc";
private  DatabaseHelper db1;
private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_scr);

        db1 = new DatabaseHelper(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        logoSplash.setAnimation(anim1);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                logoSplash.setAnimation(anim2);


                companyName.setAnimation(anim3);
                companyName.setVisibility(View.VISIBLE);
                anim3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {


                        Cursor res = db1.getAllData();
                        if (sessionManager.isLoggedIn()) {
                            while (res.moveToNext()) {
                                String account1 = res.getString(5);


                                if (account1.equals("Seller")) {
                                    Intent intent = new Intent(activity_splash_scr.this,
                                            ServiceProviderMain.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(activity_splash_scr.this,""+account1,Toast.LENGTH_LONG).show();
                                } else if ( account1.equals("User")){


                                    Intent intent = new Intent(activity_splash_scr.this,
                                            MainUserActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(activity_splash_scr.this,""+account1,Toast.LENGTH_LONG).show();
                                }




                            }
                        }

                        Intent intent = new Intent(activity_splash_scr.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();





                    }


                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });












    }

    private void init() {

        logoSplash = findViewById(R.id.ivLogoSplash);
        companyName = findViewById(R.id.compnayName);


        anim1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout);
        anim3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadein);




    }


}

