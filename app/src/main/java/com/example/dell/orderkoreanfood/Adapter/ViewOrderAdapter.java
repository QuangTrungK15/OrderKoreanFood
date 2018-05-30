package com.example.dell.orderkoreanfood.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.dell.orderkoreanfood.Model.Request;
import com.example.dell.orderkoreanfood.R;

import java.util.List;

public class ViewOrderAdapter extends ArrayAdapter<Request> {
    Activity activity;
    int resource;
    List<Request> requestList;

    public ViewOrderAdapter(@NonNull Activity context, int resource, @NonNull List<Request> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.requestList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.activity.getLayoutInflater();
        View row = layoutInflater.inflate(resource,null);

        TextView txtOrderphone = row.findViewById(R.id.txtOrderPhone);
        TextView txtOrderAddress = row.findViewById(R.id.txtOrderAddress);

        txtOrderphone.setText(requestList.get(position).getPhone().toString());
        txtOrderAddress.setText(requestList.get(position).getAddress().toString());



        return row;
    }
}
