package es.upm.dit.gsi.shanks.notification;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.event.Event;

public class NotificationManager {//extends Steppable {

    private static List<Notification> ln;
    private static ShanksSimulation sim;
    private static int ID_COUNTER;


    /**
     * Constructor with predefined  notification list. 
     * 
     * @param ln 
     *          the predefined list of notifications, probably a list of and old simulation.  
     * @param simulation
     *                  a reference to the simulation object, needed to obtain information 
     *                  for each notification.
     */
    public NotificationManager(List<Notification> ln,
            ShanksSimulation simulation) {
        NotificationManager.ln = ln;
        NotificationManager.sim = simulation;
        NotificationManager.ID_COUNTER = 0;
    }


    /**
     * Default constructor. NotificatioManager needs a reference to the simulation to work
     * properly.
     * 
     * @param simulation
     *                  a reference to the simulation object, needed to obtain information 
     *                  for each notification.
     */
    public NotificationManager(ShanksSimulation simulation) {
        NotificationManager.ln = new ArrayList<Notification>();
        NotificationManager.sim = simulation;
        NotificationManager.ID_COUNTER = 0;
    }
    
    /**
     * Whenever a new event triggers it will use this method to add the corresponding notification
     * to the notification lists. 
     *   
     * @param e
     *          the event that for the notification. 
     */
    static public void addNotification(Event e) {
        NotificationManager.ln.add(new Notification(getNotificationID(),
                                        NotificationManager.sim.getSchedule().getSteps(), 
                                        e.getLauncher(), e.getClass(), e.getAffected()));
    }

    /**
     * Each new notification needs a notification-ID. NotificationManager monitors the number of 
     * notifications and generates a unique identifier for each of them, using this method to obtain it. 
     *   
     * @return
     *          a notification ID corresponding with the number of notifications saved on this simulation.  
     */
    private static String getNotificationID() {
        return "Notification#" + NotificationManager.ID_COUNTER++;
    }

}
