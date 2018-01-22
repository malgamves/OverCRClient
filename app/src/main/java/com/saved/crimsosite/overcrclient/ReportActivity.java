package com.saved.crimsosite.overcrclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class ReportActivity extends AppCompatActivity {

    private Button mLogoutButton;
    //private Button openMap;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity( new Intent(ReportActivity.this, MainActivity.class));
                }
            }
        };

        mLogoutButton = (Button) findViewById(R.id.Logout);


        mLogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View view){
                mAuth.signOut();
            }
        });


        ImageButton acceptB = (ImageButton) findViewById(R.id.accept);

        acceptB.setOnClickListener(new View.OnClickListener()

                               {
                                   public void onClick(View v) {

                                       Intent intent = new Intent(getApplicationContext(), ConfirmReport.class);
                                       startActivity(intent);
                                   }
                               }

        );


    }
}
