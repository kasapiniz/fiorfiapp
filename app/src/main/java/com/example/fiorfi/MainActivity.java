package com.example.fiorfi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
 Button login;
 EditText email,password;
 TextView textViewSignup,textViewhelpsigninsag,textViewhelpsigninsol,textViewSignupsolda;

FirebaseAuth auth;


    FirebaseUser firebaseUser;

     @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null ){
            startActivity(new Intent(MainActivity.this,AnaActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       login=findViewById(R.id.login);
       email=findViewById(R.id.email);
       password=findViewById(R.id.password);
        textViewSignup=findViewById(R.id.textViewSignup);
        textViewhelpsigninsag=findViewById(R.id.textViewhelpsigninsag);
        textViewhelpsigninsol=findViewById(R.id.textViewhelpsigninsol);
        textViewSignupsolda=findViewById(R.id.textViewSignupsolda);


        auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){

                    Toast.makeText(MainActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }else{

                    auth.signInWithEmailAndPassword(str_email,str_password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful()){
                                         DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                 .child(auth.getCurrentUser().getUid());

                                         reference.addValueEventListener(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 pd.dismiss();
                                                 Intent intent = new Intent(MainActivity.this,AnaActivity.class);
                                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                 startActivity(intent);
                                                 finish();
                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError error) {
                                                pd.dismiss();
                                             }
                                         });
                                     }else{
                                         pd.dismiss();
                                         Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                     }
                                }
                            });
                }

            }
        });
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(yeniIntent);
            }
        });

        textViewhelpsigninsag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniIntent = new Intent(MainActivity.this,LoginHelp.class);
                startActivity(yeniIntent);
            }
        });
        textViewhelpsigninsol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniIntent = new Intent(MainActivity.this,LoginHelp.class);
                startActivity(yeniIntent);
            }
        });   textViewSignupsolda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(yeniIntent);
            }
        });


    }
}
