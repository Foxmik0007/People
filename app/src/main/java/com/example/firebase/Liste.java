package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import static com.example.firebase.MainActivity.people;

public class Liste extends AppCompatActivity {
    ListView Table;
    List <Person> ListeDePersonne = people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        Table = (ListView) findViewById(R.id.Table);

        PeopleList adapter = new PeopleList(Liste.this,ListeDePersonne );
        Table.setAdapter(adapter);
    }
}