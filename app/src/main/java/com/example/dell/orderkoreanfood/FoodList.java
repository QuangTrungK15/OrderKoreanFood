package com.example.dell.orderkoreanfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.orderkoreanfood.Model.Food;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodList extends AppCompatActivity {


    //Create database
    FirebaseDatabase database;
    DatabaseReference food_list;


    String categoryId="";

    ListView list_food;


    FirebaseListAdapter<Food> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        database = FirebaseDatabase.getInstance();
        food_list = database.getReference("Foods");
        list_food = findViewById(R.id.list_food);


        //Get Intent here

        if(getIntent()!=null)
        {
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if(!categoryId.isEmpty() && categoryId!=null)
        {
            loadListFood(categoryId);
        }


        list_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = adapter.getItem(position);

                //Get FoodId and send to new activity.
                Intent fooddetail = new Intent(FoodList.this,FoodDetail.class);
                fooddetail.putExtra("FoodId",adapter.getRef(position).getKey());
                startActivity(fooddetail);

            }
        });




    }

    private void loadListFood(String categoryId) {


        adapter = new FirebaseListAdapter<Food>(this,
                Food.class,
                R.layout.food_item,
                food_list.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateView(View v, Food model, int position) {
                TextView textView = v.findViewById(R.id.food_name);
                ImageView imageView = v.findViewById(R.id.food_image);

                textView.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(imageView);


            }
        };
        list_food.setAdapter(adapter);
    }
}
