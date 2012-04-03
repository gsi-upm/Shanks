/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.notification;

/**
 * ElementValueNotifactions are user-made notifications that trace the value of certain 
 * field, variable or whatever it wants to save for consult later. In this case  a 
 * notification has the form:
 * 
 *      source => elementID => value 
 *      
 * @author darofar
 *
 */
public class ElementValueNotification extends Notification {

    /**
     * The identifier of the element to trace.
     */
    private String elementID = null;
    
    /**
     * Value of the element for the current step of the simulation. This value is only for 
     * the use of the user.  
     */
    private Object value;
    
    /**
     * Default constructor. A ElementValueNotification needs the parameters to set a 
     * Notification plus an element identifier and the current value from that element.
     * 
     * @param id
     * @param when
     * @param source
     * @param elementID
     * @param value
     */
    public ElementValueNotification(String id, long when, Object source,
            String elementID, Object value) {
        super(id, when, source);
        this.elementID = elementID;
        this.value = value;
    }

    /**
     * @return 
     *          the element identifier. 
     */
    public String getElementID() {
        return elementID;
    }

    /**
     * @return 
     *          the saved value of the element. 
     */
    public Object getValue() {
        return value;
    }

}
