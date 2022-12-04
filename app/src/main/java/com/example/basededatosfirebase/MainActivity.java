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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText nombre, correo, contraseña;
    Button btn_reg;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nameUser = "";
    private String coUser = "";
    private String passUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        btn_reg = findViewById(R.id.btn_reg);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameUser=nombre.getText().toString();
                coUser=correo.getText().toString();
                passUser=contraseña.getText().toString();

                if (nameUser.isEmpty() && coUser.isEmpty() && passUser.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario();

                }
            }
        });

    }
    private void registrarUsuario(){
        mAuth.createUserWithEmailAndPassword(coUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nameUser);
                    map.put("correo", coUser);
                    map.put("contraseña", passUser);
                    String id= mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Registrado con exito", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, registrarUsuarios.class));
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(MainActivity.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
