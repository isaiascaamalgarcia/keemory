package models;

/**
 * Created by Kody on 02/08/2015.
 */
import com.avaje.ebean.Model;
import play.mvc.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;


@Entity
public class Carer extends Model{
    @Id
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String ckc;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    public static List<Patient> patients;

    public static Finder<Integer, Carer> find = new Finder<>(Carer.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCkc() {
        return ckc;
    }

    public void setCkc(String ckc) {
        this.ckc = ckc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addres) {
        this.address = addres;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
