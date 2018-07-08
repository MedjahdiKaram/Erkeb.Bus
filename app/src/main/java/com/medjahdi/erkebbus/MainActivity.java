package com.medjahdi.erkebbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.models.Compost;
import com.medjahdi.erkebbus.services.CompostService;
import com.medjahdi.erkebbus.services.FirebaseServiceContext;

public class MainActivity extends AppCompatActivity  {

    private CompostService compostService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        compostService= new CompostService(mDatabase);
        Compost compost = new Compost("bus01","card001",10);
        compostService.append(compost);
        Compost X =compostService.read();


    }


}
