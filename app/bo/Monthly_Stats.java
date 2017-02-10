package bo;

import dto.StationsDTO;
import play.db.jpa.JPA;
import play.mvc.Result;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Engr. ZeeShaN on 2/10/2017.
 */
@Entity
public class Monthly_Stats implements Serializable {

    @Id
    @Column(name = "station_id")
    private long stationId;

    @Id
    @Column(name = "month")
    private long month;

    @Column(name = "update_count", nullable = false)
    private long updateCount;

    @Column(name = "bike_rides_count", nullable = false)
    private long bikeRidesCount;

    @Column(name = "disable_bikes_count", nullable = false)
    private long disableBikesCount;

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(long updateCount) {
        this.updateCount = updateCount;
    }

    public long getBikeRidesCount() {
        return bikeRidesCount;
    }

    public void setBikeRidesCount(long bikeRidesCount) {
        this.bikeRidesCount = bikeRidesCount;
    }

    public long getDisableBikesCount() {
        return disableBikesCount;
    }

    public void setDisableBikesCount(long disableBikesCount) {
        this.disableBikesCount = disableBikesCount;
    }

    public void InitializeMonthlyStats(StationsDTO stationsDTO)
    {
        Query query = JPA.em().createQuery("SELECT n FROM Monthly_Stats n WHERE n.stationId = :stationid")
                        .setParameter("stationid",Long.valueOf(stationsDTO.getStation_id()));
        List<Monthly_Stats> res = query.getResultList();
//        String resss = res.toString();

        if(res.size() <  1)
        {

        for(int i = 12; i >= 1; i--)
        {
            Monthly_Stats monthly_stats = new Monthly_Stats();
            monthly_stats.updateCount = 0;
            monthly_stats.bikeRidesCount = 0;
            monthly_stats.disableBikesCount = 0;
            monthly_stats.stationId = stationsDTO.getStation_id();
            monthly_stats.month = i;
            JPA.em().persist(monthly_stats);
        }
    }
        else
    {
        return;
    }
    }
}
