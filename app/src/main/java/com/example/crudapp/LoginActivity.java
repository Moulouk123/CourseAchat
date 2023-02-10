package com.example.crudapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
private TextInputEditText email,pass;
private Button btn;
private ProgressBar loading;
private TextView txt,upd;
private FirebaseAuth auth;
private RadioButton proff,etud;
    CallbackManager  callbackManager;
    String mail , passsword;
ImageView facebook,googleg;
GoogleSignInOptions gso;
GoogleSignInClient gsc,client;
public static String rolelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editemail);
        pass=findViewById(R.id.editpassword);
        loading=findViewById(R.id.idloading);
        btn=findViewById(R.id.btnl);
        upd=findViewById(R.id.update);
        txt=findViewById(R.id.idregister);
        etud=findViewById(R.id.etudiant);
        proff=findViewById(R.id.prof);
        googleg=findViewById(R.id.logog);
        facebook=findViewById(R.id.logof);
        auth=FirebaseAuth.getInstance();
        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();
        client=GoogleSignIn.getClient(this,options);
        googleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =client.getSignInIntent();

               startActivityForResult(i,1234);


            }
        });/*
        FacebookSdk.sdkInitialize(LoginActivity.this);
        AppEventsLogger.activateApp(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Intent i=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);


                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(LoginActivity.this,MainActivity.class);;
                startActivityForResult(i,1234);

                LoginManager.getInstance().logInWithPublishPermissions(LoginActivity.this, Arrays.asList("public_profile"));

            }
        });*/
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,register2.class);
                startActivity(i);
            }
        });
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,forgetpassword.class);
                startActivity(i);            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=email.getText().toString();
                passsword=pass.getText().toString();
                if(mail.isEmpty()&&passsword.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "add your credentials", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    auth.signInWithEmailAndPassword(mail,passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                loading.setVisibility(View.GONE);

                                if(proff.isChecked())
                                {
                                    rolelogin="Professeur";
                                    Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this,MainActivity2.class);
                                    i.putExtra("email",email.getText().toString());
                                    i.putExtra("prof",rolelogin);

                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    rolelogin = "Etudiant";

                                    Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.putExtra("email", email.getText().toString());
                                    i.putExtra("prof", rolelogin);

                                    startActivity(i);
                                    finish();
                                }

                            }
                            else
                            {
                                loading.setVisibility(View.GONE);

                                Toast.makeText(LoginActivity.this, "Login FAILED", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }


            }
        });
    }


    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                Toast.makeText(LoginActivity.this, account.getIdToken(), Toast.LENGTH_SHORT).show();

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if(proff.isChecked())
                                    {
                                        rolelogin="Professeur";
                                        Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LoginActivity.this,MainActivity2.class);
                                        i.putExtra("email",email.getText().toString());
                                        i.putExtra("prof",rolelogin);

                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        rolelogin = "Etudiant";

                                        Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        i.putExtra("email", email.getText().toString());
                                        i.putExtra("prof", rolelogin);

                                        startActivity(i);
                                        finish();

                                    }
                                }




                                 else {
                                    System.out.println("caych code " + task.getException());

                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }
    /*
   public void onActivityResult(int requestcode, int resultcode, Intent data) {
       super.onActivityResult(requestcode, resultcode, data);
       callbackManager.onActivityResult(requestcode, resultcode, data);


   }*/
    protected void onStart() {

        super.onStart();
        FirebaseUser user =auth.getCurrentUser();
if(user!=null)
{
    if(proff.isChecked())
    {
        rolelogin="Professeur";
        Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this,MainActivity2.class);
        i.putExtra("email",email.getText().toString());
        i.putExtra("prof",rolelogin);

        startActivity(i);
        finish();
    }
    else {
        rolelogin = "Etudiant";

        Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("email", email.getText().toString());
        i.putExtra("prof", rolelogin);

        startActivity(i);
        finish();
    }
}
    }
}