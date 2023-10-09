package com.example.unverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUserActivity extends AppCompatActivity {
    TextView regis;
    EditText TxtEmail, Txtcontrasena;
    Button botonIngresar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        this.setTitle("Login");

        mAuth = FirebaseAuth.getInstance();

        //inicializar los edittext y el boton

        TxtEmail = findViewById(R.id.TexEmail);
        Txtcontrasena = findViewById(R.id.TxtContrasena);
        botonIngresar = findViewById(R.id.buttonIngresar);

        //boton ingresar
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        //ir a la activity de registro por medio del textview
        registro(regis);


    }
    public void registro (TextView regis){
        regis = findViewById(R.id.resgist);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(LoginUserActivity.this, RegistroUserActivity.class);
                LoginUserActivity.this.startActivity(intentReg);
            }
        });
    }

    //metodo para validar los campos de email y contraseña con firebase auth
    private void loginUser(){
        String email = TxtEmail.getText().toString();
        String password = Txtcontrasena.getText().toString();

        if(TextUtils.isEmpty(email)){
            TxtEmail.setError("Email vacio.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            Txtcontrasena.setError("Password vacio.");
            return;
        }
        //autenticacion con firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            //si el usuario se loguea correctamente
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginUserActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomePrincipalActivity.class));
                }else{
                    Toast.makeText(LoginUserActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //metodo para validar si el usuario ya esta logueado
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(getApplicationContext(), HomePrincipalActivity.class));
        }
    }

}