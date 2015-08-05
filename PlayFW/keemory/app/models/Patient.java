package models;

/**
 * Created by Kody on 01/08/2015.
 */
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Id;

@Entity
public class Patient extends Model {
    @Id
    private Integer id;
    @JsonIgnore
    @ManyToOne
    private Carer user;
    private String name;
    private String phone;

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
    @Column(columnDefinition = "TEXT")
    private String base64;
    private String type;
    private String url;
    private String CKP;
    private String lat;
    private String lon;


    public static Finder<Integer, Patient> find = new Finder<>(Patient.class);
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCKP() {
        return CKP;
    }

    public void setCKP(String CKP) {
        this.CKP = CKP;
    }
}
