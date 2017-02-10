package utilities;

import bo.Monthly_Stats;
import dal.DataAccessServices;
import org.joda.time.DateTime;

/**
 * Created by Engr. ZeeShaN on 2/10/2017.
 */
public class UtilityFunctions {

    private DataAccessServices dbOperationsObj = new DataAccessServices();
    public long GetMonthFromString(String month)
    {

        if(month.equals("january"))
            return 1;
        else if(month.equals("february"))
            return 2;
        else if(month.equals("march"))
            return 3;
        else if(month.equals("april"))
            return 4;
        else if(month.equals("may"))
            return 5;
        else if(month.equals("june"))
            return 6;
        else if(month.equals("july"))
            return 7;
        else if(month.equals("august"))
            return 8;
        else if(month.equals("september"))
            return 9;
        else if(month.equals("october"))
            return 10;
        else if(month.equals("november"))
            return 11;
        else if(month.equals("december"))
            return 12;
        else
            return 1;
    }

    public void UpdateMonthlyCount(bo.Station_Stats station_stats)
    {
        try {
            int month = GetCurrentMonth();
            Monthly_Stats monthly_stats = dbOperationsObj.GetMonthlyStatsDataFromIDAndMonth(Long.valueOf(month),station_stats.getStationId());
            monthly_stats.setUpdateCount(monthly_stats.getUpdateCount() + 1);
            dbOperationsObj.UpdateMonthlyStats(monthly_stats);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public void UpdateMonthlyData(bo.Station_Stats station_stats)
    {
        try
        {
            int month = GetCurrentMonth();
            long numToAddinBikeRides = 0;
            long numToAddinDisableBikes = 0;

            // Get the old data present in DB (Last saved instance)
            bo.Station_Stats lastSavedStationData = dbOperationsObj.GetStationStatsFromID(station_stats.getStationId());


            if(station_stats.getNumBikesAvailable() < lastSavedStationData.getNumBikesAvailable())
                numToAddinBikeRides = lastSavedStationData.getNumBikesAvailable() - station_stats.getNumBikesAvailable();

            if(station_stats.getNumBikesDisabled() > lastSavedStationData.getNumBikesDisabled() )
                numToAddinDisableBikes = station_stats.getNumBikesDisabled() - lastSavedStationData.getNumBikesDisabled();

            // Get The Monthly Stats data for current station
            Monthly_Stats monthly_stats =  dbOperationsObj.GetMonthlyStatsDataFromIDAndMonth(Long.valueOf(month), station_stats.getStationId());

            if(monthly_stats.getDisableBikesCount() == 0 && station_stats.getNumBikesDisabled() > 0)
                monthly_stats.setDisableBikesCount(station_stats.getNumBikesDisabled());

            monthly_stats.setBikeRidesCount(monthly_stats.getBikeRidesCount() + numToAddinBikeRides);
            monthly_stats.setDisableBikesCount( monthly_stats.getDisableBikesCount() + numToAddinDisableBikes);

            dbOperationsObj.UpdateMonthlyStats(monthly_stats);

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public int GetCurrentMonth()
    {
        return DateTime.now().getMonthOfYear();
    }

    public static double calculate_distance(double lat1, double lat2, double lon1,
                                            double lon2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.sqrt(distance);
    }
}
