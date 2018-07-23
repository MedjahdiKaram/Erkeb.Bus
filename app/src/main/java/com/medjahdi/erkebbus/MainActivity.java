package com.medjahdi.erkebbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;


import com.medjahdi.erkebbus.controller.MainController;
import com.medjahdi.erkebbus.dal.service.CardService;
import com.medjahdi.erkebbus.dal.service.CompostService;
import com.medjahdi.erkebbus.dal.service.ConfigService;
import com.medjahdi.erkebbus.helpers.Common;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;
import com.medjahdi.erkebbus.models.Config;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    FirebaseDatabase firebaseDataBase;
    private CompostService compostService;
    private CardService cardService;
    protected static ConfigService configService;
    List<Compost> composts = new ArrayList<Compost>();
    List<Card> cards = new ArrayList<Card>();
    public static Config mainConfig;
    private MainController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDataBase = FirebaseDatabase.getInstance();
        firebaseDataBase.getInstance().setPersistenceEnabled(true);
        firebaseDataBase.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);
        compostService = new CompostService(firebaseDataBase, this);
        cardService = new CardService(firebaseDataBase, this);
        configService = new ConfigService(this, getString(R.string.sql_database_name));

        ArrayList<Config> configs= configService.db_read();
        if (configs!=null && configs.size()>0)
            mainConfig= configService.db_read().get(0);
        if (mainConfig==null)
            gotoConfigurationActivity();
        else
        {
            TextView tv = (TextView) findViewById(R.id.busTextView);
            String busId="Bus N°: "+mainConfig.getBusId();
            tv.setText(busId);
            controller= new MainController(mainConfig,firebaseDataBase,this);

            final TextView oldBalanceView= (TextView) findViewById(R.id.oldBalanceView);
            final TextView newBalanceView= (TextView) findViewById(R.id.newBalanceView);
            oldBalanceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double[] balances=controller.runCompostTransaction("OP111");
                    oldBalanceView.setText(String.valueOf(balances[0]));
                    newBalanceView.setText(String.valueOf(balances[1]));
                }
            });


        }
    }

    public void gotoConfigurationActivity()
    {
        try {
            Intent k = new Intent(MainActivity.this, ConfigureActivity.class);
            startActivity(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


