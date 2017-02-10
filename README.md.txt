NYC Bike Sharing Application

Language: J2EE
Framework: Play Framework 2.5
Database: MYSQL


Libraries Used: 

org.json : For JSON processing
Redis : 2.4.0
google-maps-services 0.1.9
geoApi by google

Application code is divided in controllers, services, data access layer,
business object models (bo) and data transfer object (dto) layers.

Services are handled in IRequestHandler to operate Async Database operations. 
IRequestHandler then calls respective controller and respective services.
All database operations including querying, inserts, deletes and updates are handled in 
data access layer. DTO are used to transfer information among one layer to other. 

Database connectivity: 
conf/Application.conf file has all configuration keys to access database. An online database 
connections are provided. You can create your own database for that scripts are being saved in
db_scripts folder. Persistance.xml and c3p0 files are used to handle persistance and connection
pooling for database. 

conf/Application.conf has all other keys saved like stations static and dynamic info URL's,
geocode key and google maps URL. One can change these settings from accordingly.

Golbal.Java file has configurations for actor/schedular which runs after every 30 seconds. It has 
been used to get stations dynamic info from respective URL.

Services: 
1) /save It takes URL for static stations info from  configuration and saves all static information
about stations in respective tabe.
2) /update It takes URL for dynamic stations info from configuration and saves respective details
in tables. Furthermore, this service is bering called after every 30 seconds to update stations info.

3) /closest-station(longitude, latitude) This service gets information about stations from db
and calclate distances among their lat long and returns station with shortest distance. GeoApi is being used
for this purpose.

4) closest-station(address) This services uses GeoApi to get Lat, Long using given address. Furthermore, It
does the same operations as service number 3.

5) /stations-with-capacity(num_bikes) It returns the list of station Ids with having at least given of available bikes.

6) Get /monthly-stats(month)  This service returns the monthly stats (Number of bike rides and number of disable 
bikes in specified month)

7) Get /popular-station(month) This service returns the station for which information has updated maximum times. We are 
keeping record of how many times information for each station is being updated. That served as Rank.

8) GET /overall-stats  Retuns total stats (Num of bikes and docks available at all stations)

9) GET /station-stats(station_id) etuns total stats for specified station (Num of bikes and docks available)

Images/Screenshots for all services are attached in public/images folder.

Note: Business Logic has been made in a way that we don't need any cache layer. As we are 
updating data for all stations whenever it changes. And we are keeping record for monthly stats
in another table. This logic has been made by considering the only specified services. 
However, If we needed to create other services like stats for any given date etc then we could save 
all data in database and latest data in cache. Whenever new data comes or updates for any station
we just needed to update the info of that record/station in cache too.