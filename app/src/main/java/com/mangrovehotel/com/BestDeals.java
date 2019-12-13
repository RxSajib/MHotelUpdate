package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BestDeals extends AppCompatActivity {
    private Toolbar toolbar;
    private DatabaseReference Mpostdatabase;
    private RecyclerView recyclerView;
    private String messageget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_deals);


        toolbar = findViewById(R.id.BeastDealsToolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        getSupportActionBar().setTitle("TODAY Activity");
        Mpostdatabase = FirebaseDatabase.getInstance().getReference().child("Post");


        recyclerView = findViewById(R.id.PostRecylearViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }



    @Override
    protected void onStart() {

        FirebaseRecyclerAdapter<PostHolder, PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostHolder, PostViewHolder>(
                PostHolder.class,
                R.layout.post_banner,
                PostViewHolder.class,
                Mpostdatabase
        ) {

            protected void populateViewHolder(final PostViewHolder postViewholder, final PostHolder postHolder, int i) {

                String UID = getRef(i).getKey();
                Mpostdatabase.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {


                            if (dataSnapshot.hasChild("current_date")) {
                                String current_dateget = dataSnapshot.child("current_date").getValue().toString();
                                postViewholder.setdateSet(current_dateget);
                            }
                            if (dataSnapshot.hasChild("current_time")) {
                                String current_timeget = dataSnapshot.child("current_time").getValue().toString();
                                postViewholder.setTmSet(current_timeget);
                            }
                            if (dataSnapshot.hasChild("image")) {
                                String imageget = dataSnapshot.child("image").getValue().toString();
                                postViewholder.setImage(imageget);
                            }
                            if (dataSnapshot.hasChild("message")) {
                                messageget = dataSnapshot.child("message").getValue().toString();
                                postViewholder.seturlset(messageget);
                            }

                            postViewholder.Mview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(messageget));
                                        startActivity(myIntent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(BestDeals.this, "No application can handle this request." + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    static public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView time, date;
        private View Mview;
        private TextView url;
        private ImageView postimage;
        private Context context;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            time = Mview.findViewById(R.id.PostBannerTimeID);
            date = Mview.findViewById(R.id.PostBannerDatIeD);
            url = Mview.findViewById(R.id.PostUrlID);
            postimage = Mview.findViewById(R.id.PostBannerImageID);
            context = Mview.getContext();
        }

        public void setTmSet(String tim) {
            time.setText(tim);
        }

        public void setdateSet(String dat) {
            date.setText(dat);
        }

        public void seturlset(String ur) {
            url.setText(ur);
        }

        public void setImage(String img) {
            Picasso.with(context).load(img).placeholder(R.drawable.default_galleryimage).into(postimage);
        }
    }
}
