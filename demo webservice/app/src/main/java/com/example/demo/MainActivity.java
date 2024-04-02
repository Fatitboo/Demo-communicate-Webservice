package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText etSt1;
    EditText etSt2;
    TextView tvResult;
    Button btnSend;
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        etSt1 = findViewById(R.id.etSt1);
        etSt2 = findViewById(R.id.etSt2);
        tvResult = findViewById(R.id.tvResult);
        btnSend = findViewById(R.id.btnSendData);

        //
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("st1", String.valueOf(etSt1.getText()))
                            .add("st2", String.valueOf(etSt2.getText()))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:3001/api")
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e("Error", e.getLocalizedMessage());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String json = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                result = jsonObject.getString("result");
                            } catch (JSONException e) {
                                Log.e("error: ", e.getLocalizedMessage());
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText(result);
                                }
                            });
                            Log.e("body: ", json);
                        }
                    });
                }catch(Exception e) {
                    Log.e("Error", e.getLocalizedMessage());
                }

            }
        });


    }
}