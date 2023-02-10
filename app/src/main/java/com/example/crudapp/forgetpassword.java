package com.example.crudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {
ProgressDialog dialog;
FirebaseAuth auth;
TextInputEditText email;
TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        auth=FirebaseAuth.getInstance();
        txt=findViewById(R.id.retour);
        dialog=new ProgressDialog(forgetpassword.this);
        dialog.setCancelable(false);
        dialog.setMessage(" Loading ...........");
        email=findViewById(R.id.eemail);
        System.out.println(email.getText().toString());
txt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(forgetpassword.this, LoginActivity.class);
        startActivity(i);
    }
});


    }
  public void reset (View v){
      Toast.makeText(forgetpassword.this, "hello "+email.getText().toString(), Toast.LENGTH_SHORT).show();

      if (!email.getText().toString().isEmpty()) {
          System.out.println(email.getText().toString()+"not empty");

          auth.sendPasswordResetEmail(email.getText().toString())
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                          Intent i = new Intent(forgetpassword.this, LoginActivity.class);
                          startActivity(i);
                          Toast.makeText(forgetpassword.this, "update succed check you mail " + email.getText().toString(), Toast.LENGTH_SHORT).show();


                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(forgetpassword.this, "Error updated failed", Toast.LENGTH_SHORT).show();
                      }
                  });
      }
        }

}