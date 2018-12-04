package models;

import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class Monument extends Model {
    public String name;
    public double lat;
    public double lon;
    public String cat;
    public String param;
    public String description;
    public String imageurl;

    @ManyToOne
    public City city;

    public Monument (String name, double lat, double lon, String cat, String param, String description, String imageurl, City city){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.cat = cat;
        this.param = param;
        this.description = description;
        this.imageurl = imageurl;
        this.city = city;
    }
}