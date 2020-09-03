package com.example.easybuy2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easybuy2.Collection.Item;

import java.util.ArrayList;

public class SearchListR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list_r);
        loadComponentes();
    }

    private void loadComponentes() {
        final ListView list = this.findViewById(R.id.list_main1);
        final ArrayList<Item> list_data = new ArrayList<>();
        Toast.makeText(SearchListR.this, "load componentES", Toast.LENGTH_LONG).show();
        ListAdapter adapter = new com.example.easybuy2.Collection.ListAdapter(SearchListR.this,Data.item_data);
        list.setAdapter(adapter);
    }



}
