package com.medjahdi.erkebbus;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.medjahdi.erkebbus.controller.MainController;
import com.medjahdi.erkebbus.dal.service.CardService;
import com.medjahdi.erkebbus.dal.service.CompostService;
import com.medjahdi.erkebbus.dal.service.ConfigService;
import com.medjahdi.erkebbus.helpers.Common;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;
import com.medjahdi.erkebbus.models.Config;
import com.medjahdi.hardware.Arduino;
import com.medjahdi.hardware.ArduinoListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HomeActivity extends AppCompatActivity {
    //region Attributes
    private static final String TAG = "";
    FirebaseDatabase firebaseDataBase;
    private CompostService compostService;
    private CardService cardService;
    protected static ConfigService configService;
    List<Compost> composts = new ArrayList<Compost>();
    List<Card> cards = new ArrayList<Card>();
    public static Config mainConfig;
    private MainController controller;
    private Arduino arduino;
    String message;
    private boolean processing_mutex;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region Services and instance initiation
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);
        firebaseDataBase = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDataBase.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);
        compostService = new CompostService(firebaseDataBase, this);
        cardService = new CardService(firebaseDataBase, this);


        configService = new ConfigService(this, getString(R.string.sql_database_name));
        processing_mutex = false;

        //endregion
        //region configuration initiator


        ArrayList<Config> configs = configService.db_read();


        if (configs != null && configs.size() > 0)
            mainConfig = configService.db_read().get(0);
        if (mainConfig == null)
            gotoConfigurationActivity();
            //endregion
        else {
            Integer vid = Integer.parseInt(mainConfig.getVid());
            arduino = new Arduino(this, vid);
            TextView tv = findViewById(R.id.busTextView);
            String busId = "Bus N°: " + mainConfig.getBusId();
            tv.setText(busId);
            controller = new MainController(mainConfig, firebaseDataBase, this);

            final TextView oldBalanceView = findViewById(R.id.oldBalanceView);
            final TextView newBalanceView = findViewById(R.id.newBalanceView);
            //region Button for testing purpose

            oldBalanceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // controller.runCompostTransaction("1B4621C4");

                }
            });


            //endregion

            try {
                arduino.setArduinoListener(new ArduinoListener() {
                    @Override
                    public void onArduinoAttached(UsbDevice device) {
                        Toast.makeText(firebaseDataBase.getApp().getApplicationContext(), "Yo ! Hardware plugged sahhit", Toast.LENGTH_LONG).show();
                        arduino.open(device);


                    }

                    @Override
                    public void onArduinoDetached() {
                        Toast.makeText(firebaseDataBase.getApp().getApplicationContext(), "Yo ! Hardware Communication cut or unpluged ! sahhit", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onArduinoMessage(byte[] bytes) {
                        try {
                            message = message + new String(bytes);
                            String id = Common.CardIdExtractor(message);
                            Thread.sleep(200);
                            if (id != null && processing_mutex == false) {
                                processing_mutex = true;
                                System.out.println("Id: " + id);
                                final double[] balances = controller.runCompostTransaction(id);
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        oldBalanceView.setText("Ancien solde:" + String.valueOf(balances[0]));
                                        newBalanceView.setText("Nouveau solde:" + String.valueOf(balances[1]));

                                    }
                                });


                            }
                            message = "";
                            processing_mutex = false;
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }


                    }

                    @Override
                    public void onArduinoOpened() {
                        Toast.makeText(firebaseDataBase.getApp().getApplicationContext(), "Yo ! Hardware Communication opened ! sahhit", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onUsbPermissionDenied() {
                        Toast.makeText(firebaseDataBase.getApp().getApplicationContext(), "Yo ! No permission to use the usb device ! sahhit", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception ex) {
                System.out.println(ex);
            }

        }

    }



    public void gotoConfigurationActivity() {
        try {
            Intent k = new Intent(HomeActivity.this, ConfigureActivity.class);
            startActivity(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


