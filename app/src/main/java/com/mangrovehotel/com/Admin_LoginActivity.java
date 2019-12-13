package com.mangrovehotel.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button button;
    private DatabaseReference MAdminDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        MAdminDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        email = findViewById(R.id.AdminEmailAddressID);
        password = findViewById(R.id.AdminPasswordAddressID);

        button = findViewById(R.id.LoginButtonID);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setBackgroundResource(R.drawable.upbutton_design);

                    startReadingData();
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackgroundResource(R.drawable.down_button);
                }

                return false;
            }
        });
    }

    private void startReadingData() {

        final String emailget = email.getText().toString();
        final String password_get = password.getText().toString();

        if (emailget.isEmpty()) {
            email.setError("Email require");
        } else if (password_get.isEmpty()) {
            password.setError("Password require");
        } else {


            MAdminDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        String emailString = dataSnapshot.child("email").getValue().toString();
                        String passwordString = dataSnapshot.child("password").getValue().toString();

                        if (emailget.equals(emailString) || password_get.equals(passwordString)) {

                            Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "your didn't have any email", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
}
