package com.ecardero.marvelsuperheroes.splash.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.search.view.activity.SearchActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Espera dos segundos (2000 milisegundos) e inicia la nueva actividad.
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                Intent intentSearch = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                finish();
            }
        }, 2000);
    }
}
