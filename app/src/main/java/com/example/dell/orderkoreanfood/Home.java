package com.example.dell.orderkoreanfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.orderkoreanfood.Common.Common;
import com.example.dell.orderkoreanfood.Model.Category;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database;
    DatabaseReference category;


    TextView txtFullName;
    ListView list_menu;


    FirebaseListAdapter<Category> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //set name for user
        View  headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullname);
        txtFullName.setText(Common.currentUser.getName());
        //load menu
        list_menu = findViewById(R.id.list_menu);
        loadMenu();
        list_menu.setAdapter(adapter);



        list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = adapter.getItem(position);
                //Get categoryId and send to new activity.
                Intent foodlist = new Intent(Home.this,FoodList.class);

                //Because CategoryId is key , so we just get key of this item
                foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());


                startActivity(foodlist);
            }
        });




    }



    private void loadMenu()
    {
        adapter = new FirebaseListAdapter<Category>(Home.this,Category.class,R.layout.menu_item,category) {
            @Override
            protected void populateView(View v, Category model, int position) {
                ImageView imageView = v.findViewById(R.id.menu_image);
                TextView textView = v.findViewById(R.id.menu_name);
                textView.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(imageView);
            }
        };
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {
            Intent viewOrder  = new Intent(Home.this,ViewOrder.class);
            startActivity(viewOrder);
//            Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
