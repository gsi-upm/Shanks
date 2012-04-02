/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.notification;

import java.util.List;

import es.upm.dit.gsi.shanks.model.event.OneShotEvent;
import es.upm.dit.gsi.shanks.model.event.PeriodicEvent;
import es.upm.dit.gsi.shanks.model.event.ProbabilisticEvent;

/**
 * A InteractionNotification store information of Events, cause in SHANKS all interactions
 * between two elements are defined by them. This information have a relational
 * entity with the form:
 * 
 *      source => interaction => target
 * 
 * @author darofar
 *
 */
public class InteractionNotification extends Notification {
    
    public static final String ONE_SHOOT_EVENT_TYPE = OneShotEvent.class.getName();
    public static final String PERIODIC_EVENT_TYPE = PeriodicEvent.class.getName();
    public static final String PROBABILISTIC_EVENT_TYPE = ProbabilisticEvent.class.getName();
    
    /**
     * Name of the class that define the interaction itself. 
     */
    private String interaction = null;
    
    /**
     * Interaction target objects.
     */
    private List<Object> target = null;
    
    /**
     * 
     * @param id
     * @param when
     * @param source
     */
    public InteractionNotification(String id, long when, Object source) {
        super(id, when, source);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     * @param when
     * @param source
     * @param interaction
     * @param target
     */
    public InteractionNotification(String id, long when, Object source,
            String interaction, List<Object> target) {
        super(id, when, source);
        this.interaction = interaction;
        this.target = (List<Object>) target;
    }

    /**
     * @return the name of the class that defines the  interaction.
     */
    public String getInteraction() {
        return interaction;
    }

    /**
     * @return a list of interaction's target objects 
     */
    public List<Object> getTarget() {
        return target;
    }
}
