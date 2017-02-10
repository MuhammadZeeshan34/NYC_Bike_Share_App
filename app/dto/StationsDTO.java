package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Engr. ZeeShaN on 2/8/2017.
 */
public class StationsDTO {


    protected int station_id;
    protected String name;
    protected String short_name;
    protected float lat;
    protected float lon;
    protected int region_id;
    @JsonIgnore
    protected String rental_methods;
    protected int capacity;
    protected boolean eightd_has_key_dispenser;

    public StationsDTO() {
        super();
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public boolean isEightd_has_key_dispenser() {
        return eightd_has_key_dispenser;
    }

    public void setEightd_has_key_dispenser(boolean eightd_has_key_dispenser) {
        this.eightd_has_key_dispenser = eightd_has_key_dispenser;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRental_methods() {
        return rental_methods;
    }

    public void setRental_methods(String rental_methods) {
        this.rental_methods = rental_methods;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public StationsDTO SetStationDTOObject(JSONObject data)
    {

        this.capacity = (int)data.get("capacity");
        this.eightd_has_key_dispenser = (boolean)data.get("eightd_has_key_dispenser");
        this.lat = Float.valueOf(data.get("lat").toString());
        this.lon = Float.valueOf(data.get("lon").toString());
        this.station_id = Integer.valueOf(data.get("station_id").toString());
        this.region_id = Integer.valueOf(data.get("region_id").toString());
        this.name = String.valueOf(data.get("name"));
        this.short_name = String.valueOf(data.get("short_name"));
        this.rental_methods = String.valueOf(data.get("rental_methods"));

        return this;
    }

}
