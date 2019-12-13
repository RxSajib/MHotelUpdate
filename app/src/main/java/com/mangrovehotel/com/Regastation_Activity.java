package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class Regastation_Activity extends AppCompatActivity {


    private Button haveaaccount;
    private FirebaseAuth Mauth;

    private EditText email, password, cpassword;
    private Button regbutton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regastation_);

        progressDialog = new ProgressDialog(Regastation_Activity.this);
        email = findViewById(R.id.REmailID);
        password = findViewById(R.id.RPasswordID);
        cpassword = findViewById(R.id.RCPasswordID);
        regbutton = findViewById(R.id.RLoginButtonID);




        Mauth = FirebaseAuth.getInstance();
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        haveaaccount = findViewById(R.id.RRegisterButtonID);
        haveaaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();
                String cpasswordtext = cpassword.getText().toString();

                if(emailtext.isEmpty()){
                    email.setError("Email require");
                }
                else if(passwordtext.isEmpty()){
                    password.setError("Password require");
                }
                else if(cpasswordtext.isEmpty()){
                    cpassword.setError("Password require");
                }
                else if(!cpasswordtext.equals(passwordtext)){
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.setTitle("Please wait ...");
                    progressDialog.setMessage("we are creating your account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    Mauth.createUserWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }


    @Override
    protected void onStart() {
        FirebaseUser muser = Mauth.getCurrentUser();
        if(muser != null){
            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        super.onStart();
    }
}
