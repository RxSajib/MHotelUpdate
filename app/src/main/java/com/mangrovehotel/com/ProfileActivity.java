package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText username, phonenumber;
    private Button button;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private DatabaseReference Muserdatabase;
    private ProgressDialog Mprogress;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        Muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Mprogress = new ProgressDialog(ProfileActivity.this);


        username = findViewById(R.id.UsernameEdittextID);
        phonenumber = findViewById(R.id.PhoneNumberEdittextID);
        button = findViewById(R.id.SetupButtonID);
        imageView = findViewById(R.id.imagee);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernametxet = username.getText().toString().trim().toUpperCase();
                String phonenumbertext = phonenumber.getText().toString().trim();

                if (usernametxet.isEmpty()) {
                    username.setError("Username Require");
                } else if (phonenumbertext.isEmpty()) {
                    phonenumber.setError("PhoneNumber Require");
                } else {

                    Mprogress.setTitle("Please wait");
                    Mprogress.setMessage("We are creating your profile");
                    Mprogress.setCanceledOnTouchOutside(false);
                    Mprogress.show();
                    Map usermap = new HashMap();
                    usermap.put("username", usernametxet);
                    usermap.put("phonenumber", phonenumbertext);


                    Muserdatabase.child(CurrentUserID).updateChildren(usermap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful()) {
                                        Mprogress.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "profile setup success", Toast.LENGTH_LONG).show();
                                    } else {
                                        Mprogress.dismiss();
                                        String errormessage = task.getException().getMessage().toString();
                                        Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Mprogress.dismiss();
                            String error = e.getMessage();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
