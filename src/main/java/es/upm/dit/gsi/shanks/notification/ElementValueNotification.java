/**
 * 
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
     * 
     */
    private String elementID = null;
    
    /**
     * Value 
     */
    private Object value;
    
    /**
     * 
     * @param id
     * @param when
     * @param source
     */
    public ElementValueNotification(String id, long when, Object source) {
        super(id, when, source);
        // TODO Auto-generated constructor stub
    }

    /**
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
     * @return the element identifier. 
     */
    public String getElementID() {
        return elementID;
    }

    /**
     * @return the saved value of the element. 
     */
    public Object getValue() {
        return value;
    }

}
