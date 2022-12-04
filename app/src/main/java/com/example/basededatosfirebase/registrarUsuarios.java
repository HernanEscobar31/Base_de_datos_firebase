package com.example.basededatosfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registrarUsuarios extends AppCompatActivity {
    Button btn_cuenta, btn_login;
    EditText email, password;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_registrar_usuarios);
        btn_cuenta = findViewById(R.id.btn_cuenta);
        btn_login= findViewById(R.id.btn_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        btn_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registrarUsuarios.this, MainActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if(emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(registrarUsuarios.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginUser(emailUser, passUser);

                }
            }
        });
    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 finish();
                 startActivity(new Intent(registrarUsuarios.this, Perfil.class));
                 Toast.makeText(registrarUsuarios.this, "Bienvenido", Toast.LENGTH_SHORT).show();

             }
             else{
                 Toast.makeText(registrarUsuarios.this, "Error", Toast.LENGTH_SHORT).show();
             }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registrarUsuarios.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}