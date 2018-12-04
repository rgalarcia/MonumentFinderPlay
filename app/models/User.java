package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class User extends Model {
    public String username;
    public String email;
    public String password;
    public Integer mobil;
    public String gustos;

    @OneToMany
    public List<Monument> lmonuments = new ArrayList<Monument>();

    public User (String username, String email, String password, Integer mobil, String gust){
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobil = mobil;
        this.gustos = gust;
    }
}