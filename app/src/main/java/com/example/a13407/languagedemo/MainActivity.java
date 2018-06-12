package com.example.a13407.languagedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        final String zh = Locale.CHINESE.getLanguage();
        final String en = Locale.ENGLISH.getLanguage();
        final String code = mSharedPreferences.getString("languageCode", zh);

        if (!getSystemLanguage().equals(code)) {
            Log.i(TAG, "onCreate: " + code + "    " + getSystemLanguage());
             changeLanguage(code);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.equals(zh)) {
                    changeLanguage(en);
                } else {
                    changeLanguage(zh);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private String getSystemLanguage() {
        Locale locale = null;
        //兼容8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }

        return locale.getLanguage();
    }

    private void changeLanguage(String code) {
        mEditor.putString("languageCode", code).commit();
        Log.i(TAG, "changeLanguage: " + code);
        Locale locale = null;
        locale = new Locale(code);


        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        Toast.makeText(this, "更換中.....", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
