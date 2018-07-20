package com.medjahdi.erkebbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.medjahdi.erkebbus.dal.service.CardService;
import com.medjahdi.erkebbus.dal.service.CompostService;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    FirebaseDatabase firebaseDataBase;
    private CompostService compostService;
    private CardService cardService;
    List<Compost> composts = new ArrayList<Compost>();
    List<Card> cards= new ArrayList<Card>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDataBase = FirebaseDatabase.getInstance();
        compostService = new CompostService(firebaseDataBase, this,getString(R.string.composts_creation_query));
        cardService = new CardService(firebaseDataBase, this,getString(R.string.cards_creation_query));
        Button b = (Button) findViewById(R.id.readbtn);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //List<Compost> cmpresult = compostService.read();
                //List<Card> crdsresult = cardService.read();
                Card card = new Card("9100001");
                cardService.db_create(card);
                int val = cardService.db_count();
            }


        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }




}


