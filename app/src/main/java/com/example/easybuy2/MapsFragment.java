package com.example.easybuy2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easybuy2.Fragments.NewHome;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private View ROOT;
    private GoogleMap mMap;
    Marker marker;
    TextView latitud;
    TextView longitud;
    TextView Mydirection;
    /*private Double latitud = 0.0;
    private Double longitud= 0.0;

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
*/
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        MapsInitializer.initialize(getContext());



        /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            //locationStart();
        }*/



    }


    private void locationStart() {

        latitud = (TextView) getActivity().findViewById(R.id.latitude);
        longitud = (TextView) getActivity().findViewById(R.id.longitude);
        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        latitud.setText("Localización agregada");
        longitud.setText("Localización agregada");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    Mydirection.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MapsFragment mainActivity;
        public MapsFragment getMapsFragment() {
            return mainActivity;
        }
        public void setMainActivity(MapsFragment mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            Mydirection.setText(Text);
            this.mainActivity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Mydirection.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            Mydirection.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_maps, container, false);
        ROOT=inflater.inflate(R.layout.activity_maps, container, false);

        return ROOT;

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //Marker marker;
    Button btnGPS;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getLocation();

        // Add a marker in Sydney and move the camera

        final LatLng potosi = new LatLng(-19.5689736, -65.7748895 );
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



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getActivity(), "Coordinates: \n" +
                        "Lat:"+ latLng.latitude+"\n"+
                        "Long:" + latLng.longitude
                , Toast.LENGTH_LONG).show();


            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //Geocoder gc new Geocoder(MapsFragment.this);
                final LatLng ll = marker.getPosition();



                FloatingActionButton fab = getView().findViewById(R.id.send_cor);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Coordenates saved", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        final double latitud1= ll.latitude;
                        final double longitud1 =ll.longitude;


                        final String latistring = Double.toString(latitud1);
                        final String longstring = Double.toString(longitud1);
                        AddHome.latitude=latistring;
                        AddHome.longitude=longstring;

                         latitud = (TextView) getView().findViewById(R.id.latitude);
                         longitud = (TextView) getView().findViewById(R.id.longitude);



                        latitud.setText(latistring);
                        longitud.setText(longstring);

                    }
                });




                //MapsFragment mf = new MapsFragment();
                //final  Double lati =mf.getLatitud();
                //final  Double longi =mf.getLongitud();



            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_LONG).show();





                return false;
            }
        });
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void getLocation() {

        FloatingActionButton fab = getView().findViewById(R.id.send_my_loc);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Send Location", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        latitud = (TextView) getView().findViewById(R.id.latitude);
                        longitud = (TextView) getView().findViewById(R.id.longitude);

                       final Double lati = location.getLatitude();
                        final Double longi= location.getLongitude();


                        final String latistring = Double.toString(lati);
                        final String longstring = Double.toString(longi);
                        AddHome.latitude=latistring;
                        AddHome.longitude=longstring;
                        latitud.setText(latistring);
                        longitud.setText(longstring);
                        final LatLng miUbicacion = new LatLng(lati, longi );
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()

                                .position(miUbicacion)
                                .title("My Home To Oferting")
                                .snippet("Be Precise")
                                .draggable(true));

                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi, 16));
                        CameraPosition cameraPosition1 = CameraPosition.builder()
                                .target(miUbicacion)
                                .zoom(16)
                                .bearing(170)
                                .tilt(10)
                                .build();



                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                Toast.makeText(getActivity(), "Coordinates: \n" +
                                                "Lat:"+ latLng.latitude+"\n"+
                                                "Long:" + latLng.longitude
                                        , Toast.LENGTH_LONG).show();


                            }
                        });

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);



            }
        });
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_LONG).show();
            } else
            {
                ActivityCompat.requestPermissions(getActivity(), new  String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView map = (MapView) ROOT.findViewById(R.id.map);
        map.getMapAsync(this);

       /* MapsFragment mf = new MapsFragment();
          Double lati =mf.getLatitud() ;
          Double longi =mf.getLongitud() ;

         String latistring = Double.toString(lati);
         String longstring = Double.toString(longi);

        final TextView latitud = (TextView) getView().findViewById(R.id.latitude);
        final TextView longitud = (TextView) getView().findViewById(R.id.longitude);
        latitud.setText(latistring);
        longitud.setText(longstring);*/




        if (map != null){
            map.onCreate(null);
            map.onResume();
            map.getMapAsync(this);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
