package es.upm.dit.gsi.shanks.notification.util.test;

import es.upm.dit.gsi.shanks.notification.Notifable;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class NotifiedString implements Notifable {

    
    /**
     * @param id
     * @param source
     */
    public NotifiedString(String id, Object source) {
        NotificationManager.addNotifable(this);
        this.id = id;
        this.source = source;
    }

    /**
     * the variable identifier.  
     */
    String id;
    
    /**
     * current variable value. 
     */
    String elementValue;
    
    /**
     * the generated notification source.
     */
    Object source;   
    
    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getElementValue() {
        return this.elementValue;
    }

    @Override
    public void setElementValue(Object elementValue) {
        this.elementValue = (String) elementValue;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public void setSource(Object source) {
        this.source = source;
    }
    
    public String get() {
        return (String) this.getElementValue();
    }

    public void set(String value) {
        this.setElementValue(value);
    }

}
