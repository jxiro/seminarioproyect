package com.example.easybuy2;

import android.content.Intent;
import android.os.Bundle;

import com.example.easybuy2.Collection.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchHomes extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<String> minlist;
    ArrayList<String> maxlist;

    private MapView map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_homes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map=findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                callService();
        
            }
        });
        loadComponents();
    }

    private void callService() {
        //EditText ciudad = findViewById(R.id.buscarciudad);
        EditText zonas = findViewById(R.id.buscarzona69);
        EditText ofertas = findViewById(R.id.oferta);
        EditText minbanos = findViewById(R.id.minbanos);
        EditText mindorms = findViewById(R.id.mincuartos);
        EditText años = findViewById(R.id.yearr);
        EditText tipos = findViewById(R.id.tipo);
        EditText sups = findViewById(R.id.supterreno);

        Spinner spinnermin = findViewById(R.id.min);
        Spinner spinnermax = findViewById(R.id.max);
        Data.item_data= new ArrayList<>();

        //final String city = ciudad.getText().toString();
        final String zonas1 = zonas.getText().toString();
        final String ofertas1 = ofertas.getText().toString();
        final String minbanos1 = minbanos.getText().toString();
        final String mindorms1 = mindorms.getText().toString();
        final String años1 = años.getText().toString();
        final String tipos1 = tipos.getText().toString();
        final String sups1 = sups.getText().toString();

        String spinmin = minlist.get(spinnermin.getSelectedItemPosition());
        String spinmax = maxlist.get(spinnermax.getSelectedItemPosition());





        AsyncHttpClient client1 = new AsyncHttpClient();
        /*RequestParams params = new RequestParams();
        params.add("min", minlist.get(spinnermin.getSelectedItemPosition()));
        params.add("max", maxlist.get(spinnermax.getSelectedItemPosition()));
        params.add("search", ciudad.getText().toString());
        params.add("search1", zona.getText().toString());*/

        Toast.makeText(SearchHomes.this, "load components", Toast.LENGTH_LONG).show();
        client1.get(Utils.LIST_HOMES_SERVICE +"?"+
                "minp="+spinmin+
                "maxp="+spinmax+
                "mins="+sups1 +
                "minh=" +mindorms1+
                "minb="+minbanos1+
                "miny="+años1 +
                "searcho="+ofertas1+
                "searcht=" +tipos1+
                "searchz="+zonas1
                , new JsonHttpResponseHandler()

        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {

                    //JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        Toast.makeText(SearchHomes.this, "eso!", Toast.LENGTH_LONG).show();

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

                        Data.item_data.add(p);

                    }
                }catch(JSONException error){
                    //Toast.makeText(SearchHomes.this, "error al cargar datos", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    Log.d("Error", error.getMessage());
                }
                Intent tosearch = new Intent(SearchHomes.this, SearchListR.class);
                startActivity(tosearch);

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("===> falla", "");
            }
        });
    }

    private void loadComponents() {
        Spinner min =findViewById(R.id.min);
        Spinner max =findViewById(R.id.max);

        minlist= new ArrayList<>();
        maxlist = new ArrayList<>();

        minlist.add("40000");
        minlist.add("50000");
        minlist.add("50000");
        minlist.add("60000");
        minlist.add("70000");
        minlist.add("80000");
        minlist.add("90000");
        minlist.add("100000");
        minlist.add("110000");

        maxlist.add("40000");
        maxlist.add("50000");
        maxlist.add("50000");
        maxlist.add("60000");
        maxlist.add("70000");
        maxlist.add("80000");
        maxlist.add("90000");
        maxlist.add("100000");
        maxlist.add("110000");

        
        ArrayAdapter<String> adaptermin = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, minlist);
        ArrayAdapter<String> adaptermax = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, maxlist);
        min.setAdapter(adaptermin);
        max.setAdapter(adaptermax);


}
    private GoogleMap mMap;
    Marker marker;

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        getzone();
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        final LatLng potosi = new LatLng(-19.5860579, -65.7537078 );
        if (marker != null){
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()

                .position(potosi)
                .title("My Home To Oferting")
                .snippet("Be Precise")
                .draggable(true));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi, 16));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(potosi)
                .zoom(16)
                .bearing(170)
                .tilt(10)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(lat1,long1),
                        new LatLng(lat2,long2),
                        new LatLng(lat3,long3),
                        new LatLng(lat1,long1)
                        //new LatLng(-19.5764264,-65.7661845),
                        //new LatLng(-19.5767871,-65.7664691),
                        //new LatLng(-19.5777907,-65.7683409),
                        //new LatLng(-19.5749634,-65.7680291),
                        //new LatLng(-19.56914,-65.7687963)
                ));
        final LatLng cs = new LatLng(-19.56914,-65.7687963);
        if (marker != null){
            marker.remove();}
        CameraPosition cameraPositioncs = CameraPosition.builder()
                .target(cs)
                .zoom(13)
                .bearing(170)
                .tilt(10)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositioncs));



            }
    Double lat1=0.0;
    Double long1=0.0;
    Double lat2=0.0;
    Double long2=0.0;
    Double lat3=0.0;
    Double long3=0.0;
    Double lat4=0.0;
    Double long4=0.0;

    private void getzone() {

        FloatingActionButton fab1 =findViewById(R.id.fab2);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "ok", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                EditText buscarzona = findViewById(R.id.buscarzona69);

                final String buscarz = buscarzona.getText().toString();

                Toast.makeText(SearchHomes.this, "Send"+ buscarz, Toast.LENGTH_LONG).show();
                if(buscarz.equals("ciudad satelite") ){
                    lat1 +=  -19.56914;
                    long1 += -65.7687963;
                    lat2 +=  -19.5667791;
                    long2 += -65.7673669;
                    lat3 +=  -19.5746544;
                    long3 += -65.7652978;
                    lat4 += -19.5764264;
                    long4 += -65.7661845;
                    mMap.addPolyline(new PolylineOptions()
                            .clickable(true)
                            .add(
                                    new LatLng(lat1,long1),
                                    new LatLng(lat2,long2),
                                    new LatLng(lat3,long3),
                                    new LatLng(lat4,long4),
                                    new LatLng(lat1,long1)
                                    //new LatLng(-19.5764264,-65.7661845),
                                    //new LatLng(-19.5767871,-65.7664691),
                                    //new LatLng(-19.5777907,-65.7683409),
                                    //new LatLng(-19.5749634,-65.7680291),
                                    //new LatLng(-19.56914,-65.7687963)
                            ));
                    final LatLng cs = new LatLng(-19.56914,-65.7687963);
                    if (marker != null){
                        marker.remove();}
                    CameraPosition cameraPositioncs = CameraPosition.builder()
                            .target(cs)
                            .zoom(15)
                            .bearing(170)
                            .tilt(10)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositioncs));
                }

            }
        });

    }


}
