package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private FloatingActionButton update;
    private EditText emailaddress;
    private FirebaseAuth Mauth;
    private ProgressDialog mprogress;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        toolbar = findViewById(R.id.ResetPasswordID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        emailaddress = findViewById(R.id.EmailAddressID);
        update = findViewById(R.id.UpdateButtonID);
        Mauth = FirebaseAuth.getInstance();
        mprogress = new ProgressDialog(ResetPasswordActivity.this);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailaddrestext = emailaddress.getText().toString();

                if(emailaddrestext.isEmpty()){
                    Toast.makeText(getApplicationContext(), "input your email", Toast.LENGTH_LONG).show();
                }
                else {
                    //mprogress.setTitle("Please wait");
                    mprogress.setMessage("Please wait");
                    mprogress.setCanceledOnTouchOutside(false);
                    mprogress.show();
                    Mauth.sendPasswordResetEmail(emailaddrestext)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mprogress.dismiss();
                                        Toast.makeText(getApplicationContext(), "check your email address for reset password", Toast.LENGTH_LONG).show();
                                        emailaddress.setText("");
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else {
                                        mprogress.dismiss();
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
