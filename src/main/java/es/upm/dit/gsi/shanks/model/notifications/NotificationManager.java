package es.upm.dit.gsi.shanks.model.notifications;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.event.Event;
import es.upm.dit.gsi.shanks.model.notifications.Notification;

public class NotificationManager {

    private static List<Notification> ln;
    private static ShanksSimulation sim;
    private static int ID_COUNTER;


    /**
     * 
     * @param ln
     * @param simulation
     */
    public NotificationManager(List<Notification> ln,
            ShanksSimulation simulation) {
        NotificationManager.ln = ln;
        NotificationManager.sim = simulation;
        NotificationManager.ID_COUNTER = 0;
    }


    /**
     * 
     * @param simulation
     */
    public NotificationManager(ShanksSimulation simulation) {
        NotificationManager.ln = new ArrayList<Notification>();
        NotificationManager.sim = simulation;
        NotificationManager.ID_COUNTER = 0;
    }

    static public void addNotification(Event e) {
        NotificationManager.ln.add(new Notification(getNotificationID(),
                                        NotificationManager.sim.getSchedule().getSteps(), 
                                        e.getLauncher(), e.getClass(), e.getAffected()));
    }

    private static String getNotificationID() {
        return "Notification#" + NotificationManager.ID_COUNTER++;
    }

}
