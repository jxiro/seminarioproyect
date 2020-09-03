package com.example.easybuy2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easybuy2.AddHome;
import com.example.easybuy2.R;
import com.example.easybuy2.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewHome extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewHome.
     */
    // TODO: Rename and change types and number of parameters
    public static NewHome newInstance(String param1, String param2) {
        NewHome fragment = new NewHome();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment





        return inflater.inflate(R.layout.fragment_new_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Toast.makeText(getActivity(), "ID:"+ Utils.MY_ID, Toast.LENGTH_LONG).show();

        /*Intent returnIntent = new Intent();
        final String lat_q_llega = "";
        final String long_q_llega = "";
        returnIntent.putExtra("latitudO", lat_q_llega);
        returnIntent.putExtra("longitudO", long_q_llega);*/

            Button btn_reg_new;
            btn_reg_new = getView().findViewById(R.id.register_new_home);
            btn_reg_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendData1();
                }

                private void sendData1() {

                    //asociacion de las variables

                    TextView tipo = getView().findViewById(R.id.tipo);
                    TextView oferta = getView().findViewById(R.id.oferta);
                    TextView estado = getView().findViewById(R.id.estado);
                    TextView ciudad = getView().findViewById(R.id.ciudad);
                    TextView zona = getView().findViewById(R.id.zona);
                    TextView direccion = getView().findViewById(R.id.direccion);
                    TextView precio = getView().findViewById(R.id.precio);
                    TextView descripcion = getView().findViewById(R.id.desc_casa);
                    TextView supterreno = getView().findViewById(R.id.supTerreno);
                    TextView servicios = getView().findViewById(R.id.servicios);
                    TextView numDormitorios = getView().findViewById(R.id.dormitorios);
                    TextView banos = getView().findViewById(R.id.banos);
                    TextView pisos = getView().findViewById(R.id.pisos);
                    TextView piscina = getView().findViewById(R.id.piscina);
                    TextView garaje = getView().findViewById(R.id.garaje);
                    TextView amoblado = getView().findViewById(R.id.amoblado);
                    TextView year = getView().findViewById(R.id.ano);

                    AsyncHttpClient clienth = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    String id = Utils.MY_ID;
                    params.add( "idVendedor", id);
                    params.add( "tipo", tipo.getText().toString());
                    params.add( "oferta", oferta.getText().toString());
                    params.add( "estado", estado.getText().toString());
                    params.add( "ciudad", ciudad.getText().toString());
                    params.add( "zona", zona.getText().toString());
                    params.add( "direccion", direccion.getText().toString());
                    params.add( "precio", precio.getText().toString());
                    params.add( "descripcion", descripcion.getText().toString());
                    params.add( "supTerreno", supterreno.getText().toString());
                    params.add( "servicios", servicios.getText().toString());
                    params.add( "numDormitorios", numDormitorios.getText().toString());
                    params.add( "numBanos", banos.getText().toString());
                    params.add( "numPisos", pisos.getText().toString());
                    params.add( "piscina", piscina.getText().toString());
                    params.add( "capGaraje", garaje.getText().toString());
                    params.add( "amoblado", amoblado.getText().toString());
                    params.add( "yearCOns", year.getText().toString());
                    params.add( "latitud", AddHome.latitude);
                    params.add( "longitud", AddHome.longitude);

                    Toast.makeText(getActivity(), "init send data", Toast.LENGTH_LONG).show();

                    clienth.post(Utils.REGISTER_HOME ,params, new JsonHttpResponseHandler(){

                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            JSONObject jsonobject = null;
                            try {
                                jsonobject = new JSONObject(String.valueOf(response));
                                String id = jsonobject.getString("id");
                                Utils.MY_ID_HOME=id;

                                Toast.makeText(getContext(), "id:"+ Utils.MY_ID_HOME, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }


           /* public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            }*/



                    });
                }
            });


    }

    private void sendData() {

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
