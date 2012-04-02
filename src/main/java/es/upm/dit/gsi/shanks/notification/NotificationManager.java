/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.notification;

import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.event.Event;

/**
 * 
 * @author darofar
 *
 */
public class NotificationManager implements Steppable {

    private static List<Notification> notifications;
    private static List<Notifable> notifables;
    private static ShanksSimulation sim;
    private static int ID_COUNTER;


    /**
     * Constructor with predefined  notification list. 
     * 
     * @param lNotifications 
     *          the predefined list of notifications, probably a list of and old simulation.  
     * @param simulation
     *                  a reference to the simulation object, needed to obtain information 
     *                  for each notification.
     */
    public NotificationManager(List<Notification> lNotifications, List<Notifable> lNotifables,
            ShanksSimulation simulation) {
        NotificationManager.notifications = lNotifications;
        NotificationManager.notifables = lNotifables;
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
        NotificationManager.notifications = new ArrayList<Notification>();
        NotificationManager.notifables = new ArrayList<Notifable>();
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
    @SuppressWarnings("unchecked")
    static public void addNotification(Event e) {
        NotificationManager.notifications.add(new InteractionNotification(getNotificationID(),
                                        NotificationManager.sim.getSchedule().getSteps(), 
                                        e.getLauncher(), e.getClass().getName(), 
                                        (List<Object>) e.getAffected()));
    }
    
    /**
     * 
     * @param notifable
     */
    public static void addNotifable(Notifable notifable) {
        NotificationManager.notifables.add(notifable);
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
    
    /**
     * Search for a notification with the given id.
     *   
     * @param id 
     *          the identifier of the wanted notification. 
     * @return 
     *          the notification founded or null if no one can be found.
     */
    public Notification getByID(String id) {
        //TODO maybe SORT_By_ID then make a better array searching algorithm
        for (Notification n: NotificationManager.notifications){
            if(n.getId().equals(id)){
                return n;
            }
        }
        return null;
    }
    
    /**
     * Search for notifications saved on the given step.
     * 
     * @param step 
     *              the simulation step number from which the notifications are asked
     * @return
     *              a List with the notifications that match the given step or null if no one can be found.           
     */
    public List<Notification> getByStep(int step) {
        List<Notification> found = new ArrayList<Notification>();
        for (Notification n: NotificationManager.notifications)
            if (n.getWhen() == step) 
                found.add(n);
        if(found.size()>0)        
            return found;
        return null;
    }
    
    /**
     * Search for notifications originated from the given source object. 
     * 
     * @param source
     *              the object of the simulation that generates the notifications 
     * @return
     */
    public List<Notification> getBySource(Object source) {
        List<Notification> found = new ArrayList<Notification>();
        for (Notification n: NotificationManager.notifications)
            if (n.getSource().equals(source)) 
                found.add(n);
        if(found.size()>0)        
            return found;
        return null;
    }
    
    /**
     * Search for notifications of interaction type that match the given class name.
     *   
     * @param interaction
     *                  the class that defines the interaction type of desired notifications. 
     * @return
     *          a List with the notifications founded that match the given class name. 
     */
    public List<Notification> getByInteraction(Class<?> interaction) {
        List<Notification> found = new ArrayList<Notification>();
        for (Notification n: NotificationManager.notifications)
            if (((InteractionNotification)n).getInteraction().equals(interaction.getName())) 
                found.add(n);
        if(found.size()>0)        
            return found;
        return null;
    }
    
    
    /**
     * Search for notifications of interaction type that match the given target object
     * .
     * @param target
     *              the target object from which notifications are asked.
     * @return
     *          a List with notifications founded that match the given target object. 
     */
    public List<Notification> getByTarget(Object target) {
        List<Notification> found = new ArrayList<Notification>();
        for (Notification n: NotificationManager.notifications) {
            for (Object t : ((InteractionNotification)n).getTarget()) {
                if (t.equals(target)) 
                    found.add(n);
            }
        }
        if(found.size()>0)        
            return found;
        return null;
    }
    
    /**
     * Search for notifications of ElementValue type that match the given element identifier.
     * @param elementID
     *                  the identifier of the element that was notfied. 
     * @return
     *          A list with the notifications saved for the given element identifier.  
     */
    public List<Notification> getByElementID(String elementID) {
        List<Notification> found = new ArrayList<Notification>();
        for (Notification n: NotificationManager.notifications)
            if (((ElementValueNotification)n).getElementID().equals(elementID)) 
                found.add(n);
        if(found.size()>0)        
            return found;
        return null;
    }
    
    /**
     * Search for notifications that match the corresponding notifications realization.
     * 
     * @param type
     *              can be InteractionNotification.class or ElementValueNotification.class  
     * @return
     *          a List with all notifications from the selected type. 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Notification> getByType(Class<?> type) {
        List<?> byType = this.getByType();
        if (type.isAssignableFrom(InteractionNotification.class))
            return (ArrayList<Notification>) byType.get(0);
        return (ArrayList<Notification>) byType.get(1);
    }
    
    
    private List<?> getByType() {
        ArrayList <Notification> inList = new ArrayList<Notification>();
        ArrayList <Notification> evnList = new ArrayList<Notification>();
        for (Notification n: NotificationManager.notifications) {
            if(n.getClass().isAssignableFrom(InteractionNotification.class))
                inList.add(n);
            else if (n.getClass().isAssignableFrom(ElementValueNotification.class))
                evnList.add(n);
            else {
                //TODO Warning
            }
        }
        ArrayList<ArrayList<Notification>> byType = new ArrayList<ArrayList<Notification>>();
        byType.add(inList);
        byType.add(evnList);
        return byType;
    }


    @Override
    public void step(SimState arg0) {
        for(Notifable n: NotificationManager.notifables){
            NotificationManager.addNotification(n);
        }
    }
    
    /**
     * 
     * @param n
     */
    private static void addNotification(Notifable n) {
        NotificationManager.notifications.add(new ElementValueNotification(
                getNotificationID(),
                NotificationManager.sim.getSchedule().getSteps(), 
                n.getSource(), 
                n.getID(), 
                n.getElementValue()));
        
    }
    
    
    private static final long serialVersionUID = 1528691206423084650L;
}
