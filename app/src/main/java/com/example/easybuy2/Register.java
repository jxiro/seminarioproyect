package com.example.easybuy2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_surname, et_email, et_password, et_nickname, et_address, et_cellphone;
    Button btn_reg_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //asociacion de las variables
        et_name = findViewById(R.id.reg_name);
        et_surname = findViewById(R.id.reg_surname);
        et_email = findViewById(R.id.reg_email);
        et_password = findViewById(R.id.reg_password);
        //et_nickname = findViewById(R.id.reg_nickname);
        et_address = findViewById(R.id.reg_address);
        et_cellphone = findViewById(R.id.reg_cellphone);
        btn_reg_new = findViewById(R.id.register_new);
        btn_reg_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(Register.this, "please wait", Toast.LENGTH_LONG).show();
        btn_reg_new = findViewById(R.id.register_new);
        btn_reg_new.setOnClickListener(this);

        sendData();
    }

    private void sendData(){
        EditText et_name, et_surname, et_email, et_password, et_nickname, et_address, et_cellphone;

        //asociacion de las variables
        et_name = findViewById(R.id.reg_name);
        et_surname = findViewById(R.id.reg_surname);
        et_email = findViewById(R.id.reg_email);
        et_password = findViewById(R.id.reg_password);
        //et_nickname = findViewById(R.id.reg_nickname);
        et_address = findViewById(R.id.reg_address);
        et_cellphone = findViewById(R.id.reg_cellphone);



       final AsyncHttpClient client = new AsyncHttpClient();
       RequestParams params = new RequestParams();


        params.add( "email", et_email.getText().toString());
        params.add( "password", et_password.getText().toString());
        params.add( "name", et_name.getText().toString());
        params.add( "surname", et_surname.getText().toString());
        //params.add("nickname", et_nickname.getText().toString());
        params.add( "address", et_address.getText().toString());
        params.add("phone", et_cellphone.getText().toString());


        Toast.makeText(Register.this, "init send data", Toast.LENGTH_LONG).show();

        client.post(Utils.REGISTER_USER,params, new JsonHttpResponseHandler(){

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(Register.this, "on success", Toast.LENGTH_LONG).show();
                if  (response.has("rols")){
                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();

                    JSONObject jsonobject = null;
                    try {
                        jsonobject = new JSONObject(String.valueOf(response));
                        String id = jsonobject.getString("_id");
                        Utils.MY_ID=id;
                        Intent activity = new Intent(Register.this, MainActivity.class);
                        Toast.makeText(Register.this, "id:"+ Utils.MY_ID, Toast.LENGTH_LONG).show();
                        startActivity(activity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Intent activity = new Intent(Register.this, MainActivity.class);
                    Toast.makeText(Register.this, "id:"+ Utils.MY_ID, Toast.LENGTH_LONG).show();
                    //String id = null;
                    //activity.putExtra("my_id", id);

                }
                else
                    Toast.makeText(Register.this, "not Successfully Registered", Toast.LENGTH_LONG).show();
            }


           /* public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            }*/



        });


    }
}
