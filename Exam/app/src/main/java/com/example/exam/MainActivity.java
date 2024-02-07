package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private double usd=0.0;
    private double eur =0.0;
    private double mad=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue mQueue = Volley.newRequestQueue(this);
        TextView result = (TextView) findViewById(R.id.result);
        Button btnUsd = findViewById(R.id.btnUsd);
        Button btnEur = findViewById(R.id.btnEur);
        Button btnMad = findViewById(R.id.btnMad);

        Spinner cryptoSpinner = findViewById(R.id.cryptoSpinner);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.crypto_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cryptoSpinner.setAdapter(adapter);


        cryptoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long rowId) {
                JsonObjectRequest request = new JsonObjectRequest("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + cryptoName(cryptoSpinner.getSelectedItem().toString()) + ".json", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(cryptoName(cryptoSpinner.getSelectedItem().toString()));

                            usd= jsonObject.getDouble("usd");
                            eur= jsonObject.getDouble("eur");
                            mad= jsonObject.getDouble("mad");


                        } catch (Exception e) {
                            result.setText("error");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                mQueue.add(request);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        btnUsd.setOnClickListener(view -> {
            result.setText(usd + "");
        });
        btnEur.setOnClickListener(view ->{
            result.setText(eur + "");
        });
        btnMad.setOnClickListener(View ->{
            result.setText(mad +"");
        });
    }

    public String cryptoName (String name){
        if (Objects.equals(name, "Bitcoin")){
            return "btc";
        } else if (Objects.equals(name, "Ethereum")) {
            return "eth";

        } else if (Objects.equals(name, "Ripple")) {
            return "rpl";


        }else if (Objects.equals(name, "litecoin")){
            return "ltc";
        }
        else
            return "btc";
    }
}