package services;


import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.util.*;


import bo.Monthly_Stats;
import bo.Station_Stats;
import bo.Stations;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import com.typesafe.config.ConfigFactory;
import dal.DataAccessServices;
import dto.*;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import play.mvc.Result;
import utilities.JsonReader;
import utilities.UtilityFunctions;


public class NYCBikeSharingAppServices {

    private DataAccessServices sqlOperationsObj = new DataAccessServices();
    private UtilityFunctions utilityFunctions = new UtilityFunctions();

	public Result saveStaticInformation() throws ParseException {

        // Read URL to access JSON from static stations data
        String url = ConfigFactory.load().getString("static_data_url");

        // Initialize members
        JsonReader jsonReader = new JsonReader();
        JSONObject json,data;
        JSONArray stationsArray;
        StationsDTO stationsDTO = new StationsDTO();
        List<StationsDTO> stationsDTOList =  new ArrayList<StationsDTO>();;
        JSONObject object = null;

        // Business logic
        try {
            json = jsonReader.readJsonFromUrl(url);  // Read JSON from URL
            data = json.getJSONObject("data");
            stationsArray = data.getJSONArray("stations");

            // Add details in respective DTO
            for(int i = 0; i < stationsArray.length(); i++)
            {
                object  = stationsArray.getJSONObject(i);
                stationsDTO = new StationsDTO();
                stationsDTO.SetStationDTOObject(object);
                stationsDTOList.add(stationsDTO);
            }

            for(int i = 0; i < stationsDTOList.size(); i++) {
                try {
                    Monthly_Stats monthly_stats = new Monthly_Stats();
                    bo.Stations station = new bo.Stations();
                    // DTO to BO conversion
                    station = station.StationsDTO2BO(stationsDTOList.get(i));
                    // Get Already Saved Object for Specified Station ID
                    bo.Stations alreadySavedStationObj = sqlOperationsObj.GetStationFromID(station.getStationId());
                    // Initialize Monthly Stats from Static Data
                    monthly_stats.InitializeMonthlyStats(stationsDTOList.get(i));
                    if(alreadySavedStationObj == null) {
                        // Insert Static Stations Data in Database
                        sqlOperationsObj.InsertStationStaticInfo(station);
                    }
                    else
                    {
                        // Update Static Stations Data in Database
                        sqlOperationsObj.UpdateStationStaticInfo(station);
                    }

                } catch (Exception ex) {
                    return badRequest(ex.toString());
                }
            }

        }
        catch (Exception ex)
        {
            return badRequest(ex.toString());
        }

        // Return response
        return  ok("Stations Static Information Saved Successfully");
    }


    public Result updateStationsStats()
    {
        // Get URL to access JSON from dynamic stations data
        String url = ConfigFactory.load().getString("dynamic_data_url");

        // Initialize members
        JsonReader jsonReader = new JsonReader();
        JSONObject json,data;
        JSONArray stationsArray;
        StationStatsDTO stationsDTO = new StationStatsDTO();
        List<StationStatsDTO> stationsDTOList =  new ArrayList<StationStatsDTO>();;
        JSONObject object = null;

        // Business Logic
        try {
            // Read JSON from URL
            json = jsonReader.readJsonFromUrl(url);
            // Pre processing of URL
            data = json.getJSONObject("data");
            stationsArray = data.getJSONArray("stations");

            // Add detail in DTO
            for(int i = 0; i < stationsArray.length(); i++)
            {
                object  = stationsArray.getJSONObject(i);
                stationsDTO = new StationStatsDTO();
                stationsDTO.SetStationDTOObject(object);
                stationsDTOList.add(stationsDTO);
            }

            for(int i = 0; i < stationsDTOList.size(); i++) {
                try {
                    bo.Station_Stats station_stats = new bo.Station_Stats();
                    // DTO to BO conversion
                    station_stats = station_stats.StationStatsDTO2BO(stationsDTOList.get(i));
                    // Get Already Saved Station object for specified station ID for Database
                    bo.Station_Stats alreadySavedStationObj = sqlOperationsObj.GetStationStatsFromID(station_stats.getStationId());
                    if(alreadySavedStationObj == null) {
                        // Insert Station Dynamic Data in Database
                        sqlOperationsObj.InsertStationStats(station_stats);
                    }
                    else
                    {
                        bo.Station_Stats alreadySavedStationStatsObj = new Station_Stats();
                        // Get Already Saved Station Stats Object for specified Station ID from Database
                        alreadySavedStationStatsObj = sqlOperationsObj.GetStationStatsFromID(station_stats.getStationId());

                        // If data is updated in JSON for this specific station, update info in DB
                        if(alreadySavedStationStatsObj.getLastReported() == station_stats.getLastReported())
                        {
                            continue;
                        }
                        else {
                            // Update in stats table
                            // Update Rank on the base of usage
                            utilityFunctions.UpdateMonthlyCount(station_stats);
                            // Update Counts for Rides and Disable in database
                            utilityFunctions.UpdateMonthlyData(station_stats);
                            // Update Dynamic Data from new station data
                            sqlOperationsObj.UpdateStationStats(station_stats);
                        }
                    }

                } catch (Exception ex) {
                    return badRequest(ex.toString());
                }
            }

        }
        catch (Exception ex)
        {
            return badRequest(ex.toString());
        }

           return  ok("Stations Dynamic Stats Updated Successfully");
    }


    public Result GetOverAllStats()
    {
        // Initialize members
        String numOfBikesAvailable;
        String numOfDocksAvailable;

        // Business Logic
        try {
            numOfBikesAvailable = sqlOperationsObj.GetTotalAvailableBikes();
            numOfDocksAvailable = sqlOperationsObj.GetTotalAvailableDocks();
        }
        catch (Exception ex)
        {
            return badRequest(ex.toString());
        }

        // Make response to return
        JSONObject object = new JSONObject();
        object.put("Number of Bikes Available",numOfBikesAvailable);
        object.put("Number of Docks Available",numOfDocksAvailable);

        return ok(object.toString());
    }


