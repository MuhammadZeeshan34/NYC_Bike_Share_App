package dto;

/**
 * Created by Engr. ZeeShaN on 2/9/2017.
 */
public class NearestStationDetailsDTO implements Comparable<NearestStationDetailsDTO> {

    protected long stationId;
    protected double latitude;
    protected double longitude;
    protected double distance;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int compareTo(NearestStationDetailsDTO object) {
        if(this.distance < object.getDistance())
            return -1;
        else
            return 1;
    }
}
