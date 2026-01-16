package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.AlertDialog;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int pos = -1; // nothing selected
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);
        Button addButton = findViewById(R.id.add_button);
        Button deleteButton = findViewById(R.id.delete_button);
        addButton.setOnClickListener(v -> {
            // what runs when button is clicked
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); // makes dialog builder

            final EditText input = new EditText(MainActivity.this);
            builder.setView(input);

            builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                String cityName = input.getText().toString().trim(); // reads what user typed, converts to string, trim to remove unwanted spaces
                if (!cityName.isEmpty()) { // to prevent empty city name
                    dataList.add(cityName);
                    cityAdapter.notifyDataSetChanged();
                }
            });
            builder.show(); // displays on screen
        });
        deleteButton.setOnClickListener(v -> {
            if (pos != -1) {
                dataList.remove(pos); // removes selected city
                pos = -1; // reset
                cityAdapter.notifyDataSetChanged();
            }
        });

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            pos = position;
        });
    }
}