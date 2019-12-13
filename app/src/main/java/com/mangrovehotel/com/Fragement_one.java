package com.mangrovehotel.com;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragement_one extends Fragment {

    private TextView one, two;
    private Animation annotation;
    private RatingBar ratingBarcheacking_process;
    private String CheackingProcessString;
    private DatabaseReference MRatingDatabase;
    private String CurrentUserID;
    private FirebaseAuth Mauth;
    private RelativeLayout relativeLayout;

    public Fragement_one() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragement_one, container, false);


        one = view.findViewById(R.id.onee);
        two = view.findViewById(R.id.twotext);

        annotation = AnimationUtils.loadAnimation(getContext(), R.anim.frombutton);

        one.startAnimation(annotation);
        two.startAnimation(annotation);

        ratingBarcheacking_process = view.findViewById(R.id.CheckRatingId);
        MRatingDatabase = FirebaseDatabase.getInstance().getReference().child("Rating");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        relativeLayout = view.findViewById(R.id.LoneIDID);
        relativeLayout.setAnimation(annotation);


        ratingBarcheacking_process.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                CheackingProcessString = String.valueOf(rating);

                Map ratingmap = new HashMap();
                ratingmap.put("CheackingprocessRating", CheackingProcessString);


                MRatingDatabase.child(CurrentUserID)
                        .updateChildren(ratingmap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getActivity(), "save", Toast.LENGTH_LONG).show();
                                } else {
                                    String errormessage = task.getException().getMessage().toString();
                                    Toast.makeText(getActivity(), errormessage, Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String errormessage = e.getMessage().toString();
                        Toast.makeText(getActivity(), errormessage, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        return view;
    }

}
