package com.example.crudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profil extends AppCompatActivity {
    TextView tel,nom,email,prof;
    ImageView image;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    FirebaseAuth auth;
    String emaill,role;
    Button btn;
    FirebaseUser authh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);
        Intent i=getIntent();
         authh = FirebaseAuth.getInstance().getCurrentUser();

         emaill=authh.getEmail();
         role=i.getStringExtra("prof");

        tel=findViewById(R.id.phone);
        nom=findViewById(R.id.nom);
        email=findViewById(R.id.email);
        prof=findViewById(R.id.prof);
        image=findViewById(R.id.image);
        btn=findViewById(R.id.btnhome);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(profil.this,local.class);

                startActivity(i);
                Toast.makeText(profil.this," hello local",Toast.LENGTH_SHORT).show();


            }
        });
        database=FirebaseDatabase.getInstance();

      mDatabase=database.getReference().child(role);

     /*   mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       /* { if (dataSnapshot.exists())
        { HashMap dataMap = (HashMap ) dataSnapshot.getValue();
            for (String key : dataMap.keySet())
            { Object data = dataMap.get(key);
                { HashMap userData = (HashMap )
                        User mUser = new User((String) userData.get("name"), (int) (long) userData.get("age")); addTextToView(mUser.getName() + " - " + Integer.toString(mUser.getAge())); }catch (ClassCastException cce){ // Si l'objet ne peut pas être converti en HashMap, cela signifie qu'il est de type String. try{ String mString = String.valueOf(dataMap.get(key)); addTextToView(mString); }catch (ClassCastException cce2){ } } } } } @Override public void onCancelled(@NonNull DatabaseError databaseError) { } });
      mDatabase.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {


              /*for(DataSnapshot ds: snapshot.getc)
              {
                  if(ds.child("email").getValue().equals(emaill))
                  {
                      nom.setText(ds.child("fullname").getValue(String.class));
                      email.setText(ds.child("email").getValue(String.class));

                      tel.setText(ds.child("telephone").getValue(String.class));
                      prof.setText(ds.child("role").getValue(String.class));
                      localisation.setText(ds.child("gelocalisation").getValue(String.class));
                      Picasso.get().load(ds.child("image").getValue(String.class)).into(image);
                      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                     Toast.makeText(profil.this,nom.getText().toString().toString()+prof.getText().toString()+localisation.getText().toString()+email.getText(),Toast.LENGTH_SHORT).show();
                  }

              }


          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(profil.this,"profil not fonct",Toast.LENGTH_SHORT).show();

          }
      });*/
        Toast.makeText(profil.this, emaill , Toast.LENGTH_SHORT).show();

        mDatabase.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()) {

            Toast.makeText(profil.this, "bbbbbb1" , Toast.LENGTH_SHORT).show();

            for (DataSnapshot keyId : snapshot.getChildren()) {
                Toast.makeText(profil.this, "bbbbbb2" , Toast.LENGTH_SHORT).show();


                if (keyId.child("iduser").getValue().equals(emaill)) {
                    Toast.makeText(profil.this, "bbbbbb3" , Toast.LENGTH_SHORT).show();

                    nom.setText(keyId.child("fullname").getValue(String.class));
                    tel.setText(keyId.child("telephone").getValue(String.class));
                    prof.setText(keyId.child("role").getValue(String.class));
                    email.setText(keyId.child("email").getValue(String.class));
                    Picasso.get().load(keyId.child("image").getValue(String.class)).into(image);
                    Toast.makeText(profil.this, keyId.child("image").getValue(String.class) , Toast.LENGTH_SHORT).show();





                    Toast.makeText(profil.this, "bbbbbb" + nom.getText().toString() + tel.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            Toast.makeText(profil.this, "bbbbbb" , Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});






    }
}