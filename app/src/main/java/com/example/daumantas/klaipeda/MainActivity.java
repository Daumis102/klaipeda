package com.example.daumantas.klaipeda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pajuris = (Button)findViewById(R.id.pajuris);
        Button kultura = (Button)findViewById(R.id.kultura);
        Button isalkau = (Button)findViewById(R.id.isalkau);
        Button pramogos = (Button)findViewById(R.id.pramogos);
        Button login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImoniuLogin.class);
                startActivity(intent);

            }
        });

        pajuris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pajuris.class);
                startActivity(intent);

            }
        });

        kultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Kultura.class);
                startActivity(intent);

            }
        });

        isalkau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Isalkau.class);
                startActivity(intent);

            }
        });

        pramogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pramogos.class);
                startActivity(intent);

            }
        });
    }
}
