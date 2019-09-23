package com.odara.triptoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    EditText txtTitle,txtDestin,txtType,txtDura,txtTrans,txtBudget,txtDesc;
    Button btnSave,btnBack,btnShow,btnUpdate,btnDelete,btnImage;
    DatabaseReference dbRef;
    Itinerary itn;

    private void clearControls(){
        txtTitle.setText("");
        txtDestin.setText("");
        txtType.setText("");
        txtDura.setText("");
        txtTrans.setText("");
        txtBudget.setText("");
        txtDesc.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        txtTitle=findViewById(R.id.EtTitle);
        txtDestin=findViewById(R.id.EtDestin);
        txtType=findViewById(R.id.EtType);
        txtDura=findViewById(R.id.EtDura);
        txtTrans=findViewById(R.id.EtTrans);
        txtBudget=findViewById(R.id.EtBudget);
        txtDesc=findViewById(R.id.EtDesc);

        btnSave = findViewById(R.id.BtnSave);
        btnShow=findViewById(R.id.BtnShow);
        btnUpdate=findViewById(R.id.BtnUpdate);
        btnDelete=findViewById(R.id.BtnDelete);
        btnBack = findViewById(R.id.BtnBack);
        btnImage=findViewById(R.id.BtnUplImg);

        itn=new Itinerary();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this,UploadImageActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef= FirebaseDatabase.getInstance().getReference().child("Itinerary");

                try{
                    if(TextUtils.isEmpty(txtTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a title",Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtDestin.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the destination",Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtType.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the type of travel",Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtDura.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the travel duration",Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtTrans.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the mode of transport",Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtBudget.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the budget",Toast.LENGTH_SHORT).show();


                    else{
                        itn.setTitle(txtTitle.getText().toString().trim());
                        itn.setDestination(txtDestin.getText().toString().trim());
                        itn.setType(txtType.getText().toString().trim());
                        itn.setDuration(Integer.parseInt(txtDura.getText().toString().trim()));
                        itn.setTransport(txtTrans.getText().toString().trim());
                        itn.setDescription(txtDesc.getText().toString().trim());
                        itn.setBudget(Float.parseFloat(txtBudget.getText().toString().trim()));

                        //dbRef.push().setValue(std);
                        dbRef.child("itn1").setValue(itn);

                        /*Map<String,Itinerary> itineraryMap = new HashMap<>();

                        key=txtTitle.getText().toString();

                        itineraryMap.put(key,itn);

                        DatabaseReference itnRef = dbRef.child("itinerary");
                        itnRef.setValue(itineraryMap);*/

                        //String key = dbRef.push().getKey();
                        //dbRef.child(key).setValue("An Itinerary");

                        //String key = txtTitle.getText().toString();
                        //dbRef.push().setValue(itineraryMap);

                        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }


                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Budget value or duration",Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference readRef=FirebaseDatabase.getInstance().getReference().child("Itinerary").child("itn1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            txtTitle.setText(dataSnapshot.child("title").getValue().toString());
                            txtDestin.setText(dataSnapshot.child("destination").getValue().toString());
                            txtType.setText(dataSnapshot.child("type").getValue().toString());
                            txtDura.setText(dataSnapshot.child("duration").getValue().toString());
                            txtTrans.setText(dataSnapshot.child("transport").getValue().toString());
                            txtBudget.setText(dataSnapshot.child("budget").getValue().toString());
                            txtDesc.setText(dataSnapshot.child("description").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to display",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef= FirebaseDatabase.getInstance().getReference().child("Itinerary");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("itn1")){
                            try{
                                itn.setTitle(txtTitle.getText().toString().trim());
                                itn.setDestination(txtDestin.getText().toString().trim());
                                itn.setType(txtType.getText().toString().trim());
                                itn.setDuration(Integer.parseInt(txtDura.getText().toString().trim()));
                                itn.setTransport(txtTrans.getText().toString().trim());
                                itn.setDescription(txtDesc.getText().toString().trim());
                                itn.setBudget(Float.parseFloat(txtBudget.getText().toString().trim()));

                                dbRef=FirebaseDatabase.getInstance().getReference().child("Itinerary").child("itn1");
                                dbRef.setValue(itn);
                                clearControls();

                                Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid budget value or duration",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to update",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef= FirebaseDatabase.getInstance().getReference().child("Itinerary");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("itn1")){
                            dbRef=FirebaseDatabase.getInstance().getReference().child("Itinerary").child("itn1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
