package com.example.basededatosfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Perfil extends AppCompatActivity {
    Button btncerrar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        btncerrar = findViewById(R.id.btncerrar);
        mAuth = FirebaseAuth.getInstance();

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Perfil.this, registrarUsuarios.class));
                finish();
            }
        });
    }
}