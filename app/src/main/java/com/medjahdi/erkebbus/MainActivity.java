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


public class MainActivity extends AppCompatActivity {
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
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region Services and instance initiation
        super.onCreate(savedInstanceState);
        arduino=new Arduino(this);
        setContentView(R.layout.activity_main);
        firebaseDataBase = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDataBase.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);
        compostService = new CompostService(firebaseDataBase, this);
        cardService = new CardService(firebaseDataBase, this);
        configService = new ConfigService(this, getString(R.string.sql_database_name));


        //endregion
        //region configuration initiator


        ArrayList<Config> configs = configService.db_read();
        if (configs != null && configs.size() > 0)
            mainConfig = configService.db_read().get(0);
        if (mainConfig == null)
            gotoConfigurationActivity();
            //endregion
        else {
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
                    double[] balances = controller.runCompostTransaction("OP111");
                    oldBalanceView.setText(String.valueOf(balances[0]));
                    newBalanceView.setText(String.valueOf(balances[1]));
                }
            });


            //endregion
            /*
            ProbeTable customTable = new ProbeTable();
            customTable.addProduct(0x2A03, 0x0043, CdcAcmSerialDriver.class);
            //customTable.addProduct(0x1234, 0x0002, CdcAcmSerialDriver.class);

            UsbSerialProber prober = new UsbSerialProber(customTable);


            UsbManager manager = (UsbManager) getSystemService(this.USB_SERVICE);
            //   List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
            List<UsbSerialDriver> availableDrivers = prober.findAllDrivers(manager);
            if (availableDrivers.isEmpty()) {
                return;
            }

// Open a connection to the first available driver.
            UsbSerialDriver driver = availableDrivers.get(0);
            UsbDevice device = driver.getDevice();
            String ACTION_USB_PERMISSION = "com.medjahdi.erkebbus.USB_PERMISSION";

            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                    ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            manager.requestPermission(device, mPermissionIntent);
            boolean hasperm = manager.hasPermission(device);
            UsbDeviceConnection connection = manager.openDevice(device);
            if (connection == null) {
                // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
                return;
            }

// Read some data! Most have just one port (port 0).
            UsbSerialPort port = driver.getPorts().get(0);
            try {
                port.open(connection);
                port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                byte buffer[] = new byte[16];
                String message = null;
                while (true ) {
                    int numBytesRead = port.read(buffer, 500);
                    message += new String(buffer);
                    String fromDuino = new String(message);
System.out.println(fromDuino);
                    Log.d(TAG, "Read " + numBytesRead + " bytes.");
                }
            } catch (IOException e) {
                // Deal with error.
            } finally {
                try {
                    port.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



*/









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
                            if (message.length() >= 10 && Common.countOccurences(message, '#') % 2 == 0) {
                                String id = Common.CardIdExtractor(message, '#');
                                System.out.println("Id: " + id);
                                if (id != null && id.length() == 7) {
                                    final double[] balances = controller.runCompostTransaction(id);
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                             oldBalanceView.setText(String.valueOf(balances[0]));
                                            newBalanceView.setText(String.valueOf(balances[1]));

                                        }
                                    });

                                }
                            }
                            String fromDuino = new String(message);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                       // double[] balances = controller.runCompostTransaction(fromDuino);
                        //oldBalanceView.setText(String.valueOf(balances[0]));
                        //newBalanceView.setText(String.valueOf(balances[1]));
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
               // arduino.reopen();
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }

        }

    }


    public void gotoConfigurationActivity() {
        try {
            Intent k = new Intent(MainActivity.this, ConfigureActivity.class);
            startActivity(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


