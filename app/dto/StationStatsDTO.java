package dto;

import org.json.JSONObject;

import javax.annotation.Nullable;
import javax.persistence.Column;

/**
 * Created by Engr. ZeeShaN on 2/9/2017.
 */
public class StationStatsDTO {

    protected long stationId;
    protected long numBikesAvailable;
    protected long numBikesDisabled;
    protected long numDocksAvailable;
    protected long numDocksDisabled;
    protected long isInstalled;
    protected long isRenting;
    protected long isReturning;
    protected long lastReported;
    protected boolean eightdHasAvailableKeys;

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public long getNumBikesAvailable() {
        return numBikesAvailable;
    }

    public void setNumBikesAvailable(long numBikesAvailable) {
        this.numBikesAvailable = numBikesAvailable;
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

    public long getIsRenting() {
        return isRenting;
    }

    public void setIsRenting(long isRenting) {
        this.isRenting = isRenting;
    }

    public long getIsReturning() {
        return isReturning;
    }

    public void setIsReturning(long isReturning) {
        this.isReturning = isReturning;
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


    public StationStatsDTO SetStationDTOObject(JSONObject data)
    {

        this.isInstalled = Long.valueOf(data.get("is_installed").toString());
        this.numBikesAvailable = Long.valueOf(data.get("num_bikes_available").toString());
        this.numBikesDisabled = Long.valueOf(data.get("num_bikes_disabled").toString());
        this.numDocksAvailable = Long.valueOf(data.get("num_docks_available").toString());
        this.stationId = Integer.valueOf(data.get("station_id").toString());
        this.numDocksDisabled = Long.valueOf(data.get("num_docks_disabled").toString());
        this.isRenting = Long.valueOf(data.get("is_renting").toString());
        this.isReturning = Long.valueOf(data.get("is_returning").toString());
        if(data.get("last_reported").toString() != "null") {
            this.lastReported = Long.valueOf(data.get("last_reported").toString());
        }
        else
        {
            this.lastReported = 0;
        }
        this.eightdHasAvailableKeys = Boolean.valueOf(data.get("eightd_has_available_keys").toString());

        return this;
    }

}
