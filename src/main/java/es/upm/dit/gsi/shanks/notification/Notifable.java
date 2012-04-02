/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.notification;

/**
 * 
 * @author darofar
 *
 */
public abstract class Notifable {
    
    /**
     * 
     */
    String id;
    
    /**
     * 
     */
    Object elementValue;
    
    public Notifable() {
        NotificationManager.addNotifable(this);
    }

    /**
     * 
     * @return the id
     */
    abstract public String getID();

    /**
     * @param id the id to set
     */
    abstract public void setId(String id);

    /**
     * 
     * @return the elementValue
     */
    abstract public Object getElementValue();

    /**
     * @param elementValue the elementValue to set
     */
    abstract public void setElementValue(Object elementValue);

    /**
     * 
     * @return
     */
    abstract public Object getSource();
    

}
