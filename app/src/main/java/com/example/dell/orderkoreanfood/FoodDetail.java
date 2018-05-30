package com.example.dell.orderkoreanfood;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.dell.orderkoreanfood.Database.Database;
import com.example.dell.orderkoreanfood.Model.Food;
import com.example.dell.orderkoreanfood.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;


    String foodID="";


    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //init view
        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodID,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount())
                );

                Toast.makeText(FoodDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });



        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);
        collapsingToolbarLayout = findViewById(R.id.collapsing);


        //Get Food Id from Intent
        if(getIntent()!=null)
        {
            foodID = getIntent().getStringExtra("FoodId");
        }
        if(!foodID.isEmpty())
        {
            getDetailFood(foodID);
        }
    }

    private void getDetailFood(String foodID) {
        //attach event addValueEventListener , let's get data from database.
        foods.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                //set image
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());

                food_name.setText(currentFood.getName());

                food_description.setText(currentFood.getDescripition());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
