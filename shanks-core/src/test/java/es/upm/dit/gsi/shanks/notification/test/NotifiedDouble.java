package es.upm.dit.gsi.shanks.notification.test;

import es.upm.dit.gsi.shanks.notification.Notifable;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class NotifiedDouble implements Notifable{

    /**
     * the variable identifier.  
     */
    String id;
    
    /**
     * current variable value. 
     */
    Double elementValue;
    
    /**
     * @param id
     * @param source
     */
    public NotifiedDouble(String id, Object source) {
        NotificationManager.addNotifable(this);
        this.id = id;
        this.source = source;
    }

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
        this.elementValue = (Double) elementValue;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public void setSource(Object source) {
        this.source = source;
    }
    
    public Double get() {
        return (Double) this.getElementValue();
    }

    public void set(Double value) {
        this.setElementValue(value);
    }

}
