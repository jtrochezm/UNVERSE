package com.example.unverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroUserActivity extends AppCompatActivity {
    TextView login;
    EditText nombreReg, apellidosReg, correoReg, contrasenaReg, contrasenaReg2;
    Button BtnRegistrar;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_user);
        //metodo para regresar al login
        login(login);

        //inicilizar firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        //inicializar los edittext y el boton
        nombreReg = findViewById(R.id.TextnombreReg);
        apellidosReg = findViewById(R.id.TxtapellidosReg);
        correoReg = findViewById(R.id.editTextCorreoReg);
        contrasenaReg = findViewById(R.id.TextContraseñaReg);
        contrasenaReg2 = findViewById(R.id.TextContrasena2Reg);
        BtnRegistrar = findViewById(R.id.buttonregistrar);

        //boton registrar
        BtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

    }

    //metodo para registrar usuario
    private void registrarUsuario(){
        String nombre = nombreReg.getText().toString();
        String apellidos = apellidosReg.getText().toString();
        String correo = correoReg.getText().toString();
        String contrasena = contrasenaReg.getText().toString();
        String contrasena2 = contrasenaReg2.getText().toString();

        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || contrasena2.isEmpty()){
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }else if (!contrasena.equals(contrasena2)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        String id = mAuth.getCurrentUser().getUid();
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("nombre", nombre);
                        map.put("apellidos", apellidos);
                        map.put("correo", correo);
                        map.put("contrasena", contrasena);

                        mFirestore.collection("usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void> () {

                            @Override
                            public void onSuccess(Void unused) {
                                finish();
                                startActivity(new Intent(RegistroUserActivity.this, LoginUserActivity.class));
                                Toast.makeText(RegistroUserActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistroUserActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthUserCollisionException) {
                            // El correo electrónico ya está en uso por otra cuenta
                            Toast.makeText(RegistroUserActivity.this, "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show();
                        } else {
                            // Ocurrió otro error durante el registro
                            Toast.makeText(RegistroUserActivity.this, "Error al registrar: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistroUserActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    //metododo par regresar al login por medio de un textview
    public void login (TextView login){
        login = findViewById(R.id.txtreslog);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(RegistroUserActivity.this, LoginUserActivity.class);
                RegistroUserActivity.this.startActivity(intentReg);
            }
        });
    }



}