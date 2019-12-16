package com.mangrovehotel.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.annotation.Annotation;

public class Landing_Activity extends AppCompatActivity {

    private Button joinbutton;
    private Button siginbutton;
    private Animation animatable;
    private TextView hotelweb;
    private FirebaseAuth Mauth;
    private RelativeLayout layoutone, layouttwo;

    private TextView offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_);


        offer = findViewById(R.id.PostButtonID);

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestDeals.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        layoutone = findViewById(R.id.KKK);
        layouttwo = findViewById(R.id.Twos);


        Mauth = FirebaseAuth.getInstance();
        hotelweb = findViewById(R.id.HotelWebID);
        hotelweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookNowLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        joinbutton = findViewById(R.id.JoinButtonID);
        siginbutton = findViewById(R.id.SiginButtonID);


        animatable = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.frombutton);
        joinbutton.setAnimation(animatable);
        siginbutton.setAnimation(animatable);

        layouttwo.setAnimation(animatable);
        layoutone.setAnimation(animatable);

        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Regastation_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        siginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        FirebaseUser muser = Mauth.getCurrentUser();
        if (muser != null) {
            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }


        super.onStart();
    }

}
