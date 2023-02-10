package com.example.crudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  Add_courseActivity extends AppCompatActivity {
    private TextInputEditText name,price,suited,link,image,description;
private Button add;
private ProgressBar loading;
private FirebaseDatabase data;
private DatabaseReference reference;
String coursen,courses,coursel,coursed,coursei,courseID,coursep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        name=findViewById(R.id.coursename);
        price=findViewById(R.id.courseprice);
        suited=findViewById(R.id.courseSyitedFor);
        image=findViewById(R.id.courseimage);
        link=findViewById(R.id.courselink);
        description=findViewById(R.id.coursedescrption);
        add=findViewById(R.id.addcourse);
        loading=findViewById(R.id.idloading);

        data= FirebaseDatabase.getInstance();
       reference= data.getReference("courses");
       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               loading.setVisibility(View.VISIBLE);

               coursen=name.getText().toString();
               coursep=price.getText().toString();
               courses=suited.getText().toString();
               coursel=link.getText().toString();
               coursed=description.getText().toString();
               coursei=image.getText().toString();
               courseID=coursen;
               Course course=new Course(coursen,coursen,coursep,courses,coursei,coursel,coursed);
               reference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       loading.setVisibility(View.GONE);

                       reference.child(courseID).setValue(course);
                       Toast.makeText(Add_courseActivity.this, "Course Added interface", Toast.LENGTH_SHORT).show();
                         Intent i= new Intent(Add_courseActivity.this,MainActivity2.class);
                         startActivity(i);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       loading.setVisibility(View.GONE);

                       Toast.makeText(Add_courseActivity.this, "Course Failed"+error.toString(), Toast.LENGTH_SHORT).show();


                   }
               });


           }
       });

    }
}