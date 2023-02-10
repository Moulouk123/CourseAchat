package com.example.crudapp;


import static android.view.LayoutInflater.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;
public class MainActivity2 extends AppCompatActivity implements CourseAdapter.CourseClickInterface {
private RecyclerView recyclerView;
private ProgressBar loading;
private FloatingActionButton btn;
private FirebaseDatabase data;

private DatabaseReference reference;
private ArrayList<Course>list;

private RelativeLayout viexx;
private CourseAdapter adapter;
private FirebaseAuth auth;

@SuppressLint("MissingInflatedId")
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.idrecycler);
        loading=findViewById(R.id.idloading);
        btn=findViewById(R.id.btnm);
        viexx =findViewById(R.id.sheet);
        auth=FirebaseAuth.getInstance();
        data= FirebaseDatabase.getInstance();
        reference= data.getReference("courses");
        list=new ArrayList<>();
        adapter=new CourseAdapter(list,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent i = new Intent(MainActivity2.this,Add_courseActivity.class);
        startActivity(i);
        }
        });
        getAllCourses();
        }
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
        View layout = from(this).inflate(R.layout.bottom_sheet_dialog3,viexx);
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
        Intent i = new Intent(MainActivity2.this,Edit_courseActivity.class);
        i.putExtra("courses",course);
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

public boolean onCreateOptionsMenu(Menu menu)
        {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        getMenuInflater().inflate(R.menu.searchmain,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView search = (SearchView) item.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
@Override
public boolean onQueryTextSubmit(String s) {
        processsearch(s);
        return false;
        }

@Override
public boolean onQueryTextChange(String s) {
        processsearch(s);

        return false;
        }
        });

        return super.onCreateOptionsMenu(menu);
        //return true;
        }

private void processsearch(String s) {
        FirebaseRecyclerOptions<Course> options=new FirebaseRecyclerOptions.Builder<Course>()
        .setQuery(FirebaseDatabase.getInstance().getReference().child("courses").child("course_ID"),Course.class)
        .build();

        adapter=new CourseAdapter(options);




        }

@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
                case R.id.logout:
                        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        Intent i = new Intent(MainActivity2.this, LoginActivity.class);
                        startActivity(i);
                        this.finish();

                        return true;


                case R.id.profil:
                        Intent in = new Intent(MainActivity2.this, profil.class);
                        in.putExtra("prof", "Professeur");

                        startActivity(in);
                        this.finish();
                case R.id.localisation:
                        Intent innt = new Intent(MainActivity2.this, local.class);
                        startActivity(innt);
                        this.finish();
                case R.id.panier:
                        Intent p=new Intent(MainActivity2.this,MainActivity2.class);
                        startActivity(p);
                        this.finish();




                default:
                        return super.onOptionsItemSelected(item);


        }
}}
