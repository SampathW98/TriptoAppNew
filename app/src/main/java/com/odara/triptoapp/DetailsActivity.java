package com.odara.triptoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  DetailsActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ToggleButton btnFavourite;
    DatabaseReference dbRef;
    Itinerary fav;
    String txt1,txt2,txt3,txt4,txt5,txt6,txt7;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv1=findViewById(R.id.Tv1);
        tv2=findViewById(R.id.Tv2);
        tv3=findViewById(R.id.Tv3);
        tv4=findViewById(R.id.Tv4);
        tv5=findViewById(R.id.Tv5);
        tv6=findViewById(R.id.Tv6);
        tv7=findViewById(R.id.Tv7);

        imageView=findViewById(R.id.Image);

        btnFavourite=(ToggleButton) findViewById(R.id.BtnFavourite);

        fav=new Itinerary();

        Button back = findViewById(R.id.BtnSave);

        String url="https://firebasestorage.googleapis.com/v0/b/triptoapp-8969a.appspot.com/o/images%2F215cf9a0-98d6-4827-8796-e49b1c860a56?alt=media&token=b88886b2-9aaf-4e3f-8143-f11c6e46ebfc";

        Glide.with(getApplicationContext()).load(url).into(imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,ObjectivesActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Itinerary").child("itn1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    txt1=dataSnapshot.child("title").getValue().toString();
                    txt2=dataSnapshot.child("destination").getValue().toString();
                    txt3=dataSnapshot.child("type").getValue().toString();
                    txt4=dataSnapshot.child("duration").getValue().toString();
                    txt5=dataSnapshot.child("transport").getValue().toString();
                    txt6=dataSnapshot.child("budget").getValue().toString();
                    txt7=dataSnapshot.child("description").getValue().toString();

                    tv1.setText(txt1);
                    tv2.setText(txt2);
                    tv3.setText(txt3);
                    tv4.setText(txt4);
                    tv5.setText(txt5);
                    tv6.setText(txt6);
                    tv7.setText(txt7);
                }
                else
                    Toast.makeText(getApplicationContext(),"No source to display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    dbRef= FirebaseDatabase.getInstance().getReference().child("Favourites");

                    fav.setTitle(txt1.trim());
                    fav.setDestination(txt2.trim());
                    fav.setType(txt3.trim());
                    fav.setDuration(Integer.parseInt(txt4.trim()));
                    fav.setTransport(txt5.trim());
                    fav.setBudget(Float.parseFloat(txt6.trim()));
                    fav.setDescription(txt7.trim());

                    dbRef.child("fav1").setValue(fav);

                    Toast.makeText(getApplicationContext(),"Added to Favourites",Toast.LENGTH_SHORT).show();


                }
                else {
                    DatabaseReference delRef= FirebaseDatabase.getInstance().getReference().child("Favourites");
                    delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("fav1")){
                                dbRef=FirebaseDatabase.getInstance().getReference().child("Favourites").child("fav1");
                                dbRef.removeValue();
                                Toast.makeText(getApplicationContext(),"Removed from Favourites",Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(),"No source to delete",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}
