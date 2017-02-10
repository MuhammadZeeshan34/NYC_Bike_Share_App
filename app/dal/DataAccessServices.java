package dal;

import bo.Monthly_Stats;
import bo.Station_Stats;
import bo.Stations;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Engr. ZeeShaN on 2/10/2017.
 */
public class DataAccessServices {

    public Monthly_Stats GetMonthlyStatsDataFromIDAndMonth(long month, long stationId) {
        Query query = JPA.em().createQuery("SELECT n FROM Monthly_Stats n WHERE n.stationId = :stationid AND n.month = :month")
                .setParameter("stationid", stationId)
                .setParameter("month", month);
        Monthly_Stats monthly_stats = (Monthly_Stats) query.getSingleResult();
        return monthly_stats;
    }

    public Station_Stats GetStationStatsFromID(long stationId) {
        return (Station_Stats) JPA.em().find(bo.Station_Stats.class, stationId);
    }

    public void UpdateMonthlyStats(Monthly_Stats monthly_stats) {
        JPA.em().merge(monthly_stats);
    }

    public long GetHighestRankForMonth(long month) {
        return (long) JPA.em().createQuery("SELECT MAX(n.updateCount) FROM Monthly_Stats n" +
                "   WHERE n.month = :month  ")
                .setParameter("month", month).getSingleResult();
    }

    public List<Long> GetStationsForMonthAndUpdateCount(long highestRank, long month) {

        return (List<Long>) JPA.em().createQuery("SELECT n.stationId FROM Monthly_Stats n" +
                "   WHERE n.month = :month AND n.updateCount = :updatecount ")
                .setParameter("month", month).setParameter("updatecount", highestRank).getResultList();
    }

    public Object[] GetRidesAndDisableBikesForMonth(long month) {
        Query query = JPA.em().createQuery("SELECT SUM(n.bikeRidesCount), SUM(n.disableBikesCount) FROM Monthly_Stats n WHERE " +
                "n.month = :month")
                .setParameter("month", month);
        return (Object[]) query.getSingleResult();
    }

    public List<bo.Stations> GetAllStationsObject() {
        Query query = JPA.em().createQuery("SELECT n FROM Stations n");
        return (List<bo.Stations>) query.getResultList();
    }

    public Object GetStationsWithSpecifiesBikesAvailable(long numBikes) {
        Query query = JPA.em().createQuery("SELECT n.stationId FROM Station_Stats n WHERE n.numBikesAvailable >= :numBikes")
                .setParameter("numBikes", numBikes);
        return query.getResultList();
    }

    public String GetTotalAvailableBikesForStation(long stationId) {
        Query query = JPA.em().createQuery("SELECT SUM(n.numBikesAvailable) FROM Station_Stats n WHERE n.stationId= :stationID")
                .setParameter("stationID", stationId);
        Object res = query.getSingleResult();
        return res.toString();
    }

    public String GetTotalDocksAvailableForStation(long stationId)
    {
        Query query = JPA.em().createQuery("SELECT SUM(n.numDocksAvailable) FROM Station_Stats n WHERE n.stationId= :stationID")
            .setParameter("stationID", stationId);
        Object res=query.getSingleResult();
        return res.toString();
    }

    public String GetTotalAvailableBikes() {
        Query query = JPA.em().createQuery("SELECT SUM(n.numBikesAvailable) FROM Station_Stats n");
        Object res = query.getSingleResult();
        return res.toString();
    }

    public String GetTotalAvailableDocks() {
        Query query = JPA.em().createQuery("SELECT SUM(n.numDocksAvailable) FROM Station_Stats n");
        Object res = query.getSingleResult();
        return res.toString();
    }

    public void InsertStationStats(Station_Stats station_stats)
    {
        JPA.em().persist(station_stats);
    }

    public void UpdateStationStats(Station_Stats station_stats)
    {
        JPA.em().merge(station_stats);
    }

    public void UpdateStationStaticInfo(Stations station)
    {
        JPA.em().merge(station);
    }

    public void InsertStationStaticInfo(Stations station)
    {
        JPA.em().persist(station);
    }



    public  bo.Stations GetStationFromID(long stationId)
    {
        return  JPA.em().find(Stations.class, stationId);
    }
}
