package com.example.lab03_02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab03_02.DatabaseHandler;
import com.example.lab03_02.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ListView lv = findViewById(R.id.lv_contact);
        DatabaseHandler db = new DatabaseHandler(this);
        db.deleteAllContact();
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.e("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        List<String> contactInfoList = new ArrayList<>();

        for (Contact cn : contacts) {
            String log = "ID: " + cn.getID() + " ,Name: " + cn.getName() + ",Phone: " + cn.getPhoneNumber();
            String contactInfo = "ID: " + cn.getID() + ", Name: " + cn.getName() + ", Phone: " + cn.getPhoneNumber();
            contactInfoList.add(contactInfo);
            // Writing Contacts to log
            Log.e("Name: ", log);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactInfoList);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                contactInfoList.remove(position);
                Contact contactToRemove = contacts.get(position);
                db.deleteContact(contactToRemove);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}