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
public class Fragement_two extends Fragment {

    private RelativeLayout one, two, three, four, five;
    private Animation annotation;

    private RatingBar cheackingprocessRating, roomservicesRating, bed_and_linesRating, BreakfastServicesRating, OverallRating;
    private DatabaseReference MratingData;
    private String CurrentUserID;
    private FirebaseAuth Mauth;

    public Fragement_two() {
        // Required empty public constructor
    }

    private String cheackingprocessRatingText, RoomservicesRatingText, BedandLinesRatingtext, BreakfastRatingtext, OverallRatingText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragement_two, container, false);



        MratingData = FirebaseDatabase.getInstance().getReference().child("Rating");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        one = view.findViewById(R.id.lone);
        two = view.findViewById(R.id.layoutthree);
        three = view.findViewById(R.id.layoutfour);
        four = view.findViewById(R.id.layoutfive);
        five = view.findViewById(R.id.layoutsiz);
        annotation = AnimationUtils.loadAnimation(getContext(), R.anim.frombutton);

        one.setAnimation(annotation);
        two.setAnimation(annotation);
        three.setAnimation(annotation);
        four.setAnimation(annotation);
        five.setAnimation(annotation);


        cheackingprocessRating = view.findViewById(R.id.CheackInprocssRatingId);
        roomservicesRating = view.findViewById(R.id.RoomServicesRatingID);
        bed_and_linesRating = view.findViewById(R.id.BedandLinesRatingID);
        BreakfastServicesRating = view.findViewById(R.id.BreakfastRatingID);
        OverallRating = view.findViewById(R.id.OverAllratingID);


        cheackingprocessRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                cheackingprocessRatingText = String.valueOf(rating);


                final Map ratingmap = new HashMap();
                ratingmap.put("CheackingprocessRating", cheackingprocessRatingText);


                MratingData.child(CurrentUserID).updateChildren(ratingmap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
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


        roomservicesRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                RoomservicesRatingText = String.valueOf(rating);

                final Map ratingmap = new HashMap();
                ratingmap.put("RoomservicesRating", RoomservicesRatingText);

                MratingData.child(CurrentUserID).updateChildren(ratingmap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
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

        bed_and_linesRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                BedandLinesRatingtext = String.valueOf(rating);

                final Map ratingmap = new HashMap();
                ratingmap.put("BedandLinesRating", BedandLinesRatingtext);

                MratingData.child(CurrentUserID).updateChildren(ratingmap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
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

        BreakfastServicesRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                BreakfastRatingtext = String.valueOf(rating);

                final Map ratingmap = new HashMap();
                ratingmap.put("BreakfastRating", BreakfastRatingtext);

                MratingData.child(CurrentUserID).updateChildren(ratingmap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
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

        OverallRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                OverallRatingText = String.valueOf(rating);

                final Map ratingmap = new HashMap();
                ratingmap.put("OverallRating", OverallRatingText);


                MratingData.child(CurrentUserID).updateChildren(ratingmap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
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
