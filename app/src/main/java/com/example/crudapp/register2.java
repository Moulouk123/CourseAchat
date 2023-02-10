package com.example.crudapp;

import static android.Manifest.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class register2 extends AppCompatActivity {
    private ProgressBar loading;
    TextInputEditText fullname, tel, email, image, pass;
    Button btn;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    RadioButton etud,prof;
    TextView txt;
    private List<Address>addresses;
    String latitude,longitude,address,city,country;
private static  int  REQuest_CODE=5;
String role;
private User user;
   private  FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        fullname = findViewById(R.id.editfull);
        tel = findViewById(R.id.edittelephone);
        email = findViewById(R.id.editemail);
        image = findViewById(R.id.editlink);

        pass = findViewById(R.id.editpassword);
        loading = findViewById(R.id.idloading);
        etud=findViewById(R.id.etud);
        prof=findViewById(R.id.prof);
        txt=findViewById(R.id.txt);
        btn = findViewById(R.id.btnr);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(register2.this,LoginActivity.class);
                startActivity(i);
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString();
                String password = pass.getText().toString();


              /*  if (ContextCompat.checkSelfPermission(register2.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                    fusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {

                                    if (location != null) {

                                        Geocoder geocoder = new Geocoder(register2.this, Locale.getDefault());
                                        try {
                                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            System.out.println("////////////////////////////////////////////////////////////////////////////////");
                                            System.out.println(" adresssessssssssss" + addresses.get(0).getLocality() + " " + addresses.get(0).getCountryName());
                                            MonAdresse = addresses.get(0).getAddressLine(0);
                                            map.setText(MonAdresse);
                                            Toast.makeText(register2.this, "this " + MonAdresse, Toast.LENGTH_SHORT).show();



                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            });
                }*/
                if (mail.isEmpty() && password.isEmpty()) {
                    Toast.makeText(register2.this, "Enter please your mail and password", Toast.LENGTH_SHORT).show();

                }
                if (etud.isChecked()) {
                    role = "Etudiant";
                    mDatabase = database.getReference("Etudiant");


                } else if (prof.isChecked()) {
                    role = "Professeur";
                    mDatabase = database.getReference("Professeur");

                } else {
                    Toast.makeText(register2.this, "check your profession", Toast.LENGTH_SHORT).show();

                }


                user = new User(tel.getText().toString(), image.getText().toString(), email.getText().toString(), fullname.getText().toString(), email.getText().toString(), role);
                registerUser(email.getText().toString(), pass.getText().toString());


            }});
    }


    public void registerUser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user=auth.getCurrentUser();
                    updateUI(user);


                } else {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(register2.this, "FAIL TO register", Toast.LENGTH_SHORT).show();
                    Toast.makeText(register2.this, "user registered", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
private void getLocation()
{
    if(ContextCompat.checkSelfPermission(register2.this, permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
    {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {
                            Geocoder geocoder=new Geocoder(register2.this,Locale.getDefault());
                            addresses= null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                latitude= String.valueOf(addresses.get(0).getLatitude());
                                longitude= String.valueOf(addresses.get(0).getLongitude());
                                address=addresses.get(0).getAddressLine(0);
                                city=addresses.get(0).getLocality();
                                country=addresses.get(0).getCountryName();



                            } catch (IOException e) {
                                e.printStackTrace();
                            }





                        }
                    }
                });


    }
    else
    {
        askPermission();

    }
}
private void askPermission()
{

    ActivityCompat.requestPermissions(register2.this,new String[]
    {permission.ACCESS_BACKGROUND_LOCATION},REQuest_CODE);


}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQuest_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED )
            {
                getLocation();
            }
            else
            {
                Toast.makeText(register2.this,"Requireed Parmission",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void updateUI(FirebaseUser currentuser) {
        String keyid=mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user);
        loading.setVisibility(View.GONE);

        Toast.makeText(register2.this, "user registered", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(register2.this, LoginActivity.class);
        startActivity(i);

        finish();
    }


        }