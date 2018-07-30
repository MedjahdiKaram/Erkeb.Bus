package com.medjahdi.erkebbus;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medjahdi.erkebbus.models.Config;

public class ConfigureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String busId=((TextView)findViewById(R.id.busIdTxt)).getText().toString();
                String lineNum=((TextView)findViewById(R.id.lineNumTxt)).getText().toString();

                String pid=((TextView)findViewById(R.id.pidTxt)).getText().toString();

                String vid=((TextView)findViewById(R.id.vidTxt)).getText().toString();

                float minAmount= Float.parseFloat(((EditText)findViewById(R.id.minCompostTxt)).getText().toString());
                float amount= Float.parseFloat(((EditText)findViewById(R.id.tarifTxt)).getText().toString());
                Config cfg= new Config(busId,lineNum,vid,pid,minAmount,amount);
                HomeActivity.configService.db_create(cfg);
                Toast.makeText(view.getContext(), "Configuration saved", Toast.LENGTH_LONG).show();
            }


        });
    }

}
