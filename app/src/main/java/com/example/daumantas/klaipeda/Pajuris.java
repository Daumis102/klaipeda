package com.example.daumantas.klaipeda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Pajuris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pajuris);
        Button melnarage = (Button)findViewById(R.id.melnarage);
        Button giruliai = (Button)findViewById(R.id.giruliai);
        Button palanga = (Button)findViewById(R.id.palanga);
        Button nida = (Button)findViewById(R.id.nida);
        Button smiltyne = (Button)findViewById(R.id.smiltyne);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pajuris.this, PajurioInfo.class);
                switch (v.getId()){
                    case R.id.melnarage:
                        intent.putExtra("pajuris","melnarage");
                        break;
                    case R.id.giruliai:
                        intent.putExtra("pajuris","giruliai");
                        break;
                    case R.id.palanga:
                        intent.putExtra("pajuris","palanga");
                        break;
                    case R.id.nida:
                        intent.putExtra("pajuris","nida");
                        break;
                    case R.id.smiltyne:
                        intent.putExtra("pajuris","smiltyne");
                        break;

                }
                startActivity(intent);
            }
        };

        melnarage.setOnClickListener(listener);
        giruliai.setOnClickListener(listener);
        palanga.setOnClickListener(listener);
        nida.setOnClickListener(listener);
        smiltyne.setOnClickListener(listener);
    }
}
