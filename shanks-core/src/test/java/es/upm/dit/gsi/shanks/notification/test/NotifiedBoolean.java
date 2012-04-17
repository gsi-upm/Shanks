package es.upm.dit.gsi.shanks.notification.test;

import es.upm.dit.gsi.shanks.notification.Notifable;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class NotifiedBoolean implements Notifable{

    /**
     * the variable identifier.  
     */
    String id;
    
    /**
     * current variable value. 
     */
    Boolean elementValue;
    
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
        this.elementValue = (Boolean) elementValue;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public void setSource(Object source) {
        this.source = source;
    }
    
    public Boolean get() {
        return (Boolean) this.getElementValue();
    }

    /**
     * @param id
     * @param source
     */
    public NotifiedBoolean(String id, Object source) {
        NotificationManager.addNotifable(this);
        this.id = id;
        this.source = source;
    }

    public void set(Boolean value) {
        this.setElementValue(value);
    }

}
