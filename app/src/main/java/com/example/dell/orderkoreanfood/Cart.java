package com.example.dell.orderkoreanfood;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.orderkoreanfood.Adapter.CartAdapter;
import com.example.dell.orderkoreanfood.Common.Common;
import com.example.dell.orderkoreanfood.Database.Database;
import com.example.dell.orderkoreanfood.Model.Order;
import com.example.dell.orderkoreanfood.Model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference requests;


    TextView txtTotalPrice;
    FButton btnPlace;

    ListView lvOrderDetail;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        lvOrderDetail = findViewById(R.id.listCart);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Database(Cart.this).cleanCart();
                showAlterDialog();
            }

        });

        loadListFood();

        //Register
        registerForContextMenu(lvOrderDetail);



    }



    private void showAlterDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address");

        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );



                //Submit to firebase
                //We will using System.CurrentMilli to key
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                //delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you",Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alertDialog.show();



    }


    private void loadListFood() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(Cart.this,R.layout.cart_layout,cart);
        adapter.notifyDataSetChanged();
        lvOrderDetail.setAdapter(adapter);


        //Caculate total price
        int total = 0;
        for(Order order:cart)
        {
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

            //Set format currency US
            Locale locale = new Locale("en","US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            txtTotalPrice.setText(fmt.format(total));

        }


    }





    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Delete")) {
            deleteCart(item.getItemId());
            loadListFood();
            //Toast.makeText(this,""+item.getItemId(),Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void deleteCart(int itemId) {
        //we will remove item at List<Order> by position
        cart.remove(itemId);
        //After that , we will delete all old data from SQLite
        new Database(this).cleanCart();
        //And final , we will update new data from List<Order> to SQLite
        for(Order item:cart)
           new Database(this).addToCart(item);

    }






}
