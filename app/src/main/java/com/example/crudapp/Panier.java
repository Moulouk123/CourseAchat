package com.example.crudapp;

import static android.view.LayoutInflater.from;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Panier extends AppCompatActivity implements CourseAdapter.CourseClickInterface {
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private FirebaseDatabase data;

    private DatabaseReference reference,ref;
    private ArrayList<Course> list;
private FusedLocationProviderClient fusedLocationProviderClient;
    private RelativeLayout viexx;
    private CourseAdapter adapter;
    private FirebaseUser auth;
    private Button check;

    private  final  static int REQUEST_CODE=10;
private int somme=0;
    List<Address> addresses;
    String country,city,address;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        recyclerView=findViewById(R.id.idrecycler);
        loading=findViewById(R.id.idloading);
        viexx =findViewById(R.id.sheet);
        data= FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance().getCurrentUser();
        check=findViewById(R.id.check);

        Toast.makeText(Panier.this, "panier", Toast.LENGTH_SHORT).show();
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        reference= data.getReference("panier").child(auth.getUid());
        list=new ArrayList<>();
        adapter=new CourseAdapter(list,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data= FirebaseDatabase.getInstance();

        FirebaseUser authh;
        authh=FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setAdapter(adapter);
        getAllCourses();
        getLastLocation();



        check.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ref= data.getReference("checkout").child(authh.getUid());




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(int i=0;i<list.size();i++)
                {
                    Course c=list.get(i);
                    somme = Integer.parseInt(c.getCourse_price());
                }
                ref.child("Address").child("City").setValue(city);
                ref.child("Address").child("Country").setValue(country);
                ref.child("Address").child("address").setValue(address);
                ref.child("Money").setValue(somme);
                ref.child("email").setValue(auth.getEmail());
                ref.child("Liste dachat").setValue(list);
                ref.child("nb dachat").setValue(list.size());




                Toast.makeText(Panier.this, "Course Added to checkout", Toast.LENGTH_SHORT).show();
                Intent i = new Intent (Panier.this,MainActivity.class);
                startActivity(i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Panier.this, "Course Failed to add to checkout"+error.toString(), Toast.LENGTH_SHORT).show();


            }
        });
    }
});


    }

   /* private void check( )
    {
        getLastLocation();
        data= FirebaseDatabase.getInstance();
        FirebaseUser authh;
        authh=FirebaseAuth.getInstance().getCurrentUser();



        reference= data.getReference("checkout").child(authh.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.child("ADDRESS").setValue(listadr);



                Toast.makeText(Panier.this, "Course Added to checkout", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Panier.this, "Course Failed to add to checkout"+error.toString(), Toast.LENGTH_SHORT).show();


            }
        });

    }*/
    private void getAllCourses()
    {
        list.clear();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                list.add(snapshot.getValue(Course.class));

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        displayBottom(list.get(position));

    }
    private void displayBottom(Course course)
    {
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View layout = from(this).inflate(R.layout.bottom_sheet_dialog2,viexx);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        TextView namec=layout.findViewById(R.id.idCourseName);
        TextView desc=layout.findViewById(R.id.idCoursedescription);
        TextView price=layout.findViewById(R.id.idcourceprice);
        TextView suite=layout.findViewById(R.id.idCoursesuite);
        ImageView image=layout.findViewById(R.id.idCourseimage);
        Button btn1=layout.findViewById(R.id.btnedit);
        Button btn2=layout.findViewById(R.id.btndetails);
        namec.setText(course.getCourse_name());
        price.setText(course.getCourse_price());
        desc.setText(course.getCourse_Description());
        suite.setText(course.getCourse_SuitedFor());
        Picasso.get().load(course.getCourse_image()).into(image);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference= data.getReference("panier").child(auth.getUid());
                reference.child(namec.getText().toString()).setValue(null);
                Toast.makeText(Panier.this, "Course removed", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Panier.this,MainActivity.class);
                startActivity(i);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(course.getCourse_Link()));
                startActivity(i);
            }
        });



    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {


                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Geocoder geocoder=new Geocoder(Panier.this, Locale.getDefault());
                                addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    address=addresses.get(0).getAddressLine(0);
                                    city=addresses.get(0).getLocality();
                                    country=addresses.get(0).getCountryName();


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }



                            }

                        }
                    });


        }else
        {

            askPermission();

        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Panier.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }






        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}