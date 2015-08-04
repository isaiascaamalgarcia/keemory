package com.example.bitware.keemory;

/**
 * Created by Izzy-Izumi on 03/08/2015.
 */
public class Patient {
    int id;
    String name;
    String address;
    String phone;
    String base64;
    String ckp;
    String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addres) {
        this.address = addres;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCkp() {
        return ckp;
    }

    public void setCkp(String ckp) {
        this.ckp = ckp;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
