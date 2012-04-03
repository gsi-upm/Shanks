/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.notification;

import java.util.List;

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
    
    /**
     * Name of the class that define the interaction itself. 
     */
    private String interaction = null;
    
    /**
     * Interaction's target objects.
     */
    private List<Object> target = null;
    
    /**
     * Default constructor. A InteractionNotification needs the parameters to set up a 
     * notification plus a the name of the class that defines the interaction and the 
     * target object that the interactions affect. 
     *   
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
     * @return 
     *          the name of the class that defines the  interaction.
     */
    public String getInteraction() {
        return interaction;
    }

    /**
     * @return 
     *          a list of interaction's target objects 
     */
    public List<Object> getTarget() {
        return target;
    }
}