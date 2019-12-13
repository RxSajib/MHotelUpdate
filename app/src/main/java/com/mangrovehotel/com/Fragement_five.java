package com.mangrovehotel.com;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mangrovehotel.com.API_Gmail.Post;
import com.mangrovehotel.com.API_Gmail.Postt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragement_five extends Fragment {



    private EditText email, comments;
    private Button submitbutton;
    private DatabaseReference MFeedbackDatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private String BedandLinesRatingText, BreakfastRatingText, CheackingprocessRatingText;
    private String Hotel_StaffText, OverallRatingText, RoomservicesRatingText, clean_roomText;
    private String commentsText, emailtext, commentstext;

    private String MESSAGE;
    private ProgressDialog Mprogress;

    public Fragement_five() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragement_five, container, false);



        Mprogress = new ProgressDialog(getContext());
        MFeedbackDatabase = FirebaseDatabase.getInstance().getReference().child("Rating");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        email = view.findViewById(R.id.EmailAddressInputID);
        comments = view.findViewById(R.id.MessageID);
        submitbutton = view.findViewById(R.id.SubmitButtonID);


        MFeedbackDatabase.child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("BedandLinesRating")){
                                BedandLinesRatingText = dataSnapshot.child("BedandLinesRating").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("BreakfastRating")){
                                BreakfastRatingText = dataSnapshot.child("BreakfastRating").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("CheackingprocessRating")){
                                CheackingprocessRatingText = dataSnapshot.child("CheackingprocessRating").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("Hotel_Staff")){
                                Hotel_StaffText = dataSnapshot.child("Hotel_Staff").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("OverallRating")){
                                OverallRatingText = dataSnapshot.child("OverallRating").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("RoomservicesRating")){
                                RoomservicesRatingText = dataSnapshot.child("RoomservicesRating").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("clean_room")){
                                clean_roomText = dataSnapshot.child("clean_room").getValue().toString();
                            }
                            if(dataSnapshot.hasChild("comments")){
                                commentsText = dataSnapshot.child("comments").getValue().toString();
                            }

                        }
                        else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailtext = email.getText().toString().trim();

                commentstext = comments.getText().toString();

                if(emailtext.isEmpty()){
                    email.setError("Email require");
                }
                else if(commentstext.isEmpty()){
                    comments.setError("Answer require");
                }


                else {

                    sendingemail();
                }
            }
        });


        return view;
    }


    private void sendingemail(){

        MESSAGE = "Email :"+emailtext+"\n"+"COMMENTS :"+commentstext+"\n"+"CHECKING PROCESS :"+CheackingprocessRatingText+"\n"+"ROOM SERVICES :"+RoomservicesRatingText+"\n"+"BED AND LINES :"+BedandLinesRatingText+"\n"+"BREAKFAST AND SERVICES :"+BreakfastRatingText+"\n"+"OVERALL SERVICES :"+OverallRatingText+"\n"+"WAS ROOM :"+clean_roomText+"\n"+"HOTEL STAFF :"+Hotel_StaffText+"\n";

        Mprogress.setTitle("Please wait");
        Mprogress.setMessage("Sending ...");
        Mprogress.show();
        Mprogress.setCanceledOnTouchOutside(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hsmailapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /////send email

        Post sendMailAPIClint = retrofit.create(Post.class);


        ///imikhramkhan@gmail.com
        Postt postt = new Postt("mangrovehotel4@gmail.com", "Mangrovehotel2019", "imikhramkhan@gmail.com", "FeedBack From Mangrove", MESSAGE);

        Call<Post> call = sendMailAPIClint.createPost(postt);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Mprogress.dismiss();

                }
                else {
                    Mprogress.dismiss();
                    email.setText("");
                    comments.setText("");

                    MFeedbackDatabase.removeValue();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Mprogress.dismiss();
                email.setText("");
                comments.setText("");
                MFeedbackDatabase.removeValue();

            }
        });

    }
}
