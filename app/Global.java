/**
 * Created by Engr. ZeeShaN on 2/8/2017.
 */
import akka.actor.Cancellable;
import controllers.NYCBikeSharingAppController;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class Global extends GlobalSettings {

    private Cancellable scheduler;
    private NYCBikeSharingAppController appointmentObj = new NYCBikeSharingAppController();

    @Override
    public void onStart(Application application) {
        int timeDelayFromAppStartToLogFirstLogInMs = 0;
        int timeGapBetweenMemoryLogsInMinutes = 30;
        scheduler = Akka.system().scheduler().schedule(Duration.create(timeDelayFromAppStartToLogFirstLogInMs, TimeUnit.MILLISECONDS),
                Duration.create(timeGapBetweenMemoryLogsInMinutes, TimeUnit.SECONDS),
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Cron Job");
                        appointmentObj.UpdateStationsStatsAction();
                        //nycController.GetCount();
                        // Call a function (to print JVM stats)
                    }
                },
                Akka.system().dispatcher());
        super.onStart(application);
    }

    @Override
    public void onStop(Application app) {
        scheduler.cancel();
        super.onStop(app);
    }

}