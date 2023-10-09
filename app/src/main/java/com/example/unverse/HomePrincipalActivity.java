package com.example.unverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomePrincipalActivity extends AppCompatActivity {

    TextView cerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_principal);

        //inicializar variables
        cerrarSesion = findViewById(R.id.txtcerrarsesion);

        cerrarSesion(cerrarSesion);
    }

    public void cerrarSesion (TextView cerrarSesion){
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intentCerrarSesion = new Intent(HomePrincipalActivity.this, LoginUserActivity.class);
                HomePrincipalActivity.this.startActivity(intentCerrarSesion);
                finish();
            }
        });
    }
}