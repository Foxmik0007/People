package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nom;
    private EditText prenom;
    private Button Enter, access;
    public static List<Person> people;
    ListView LisViewPeople;

    DatabaseReference PersonDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // CATCHING
        PersonDataBase = FirebaseDatabase.getInstance().getReference();
        nom = (EditText) findViewById(R.id.Name);
        prenom = (EditText) findViewById(R.id.Last);
        Enter = (Button) findViewById(R.id.Enter);
        people = new ArrayList<>();
        LisViewPeople = (ListView) findViewById(R.id.People);
        access = (Button) findViewById(R.id.Access);
        people.add(new Person("0", "Mika","Rafaralahy" ));

        //Entrer des données sur la base de donnée
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPerson();
            }
        });
        //Voir les données sur la base de donnée
        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Liste.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        PersonDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot peopleSnapShot : dataSnapshot.getChildren()) {
                    Person person = peopleSnapShot.getValue(Person.class);
                    boolean verify = false;

                    // Ajout/ Mise à jour de la base de donnée
                        for (short i = 0; i < people.size(); i++) {
                            if (person.getName() == people.get(i).getName())
                                verify = true;

                        }
                        if (!verify)
                            people.add(person);
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void AddPerson (){
        String name = nom.getText().toString().trim();
        String lastName = prenom.getText().toString().trim();

        if (!TextUtils.isEmpty(name)){
            String id = PersonDataBase.push().getKey();
            Person Person = new Person(id, name,  lastName);

            PersonDataBase.child(id).setValue(Person);

            Toast.makeText(this, "Complete",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "You MF", Toast.LENGTH_LONG).show();
        }

}


}
