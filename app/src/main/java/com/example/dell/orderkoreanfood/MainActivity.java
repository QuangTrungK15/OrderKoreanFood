package com.example.dell.orderkoreanfood;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button btnSignIn, btnSignUp;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();


    }

    private void addEvents() {


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn  = new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUp  = new Intent(MainActivity.this,SignUp.class);
                startActivity(SignUp);

            }
        });


    }

    private void addControls() {
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        txtSlogan = findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"font/NABILA.TTF");
        txtSlogan.setTypeface(face);
    }
}
