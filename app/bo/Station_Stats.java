package bo;

import dto.StationStatsDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Engr. ZeeShaN on 2/9/2017.
 */
@Entity
public class Station_Stats implements Serializable {


    @Id
    @Column(name = "station_id")
    private long stationId;
    @Column(name = "num_bikes_available", nullable = false)
    private long numBikesAvailable;
    @Column(name = "num_bikes_disabled", nullable = false)
    private long numBikesDisabled;
    @Column(name = "num_docks_available", nullable = false)
    private long numDocksAvailable;
    @Column(name = "num_docks_disabled", nullable = false)
    private long numDocksDisabled;
    @Column(name = "is_installed", nullable = false)
    private long isInstalled;
    @Column(name = "is_renting", nullable = false)
    private long isRenting;
    @Column(name = "is_returning", nullable = false)
    private long isReturning;
    @Column(name = "last_reported", nullable = false)
    private long lastReported;
    @Column(name = "eightd_has_available_keys", nullable = false)
    private boolean eightdHasAvailableKeys;

    public long getNumBikesAvailable() {
        return numBikesAvailable;
    }

    public void setNumBikesAvailable(long numBikesAvailable) {
        this.numBikesAvailable = numBikesAvailable;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public long getNumBikesDisabled() {
        return numBikesDisabled;
    }

    public void setNumBikesDisabled(long numBikesDisabled) {
        this.numBikesDisabled = numBikesDisabled;
    }

    public long getNumDocksAvailable() {
        return numDocksAvailable;
    }

    public void setNumDocksAvailable(long numDocksAvailable) {
        this.numDocksAvailable = numDocksAvailable;
    }

    public long getNumDocksDisabled() {
        return numDocksDisabled;
    }

    public void setNumDocksDisabled(long numDocksDisabled) {
        this.numDocksDisabled = numDocksDisabled;
    }

    public long getIsInstalled() {
        return isInstalled;
    }

    public void setIsInstalled(long isInstalled) {
        this.isInstalled = isInstalled;
    }

    public long getIsReturning() {
        return isReturning;
    }

    public void setIsReturning(long isReturning) {
        this.isReturning = isReturning;
    }

    public long getIsRenting() {
        return isRenting;
    }

    public void setIsRenting(long isRenting) {
        this.isRenting = isRenting;
    }

    public long getLastReported() {
        return lastReported;
    }

    public void setLastReported(long lastReported) {
        this.lastReported = lastReported;
    }

    public boolean isEightdHasAvailableKeys() {
        return eightdHasAvailableKeys;
    }

    public void setEightdHasAvailableKeys(boolean eightdHasAvailableKeys) {
        this.eightdHasAvailableKeys = eightdHasAvailableKeys;
    }


    public Station_Stats StationStatsDTO2BO(StationStatsDTO data)
    {

        this.stationId = data.getStationId();
        this.eightdHasAvailableKeys =data.isEightdHasAvailableKeys ();
        this.isRenting =data.getIsRenting();
        this.isReturning = data.getIsReturning();
        this.isInstalled = data.getIsInstalled();
        this.numBikesAvailable = data.getNumBikesAvailable();
        this.numBikesDisabled = data.getNumBikesDisabled();
        this.numDocksAvailable = data.getNumDocksAvailable();
        this.numDocksDisabled = data.getNumDocksDisabled();
        this.lastReported = data.getLastReported();

        return this;
    }

}
