package ru.test.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
     public TextView textView;

     public String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int cacheSize = 10 * 1024 * 1024;

        File cacheDirectory = new File("src/test/resources/cache");
        Cache cache = new Cache(cacheDirectory, cacheSize);


        textView = (TextView) findViewById(R.id.textView);
//
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .cache(cache)
                .build();
        //в formBody кладём параметры запроса
        RequestBody formBody = new FormBody.Builder()
//                .add("message", "Your message")
                .build();

        final Request request = new Request.Builder()
                .url("https://www.google.ru/")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                str = response.toString();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        textView.setText(str);
                    }
                });
            }
        });


    }
}