    public Result GetStationStats(long stationId) {

        // Initialize Members
        Stations station;

        // Business Logic
        try {
            station = sqlOperationsObj.GetStationFromID(stationId);
        }
        catch (Exception ex)
        {
            return badRequest(ex.toString());
        }

        if(station == null)
            return badRequest("Station does not exist");

        // Get Total Bikes and Docks Count from database for specified station id
        String numOfBikesAvailable = sqlOperationsObj.GetTotalAvailableBikesForStation(stationId);
        String numOfDocksAvailable = sqlOperationsObj.GetTotalDocksAvailableForStation(stationId);

        // Make Response
        JSONObject object = new JSONObject();
        object.put("Number of Bikes Available", numOfBikesAvailable);
        object.put("Number of Docks Available", numOfDocksAvailable);

        return ok(object.toString());
    }

    public Result GetListOfStationsWithSpecifiedBikes(long numBikes)
    {
        // Initialize member
        Object res;

        // Business Logic
        try {
            res = sqlOperationsObj.GetStationsWithSpecifiesBikesAvailable(numBikes);
        }catch (Exception ex) {
            return badRequest(ex.toString());
        }

        return ok(res.toString());
    }

    public Result GetNearestStation(double paramsLon,double paramsLat)
    {
        // Initialze Members
        StationsDTO stationsDTO = new StationsDTO();
        List<bo.Stations> nearestStationDetailsDTOList = new ArrayList<Stations>();
        List<NearestStationDetailsDTO> distances = new ArrayList<NearestStationDetailsDTO>();

        // Business Logic
        try {
            // Get details of all saved stations
            nearestStationDetailsDTOList = sqlOperationsObj.GetAllStationsObject();
        }
        catch (Exception ex) {
            return badRequest(ex.toString());
        }

        // Compute Distances for all stations with specified Lat, Long and insert them in an array
        for(int i = 0; i < nearestStationDetailsDTOList.size(); i++)
        {
            bo.Stations station= nearestStationDetailsDTOList.get(i);
            NearestStationDetailsDTO nearestStationDetailsDTO = new NearestStationDetailsDTO();
            nearestStationDetailsDTO.setDistance(utilityFunctions.calculate_distance(paramsLat, station.getLatitude(), paramsLon, station.getLongitude()));
            nearestStationDetailsDTO.setStationId(station.getStationId());
            nearestStationDetailsDTO.setLatitude(station.getLatitude());
            nearestStationDetailsDTO.setLongitude(station.getLongitude());
            distances.add(nearestStationDetailsDTO);
        }

        // Sort Array object on the basis of Object by using self made comparator
        Collections.sort(distances, (NearestStationDetailsDTO v1, NearestStationDetailsDTO v2) -> v1.compareTo(v2));

        // Make response to return
        JSONObject object = new JSONObject();
        object.put("Nearest Station ID", distances.get(0).getStationId());
        object.put("Nearest Station Distance (In meters)", distances.get(0).getDistance());
        object.put("Nearest Station Latitude", distances.get(0).getLatitude());
        object.put("Nearest Station Longitude", distances.get(0).getLongitude());

        return ok(object.toString());
    }


    public Result GetNearestStation(String address)
    {
        // Initialize members
        String myKey,encodedAddress, url;
        double latitude, longitude;
        GeoApiContext context;
        GeocodingResult[] results;

        // Business Logic
        // Get Key for Geocode Library
        myKey = ConfigFactory.load().getString("geocode_lib_key");

        try {
            // Encode Address
            encodedAddress = new String(address.getBytes("UTF-8"));
        }catch(UnsupportedEncodingException ex)
        {
            return badRequest();
        }

        try {
            // Get Lat Long for specified address using GeoApi
            context = new GeoApiContext().setApiKey(myKey);
            results =  GeocodingApi.geocode(context,
                    encodedAddress).await();

            latitude = results[0].geometry.location.lat;
            longitude = results[0].geometry.location.lng;
            }
        catch (Exception ex)
        {
            return  badRequest();
        }

        return  GetNearestStation(longitude,latitude);
    }


    public Result GetMonthlyStats(String monthString)
    {
        // Initialze members
        long month;
        Object[] objects;
        JSONObject jsonObject = new JSONObject();

        // Business Logic
        try {
            month = utilityFunctions.GetMonthFromString(monthString);
            // Get Rides count and disable bikes count for specified month
            objects = sqlOperationsObj.GetRidesAndDisableBikesForMonth(month);
        }
        catch (Exception ex)
        {
            return badRequest(ex.toString());
        }

        // Make response
        jsonObject.put("Num of Bike Rides in the month of " + monthString, objects[0]);
        jsonObject.put("Num of Bikes disables in the month of " + monthString, objects[1]);

        return ok(jsonObject.toString());
    }

    public Result GetPopularStation(String monthString)
    {
        // Initialize Methods
        long month,highestRank;
        List<Long> stationsId;
        JSONObject jsonObject = new JSONObject();

        // Business Logic
        try {
            month = utilityFunctions.GetMonthFromString(monthString.toLowerCase());
            highestRank = sqlOperationsObj.GetHighestRankForMonth(month);
            stationsId = sqlOperationsObj.GetStationsForMonthAndUpdateCount(highestRank, month);//
        }
        catch (Exception ex)
        {
            return badRequest(ex.toString());
        }

        // Make response to return
        jsonObject.put("Popular Station ID for the month of " + monthString, stationsId.get(0));

        return ok(jsonObject.toString());
    }


}
