package com.example.ibrahimelhout.graduationprojectnanodegree.Utils;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ibrahimelhout.graduationprojectnanodegree.LoginAcitivity;
import com.example.ibrahimelhout.graduationprojectnanodegree.R;
import com.google.firebase.auth.FirebaseAuth;

public class MotherActivity extends AppCompatActivity {


    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFirebaseAuth.getCurrentUser()==null){
//            singOutAndUpdateUI();
        }

    }



    public void singOutAndUpdateUI(){

        FirebaseAuth.getInstance().signOut();
        Intent startIntent = new Intent();
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.setClass(this,LoginAcitivity.class);
        startActivity(startIntent);
        finish();


    }
}
