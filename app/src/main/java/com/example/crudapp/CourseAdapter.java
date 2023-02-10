package com.example.crudapp;

import static android.view.LayoutInflater.from;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder >{
    private ArrayList<Course>liste;
    private Context context;
    int lastPos =-1;

    private FirebaseDatabase data;
    private DatabaseReference reference;
    private FirebaseUser auth;
    private CourseClickInterface courceinterface;

    public CourseAdapter(ArrayList<Course> liste, Context context, CourseClickInterface courceinterface) {
        this.liste = liste;
        this.context = context;
        this.courceinterface = courceinterface;
    }

    public CourseAdapter(@NonNull FirebaseRecyclerOptions<Course> options) {
      super();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item1,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
Course coursee=liste.get(position);
holder.coursename.setText(coursee.getCourse_name());
        holder.courseprice.setText(coursee.getCourse_price() +" DT");

        Picasso.get().load(coursee.getCourse_image()).into(holder.image);//courseIV
       //setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                courceinterface.onCourseClick(position);

            }
        });


    /* holder.btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data= FirebaseDatabase.getInstance();
                auth=FirebaseAuth.getInstance().getCurrentUser();

                reference= data.getReference("panier").child(auth.getUid());


               reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        reference.child(coursee.getCourse_ID()).setValue(coursee);
                        Toast.makeText(context.getApplicationContext(), "Course Added to panier", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(context.getApplicationContext(), "Course Failed to add to panier"+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });*/


    }



    private void setAnimation(View item,int position)
{
    if(position>lastPos)
    {
        Animation animation= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        item.setAnimation(animation);
        lastPos=position;
    }

}
    @Override
    public int getItemCount() {
        return liste.size();
    }


    public interface CourseClickInterface {
        void onCourseClick(int position);
    }
    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView coursename,courseprice;
        private ImageView image;
        private Button btnp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coursename=itemView.findViewById(R.id.namecourse);
            courseprice=itemView.findViewById(R.id.pricecourse);
            image=itemView.findViewById(R.id.imagecourse);
            btnp=itemView.findViewById(R.id.btn);
        }
    }
}
