package controllers;

import play.libs.F;
import play.mvc.Result;
import services.NYCBikeSharingAppServices;
import tenantmanagement.AsyncJPA;


public class NYCBikeSharingAppController {
    private NYCBikeSharingAppServices nycBikeSharingAppServicesObj;

    public NYCBikeSharingAppController(){
        nycBikeSharingAppServicesObj = new NYCBikeSharingAppServices();}

    public F.Promise<Result> SaveStaticInformationAction() {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.saveStaticInformation());

        return serviceResponse;
    }

    public F.Promise<Result> UpdateStationsStatsAction() {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.updateStationsStats());

        return serviceResponse;
    }

    public F.Promise<Result> GetOverAllStatsAction() {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetOverAllStats());

        return serviceResponse;
    }

    public F.Promise<Result> GetStationStatsAction(long stationId) {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetStationStats(stationId));

        return serviceResponse;
    }

    public F.Promise<Result> GetListOfStationsWithSpecifiedBikesAction(long numBikes) {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetListOfStationsWithSpecifiedBikes(numBikes));

        return serviceResponse;
    }

    public F.Promise<Result> GetNearestStationAction(double lon, double lat) {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetNearestStation(lon, lat));

        return serviceResponse;
    }

    public F.Promise<Result> GetNearestStationAction2(String address) {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetNearestStation(address));

        return serviceResponse;
    }

    public F.Promise<Result> GetPopularStationAction(String month) {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetPopularStation(month));

        return serviceResponse;
    }

    public F.Promise<Result> GetMonthlyStatsAction(String month) {
        F.Promise<Result> serviceResponse;

        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.GetMonthlyStats(month));

        return serviceResponse;
    }









}
//	private NYCBikeSharingAppServices nycBikeSharingAppServicesObj;
//
//    public NYCBikeSharingAppController(){nycBikeSharingAppServicesObj = new NYCBikeSharingAppServices();}
//
//	public F.Promise<Result> SaveStaticInformationAction() {
//        F.Promise<Result> serviceResponse;
//
//        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.saveStaticInformation());
//
//        return serviceResponse;
//    }
//	public F.Promise<Result> updateAppointmentAction() {
//        F.Promise<Result> serviceResponse;
//
//        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.updateAppointment());
//
//        return serviceResponse;
//    }
//	public F.Promise<Result> updateAppointmentEMRsByPhysicianAction() {
//        F.Promise<Result> serviceResponse;
//
//        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.updateAppointmentEMRsByPhysician());
//
//        return serviceResponse;
//    }
//
//    public F.Promise<Result> deleteAppointmentAction(long appointmentId) {
//        F.Promise<Result> serviceResponse;
//
//        serviceResponse = AsyncJPA.doInAsync(() -> nycBikeSharingAppServicesObj.deleteAppointment(appointmentId));
//
//        return serviceResponse;
//    }
//
//}
