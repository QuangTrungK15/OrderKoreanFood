package com.example.dell.orderkoreanfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.dell.orderkoreanfood.Model.Order;
import com.example.dell.orderkoreanfood.R;
import com.firebase.ui.database.FirebaseListAdapter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


/**
 * Created by dell on 24/04/2018.
 */

public class CartAdapter extends ArrayAdapter<Order>  {

    Activity context;
    int resource;
    List<Order> lvOrder;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row = layoutInflater.inflate(resource,null);


        TextView txt_cart_name = row.findViewById(R.id.cart_item_name);
        TextView txt_price = row.findViewById(R.id.cart_item_price);
        ImageView img_cart_count = row.findViewById(R.id.cart_item_count);


        TextDrawable drawable = TextDrawable.builder().buildRound(""+lvOrder.get(position).getQuantity(), Color.RED);
        img_cart_count.setImageDrawable(drawable);


        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(lvOrder.get(position).getPrice()))*(Integer.parseInt(lvOrder.get(position).getQuantity()));
        txt_price.setText(fmt.format(price));
        txt_cart_name.setText(lvOrder.get(position).getProductName());


        row.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, position, 0, "Delete");//groupId, itemId, order, title
            }
        });


        return row;
    }

    public CartAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.lvOrder = objects;
    }


}
