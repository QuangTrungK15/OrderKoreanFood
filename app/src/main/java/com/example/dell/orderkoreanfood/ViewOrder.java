package com.example.dell.orderkoreanfood;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.orderkoreanfood.Adapter.ViewOrderAdapter;
import com.example.dell.orderkoreanfood.Common.Common;
import com.example.dell.orderkoreanfood.Model.Request;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewOrder extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference requests;

    ListView lvOrderView;


    List<Request> requestList;
    FirebaseListAdapter<Request> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        lvOrderView = findViewById(R.id.lvOrderView);
        String phone = Common.currentUser.getPhone();


        //Toast.makeText(ViewOrder.this, ""+requests.orderByChild("phone").equalTo(phone), Toast.LENGTH_SHORT).show();


        //String phone = Common.currentUser.getPhone();

        adapter = new FirebaseListAdapter<Request>(ViewOrder.this,Request.class,R.layout.view_order_layout, requests.orderByChild("phone").equalTo(phone)) {
            @Override
            protected void populateView(View v, Request model, final int position) {
                TextView txtID = v.findViewById(R.id.txtOrderId);
                TextView txtStatus = v.findViewById(R.id.txtOrderStatus);
                TextView txtOrderphone = v.findViewById(R.id.txtOrderPhone);
                TextView txtOrderAddress = v.findViewById(R.id.txtOrderAddress);
                txtID.setText(adapter.getRef(position).getKey());
                txtStatus.setText(convertToStatus(model.getStatus()));
                txtOrderphone.setText(model.getPhone());
                txtOrderAddress.setText(model.getAddress());


                v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.setHeaderTitle("Select The Action");
                        menu.add(0, position, 0, Common.DELETE);//groupId, itemId, order, title
                    }
                });

            }
        };

        lvOrderView.setAdapter(adapter);


    }


    @Override
    public boolean onContextItemSelected(final MenuItem item) {


        if(item.getTitle().equals(Common.DELETE)) {


            AlertDialog.Builder altertDialog = new AlertDialog.Builder(ViewOrder.this);
            altertDialog.setTitle("Delete Order");
            altertDialog.setMessage("Are you sure to want delete your order ?");


            altertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    deleteMyOrder(adapter.getRef(item.getItemId()).getKey());

                    Toast.makeText(ViewOrder.this,"Deleted",Toast.LENGTH_SHORT).show();
                }
            });
            altertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            altertDialog.show();
        }

        return true;
    }

    private void deleteMyOrder(String key) {
        requests.child(key).removeValue();
    }

    private String convertToStatus(String status) {

        if(status.equals("0"))
            return "Placed";
        if(status.equals("1"))
            return "On my way";
        if(status.equals("2"))
            return "Shipped";
        return null;
    }


}
