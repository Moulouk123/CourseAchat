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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Edit_courseActivity extends AppCompatActivity {
    private TextInputEditText name,price,suited,link,image,description;
    private Button btnupdate,btndelete;
    private ProgressBar loading;
    private FirebaseDatabase data;
    private DatabaseReference reference;
    String coursen,courses,coursel,coursed,coursei,courseID,coursep;
    private Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        data= FirebaseDatabase.getInstance();
        name=findViewById(R.id.coursename);
        price=findViewById(R.id.courseprice);
        suited=findViewById(R.id.courseSyitedFor);
        image=findViewById(R.id.courseimage);
        link=findViewById(R.id.courselink);
        description=findViewById(R.id.coursedescrption);
        btndelete=findViewById(R.id.deletecourse);
        btnupdate=findViewById(R.id.updatecourse);
        loading=findViewById(R.id.idloading);
        course=getIntent().getParcelableExtra("courses");
        if(course!=null)
        {
           name.setText(course.getCourse_name());
           price.setText(course.getCourse_price());
           suited.setText(course.getCourse_SuitedFor());
           image.setText(course.getCourse_image());
           link.setText(course.getCourse_Link());
           description.setText(course.getCourse_Description());
           courseID=course.getCourse_ID();



        }
        reference= data.getReference("courses").child(courseID);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                coursen=name.getText().toString();
                coursep=price.getText().toString();
                courses=suited.getText().toString();
                coursel=link.getText().toString();
                coursed=description.getText().toString();
                coursei=image.getText().toString();
                Map<String,Object> map= new HashMap<>();
                map.put("course_name",coursen);
                map.put("course_price",coursep);

                map.put("course_SuitedFor",courses);

                map.put("course_image",coursei);

                map.put("course_Link",coursel);

                map.put("course_Description",coursed);
                map.put("course_ID",courseID);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loading.setVisibility(View.GONE);
                        reference.updateChildren(map);
                        Toast.makeText(Edit_courseActivity.this, "Course updated", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(Edit_courseActivity.this,MainActivity2.class);
                        startActivity(i);
                        finish();





                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Edit_courseActivity.this, "Course update FAILED", Toast.LENGTH_SHORT).show();


                    }
                });







            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
                loading.setVisibility(View.GONE);



            }
        });

    }
    private void deleteCourse()
    {
        reference.setValue(null);
        Toast.makeText(Edit_courseActivity.this, "Course removed", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Edit_courseActivity.this,MainActivity2.class);
        startActivity(i);


    }
}