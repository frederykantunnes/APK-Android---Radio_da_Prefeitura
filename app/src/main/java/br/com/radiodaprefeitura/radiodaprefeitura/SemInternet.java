package br.com.radiodaprefeitura.radiodaprefeitura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SemInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_internet);
        setTitle("Rádio da Prefeitura");
    }
}
