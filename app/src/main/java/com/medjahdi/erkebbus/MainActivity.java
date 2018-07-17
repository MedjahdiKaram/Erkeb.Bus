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
    FirebaseDatabase mDatabase;
    private CompostService compostService;
    private CardService cardService;
    List<Compost> composts = new ArrayList<Compost>();
    List<Card> cards= new ArrayList<Card>();
    ValueEventListener compostEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            compostService.firebaseDataContext.onDataChange(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            compostService.firebaseDataContext.onCancelled(databaseError);
//                System.out.println("The read failed: " + databaseError.getCode());
        }
    };
    ValueEventListener cardEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            cardService.firebaseDataContext.onDataChange(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            cardService.firebaseDataContext.onCancelled(databaseError);
//                System.out.println("The read failed: " + databaseError.getCode());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference compostDbReference = mDatabase.getReference("Compost");
        DatabaseReference cardDbReference = mDatabase.getReference("Card");

        compostService = new CompostService(mDatabase);
        cardService = new CardService(mDatabase);
        compostDbReference.addValueEventListener(compostEventListener);
        cardDbReference.addValueEventListener(cardEventListener);
        Button b = (Button) findViewById(R.id.readbtn);
        /*for (int i=0; i<10000;i++) {
            Card card = new Card("C"+String.valueOf(i));
            Compost compost=new Compost("B"+String.valueOf(i*2),"C"+String.valueOf(i),20);
           compostService.append(compost);
            cardService.append(card);
        }*/
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ArrayList<Compost> cmpresult = compostService.read();
                ArrayList<Card> crdsresult = cardService.read();
            }


        });

        //dbReference.push().setValue(object);
        //composts = compostService.read();




    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }




}


