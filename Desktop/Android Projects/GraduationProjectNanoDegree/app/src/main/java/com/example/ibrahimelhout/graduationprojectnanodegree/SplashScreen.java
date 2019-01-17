package com.example.ibrahimelhout.graduationprojectnanodegree;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity {

    private static final long SLEEP_TIME = 3000;
//    @BindView(R.id.logo)
//    ImageView logo;
//    @BindView(R.id.progressSplash)
//    ProgressBar progressSplash;


    Intent intent;
    @BindView(R.id.logoIV)
    ImageView logoIV;
    @BindView(R.id.sloganTV)
    TextView sloganTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splashalpha);
        animation.setDuration(1000);
        logoIV.setAnimation(animation);
        sloganTV.setAnimation(animation);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the





        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {


//                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
//                    startActivity(intent);


                    if (FirebaseAuth.getInstance().getCurrentUser()==null){

                        Intent intent = new Intent(SplashScreen.this,LoginAcitivity.class);
                        startActivity(intent);
//                        Log.d("test", "run: "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        finish();
                    }else {

                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
//                        Log.d("test", "run: "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                        finish();
                    }


                }

            }
        };

        thread.start();


    }
}
