package com.example.easybuy2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.easybuy2.Collection.Item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Button btn_register, btn_reg_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //llamar al otro activity register
        btn_register = findViewById(R.id.buttonRegister);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent_reg = new Intent(MainActivity.this, Register.class);
               MainActivity.this.startActivity(intent_reg);


            }

        });
        btn_reg_new = findViewById(R.id.buttonLogin);
        btn_reg_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendLogin();


            }
        });
    }
    private void sendLogin(){
        final EditText email =  findViewById(R.id.login_email);
        EditText password =  findViewById(R.id.login_pass);
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add( "email", email.getText().toString());
        params.add( "password", password.getText().toString());
        Toast.makeText(MainActivity.this, "Send login", Toast.LENGTH_LONG).show();
        client.post(Utils.LOGIN_SERVICE,params, new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if  (response.has("token")){
                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();

                    JSONObject jsonobject= null;
                    try {
                        jsonobject = new JSONObject(String.valueOf(response));
                        String token = jsonobject.getString("id");

                        Utils.MY_ID=token;
                        //JWT jwt = new JWT("token" );
                        Intent intent = new Intent(MainActivity.this, Start.class);
                        //intent.putExtra("jwt", jwt);
                        //Utils.MY_TOKEN=parsedValue;

                        Toast.makeText(MainActivity.this, "ID:"+ Utils.MY_ID, Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
                else
                    Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_LONG).show();
            }

        });

    }
}
