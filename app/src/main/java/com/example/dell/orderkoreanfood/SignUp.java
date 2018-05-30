package com.example.dell.orderkoreanfood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.dell.orderkoreanfood.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignUp extends AppCompatActivity {


    MaterialEditText editPhone,editName, editPass;
    FButton btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = findViewById(R.id.editName);
        editPass = findViewById(R.id.editPass);
        editPhone = findViewById(R.id.editPhone);

        btnSignUp = findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Please waiting... ");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number already register ", Toast.LENGTH_SHORT).show();


                        } else {
                            progressDialog.dismiss();
                            //get value to elements
                            User user = new User(editName.getText().toString(),editPass.getText().toString());
                            //Sign up user account
                            table_user.child(editPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successfull ! ", Toast.LENGTH_SHORT).show();
                            finish();

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


    }



}
