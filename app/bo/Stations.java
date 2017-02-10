package bo;

import dto.StationsDTO;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Engr. ZeeShaN on 2/8/2017.
 */

@Entity
public class Stations implements Serializable {


    @Id
    @Column(name = "station_id")
    private long stationId;
    @Column(name = "name", nullable = false)
    private String stationName;
    @Column(name = "short_name", nullable = false)
    private String shortName;
    @Column(name = "lat", nullable = false)
    private float latitude;
    @Column(name = "lon", nullable = false)
    private float longitude;
    @Column(name = "region_id", nullable = false)
    private long regionId;
    @Column(name = "rental_methods", nullable = false)
    private String rentalMethods;
    @Column(name = "capacity", nullable = false)
    private long capacity;
    @Column(name = "eightd_has_key_dispenser", nullable = false)
    private boolean eightdHasKeyDispenser;




    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRentalMethods() {
        return rentalMethods;
    }

    public void setRentalMethods(String rentalMethods) {
        this.rentalMethods = rentalMethods;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public boolean getEightdHasKeyDispenser() {
        return eightdHasKeyDispenser;
    }

    public void setEightdHasKeyDispenser(boolean eightdHasKeyDispenser) {
        this.eightdHasKeyDispenser = eightdHasKeyDispenser;
    }

    public Stations StationsDTO2BO(StationsDTO data)
    {

        this.capacity = data.getCapacity();
        this.eightdHasKeyDispenser =data.isEightd_has_key_dispenser ();
        this.latitude =data.getLat();
        this.longitude = data.getLon();
        this.stationId = data.getStation_id();
        this.regionId = data.getRegion_id();
        this.stationName = data.getName();
        this.shortName = data.getShort_name();
        this.rentalMethods = data.getRental_methods();

        return this;
    }

}
