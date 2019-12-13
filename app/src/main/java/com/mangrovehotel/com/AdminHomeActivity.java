package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;
    private Button button;
    private Uri imageuri = null;
    private EditText inputtext;
    private String DownloadUrl;
    private DatabaseReference MpostDatabase;
    private ProgressDialog Mprogress;
    private Uri uri = null;
    private StorageReference Mstores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        Mstores = FirebaseStorage.getInstance().getReference();
        Mprogress = new ProgressDialog(AdminHomeActivity.this);
        MpostDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        toolbar = findViewById(R.id.AdminHomeToolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        inputtext = findViewById(R.id.DesEdittextID);



        imageView = findViewById(R.id.ImageID);
        button = findViewById(R.id.SubmitButtonDID);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startposting_image();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String des = inputtext.getText().toString();

                if (des.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Link found", Toast.LENGTH_LONG).show();
                } else {

                    Mprogress.setTitle("Please wait ...");
                    Mprogress.setMessage("wait for a moment uploading your post");
                    Mprogress.setCanceledOnTouchOutside(false);
                    Mprogress.show();

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormattime = new SimpleDateFormat("dd-MM-yyyy");
                    String CurrentDate = simpleDateFormattime.format(calendar.getTime());

                    Calendar calendartime = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormatdate = new SimpleDateFormat("HH:mm:ss");
                    String CurrentTime = simpleDateFormatdate.format(calendartime.getTime());


                    Map postmap = new HashMap();
                    postmap.put("image", DownloadUrl);
                    postmap.put("current_time", CurrentTime);
                    postmap.put("current_date", CurrentDate);
                    postmap.put("message", des);


                    MpostDatabase.push().updateChildren(postmap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful()) {

                                        Mprogress.dismiss();
                                        Toast.makeText(getApplicationContext(), "upload success", Toast.LENGTH_LONG).show();
                                    } else {
                                        String error = task.getException().getMessage().toString();
                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                        Mprogress.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Mprogress.dismiss();
                            String errormesssage = e.getMessage().toString();
                            Toast.makeText(getApplicationContext(), errormesssage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startposting_image() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);

                StorageReference filepath = Mstores.child("Image").child(resultUri.getLastPathSegment());


                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "image post success", Toast.LENGTH_LONG).show();
                            DownloadUrl = task.getResult().getDownloadUrl().toString();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
