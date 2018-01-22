package com.saved.crimsosite.overcrclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by DanielPhiri on 1/15/2017.
 */
public class ConfirmReport extends AppCompatActivity{


    private BroadcastReceiver broadcastReciever;
    private TextView Textview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_report);

        ImageButton yes = (ImageButton) findViewById(R.id.yes);
        ImageButton no = (ImageButton) findViewById(R.id.no);

        if(!runtime_permissions())
            enable_buttons();

        yes.setOnClickListener(new View.OnClickListener()

                              {
                                  public void onClick(View v) {

                                      Intent intent = new Intent(getApplicationContext(), ConfirmedSent.class);
                                      startActivity(intent);
                                  }
                              }
        );



        no.setOnClickListener(new View.OnClickListener()

                              {
                                  public void onClick(View v) {

                                      Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                                      startActivity(intent);
                                  }
                              }
        );


    }

    private void enable_buttons() {

        ImageButton yes = (ImageButton) findViewById(R.id.yes);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }
        });


    }

    private boolean runtime_permissions() {

        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }


    }

    @Override
    protected void onResume() {

        final TextView Textview = (TextView) findViewById(R.id.textView5);


        super.onResume();
        if(broadcastReciever == null){
            broadcastReciever = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Textview.append("\n"+intent.getExtras().get("coordinates"));

                }
            };
        }
        registerReceiver(broadcastReciever, new IntentFilter("location_update"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReciever != null){
            unregisterReceiver(broadcastReciever);
        }
    }
}
