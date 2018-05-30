package com.example.dell.orderkoreanfood.Model;

import java.util.List;

/**
 * Created by dell on 22/04/2018.
 */

public class Request {

    private String  phone;
    private String  name;
    private String  address;
    private String  total;
    private String  status;
    private List<Order> foods; //list of food order


    public Request()
    {

    }



    public Request(String phone, String name, String address, String total, List<Order> foods) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status = "0";
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }


    public String getStatus() {
        return status;
    }
}
