package com.example.easybuy2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easybuy2.Collection.Item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Homes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        loadComponents();

    }

    private void loadComponents() {

        final ListView list = this.findViewById(R.id.list_main);
        final ArrayList<Item> list_data = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utils.LIST_HOMES_SERVICE, new JsonHttpResponseHandler()

        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {

                    //JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        Toast.makeText(Homes.this, "3", Toast.LENGTH_LONG).show();
                        Item p = new Item();
                        JSONObject obj = response.getJSONObject(i);
                        p.id = i;

                        p.description = obj.getString("descripcion");
                        p.numDormitorios=obj.getString("numDormitorios");
                        p.numBanos=obj.getString("numBanos");
                        p.supTerreno=obj.getString("supTerreno");
                        p.precio=obj.getString("precio");
                        p.oferta=obj.getString("oferta");
                        p.yearCOns=obj.getString("yearCOns");
                        p.url = Utils.HOST_IMAGE + obj.getString("img");

                        list_data.add(p);

                    }
                }catch(JSONException error){
                    Toast.makeText(Homes.this, "4", Toast.LENGTH_LONG).show();
                    //error.printStackTrace();
                    Log.d("Error", error.getMessage());
                }
                ListAdapter adapter = new com.example.easybuy2.Collection.ListAdapter(Homes.this,list_data);
                list.setAdapter(adapter);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("===> falla", "");
            }
        });
    }
}
