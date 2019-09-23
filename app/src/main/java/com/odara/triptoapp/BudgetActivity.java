package com.odara.triptoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BudgetActivity extends AppCompatActivity {

    String txt1,txt2;
    TextView tv1,tv2,tv3;
    Button tripObjective,tripDuration,tripTransport,tripBudget,back;
    ImageView imageView;
    ImageButton vmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        tripObjective = findViewById(R.id.btn_tripObj1);
        tripDuration = findViewById(R.id.btn_tripDura1);
        tripTransport = findViewById(R.id.btn_tripTrans1);
        tripBudget = findViewById(R.id.btn_tripBud1);

        back = findViewById(R.id.btn_back1);

        tv1= findViewById(R.id.Tv_budget1);
        tv2= findViewById(R.id.Tv_budget2);
        tv3= findViewById(R.id.Tv_budget3);

        vmore=findViewById(R.id.Btn_viewmore);

        imageView=findViewById(R.id.ImageDisplay);

        String url="https://firebasestorage.googleapis.com/v0/b/triptoapp-8969a.appspot.com/o/images%2F215cf9a0-98d6-4827-8796-e49b1c860a56?alt=media&token=b88886b2-9aaf-4e3f-8143-f11c6e46ebfc";

        Glide.with(getApplicationContext()).load(url).into(imageView);

        vmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });

        tripObjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetActivity.this,ObjectivesActivity.class);
                startActivity(intent);
            }
        });

        tripDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetActivity.this,DurationActivity.class);
                startActivity(intent);
            }
        });

        tripTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetActivity.this,TransportationActivity.class);
                startActivity(intent);
            }
        });

        tripBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetActivity.this,BudgetActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BudgetActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Itinerary").child("itn1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    txt1=dataSnapshot.child("title").getValue().toString();
                    txt2=dataSnapshot.child("budget").getValue().toString();

                    Float value = Float.parseFloat(txt2);

                    if(value<1000 || value==1000){
                        tv1.setText(txt1);
                    }
                    else if(value>1000 && value<5000){
                        tv2.setText(txt1);
                    }
                    else{
                        tv3.setText(txt1);
                    }

                }
                else
                    Toast.makeText(getApplicationContext(),"No source to display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
