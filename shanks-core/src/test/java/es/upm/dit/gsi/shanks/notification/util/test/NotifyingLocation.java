package es.upm.dit.gsi.shanks.notification.util.test;

import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.notification.Notifable;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class NotifyingLocation extends Location implements Notifable {

    /**
     * the variable identifier.  
     */
    String id;
    
    /**
     * current variable value. 
     */
    Object elementValue;
    
    /**
     * the generated notification source.
     */
    Object source;    
    
    /**
     * Default constructor. It subscribes this object on the Notifables list of 
     * NotificationManager.   
     */
    public NotifyingLocation(String id, Object source) {
        NotificationManager.addNotifable(this);
        this.source = source;
        this.setElementValue((Location)this);
    }
    
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
        this.elementValue = elementValue;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public void setSource(Object source) {
        this.source = source;
    }
    
//    /**
//     * @return the location2D
//     */
//    public Double2D getLocation2D() {
//        if (this.is2DLocation()) {
//            return this.location2D;   
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * @param location2D
//     *            the location2D to set
//     */
//    public void setLocation2D(Double2D location2D) {
//        this.location2D = location2D;
//        this.is2D = true;
//    }
//
//    /**
//     * @return the location3D
//     */
//    public Double3D getLocation3D() {
//        if (this.is3DLocation()) {
//            return location3D;            
//        } else {
//            return null;   
//        }
//    }
//
//    /**
//     * @param location3D
//     *            the location3D to set
//     */
//    public void setLocation3D(Double3D location3D) {
//        this.location3D = location3D;
//        this.is3D = true;
//    }
//
//    /**
//     * @return true if the location has a Double2D location, else if not
//     */
//    public boolean is2DLocation() {
//        return this.is2D;
//    }
//
//    /**
//     * @return true if the location has a Double3D location, else if not
//     */
//    public boolean is3DLocation() {
//        return this.is3D;
//    }
//
//    /**
//     * Compare two locations and return if the locations are near or not
//     * 
//     * @param location Location to compare with
//     * @param distance The distance between two locations
//     * @return true is the real distance is lower or equals than the distance parameter
//     */
//    public boolean isNearTo(Location location, double distance) {
//        if (this.is2DLocation() && location.is2DLocation()) {
//            return this.isNearTo(location.getLocation2D(), distance);
//        } else if (this.is3DLocation() && location.is3DLocation()) {
//            return this.isNearTo(location.getLocation3D(), distance);
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * Compare two locations and return if the locations are near or not
//     * 
//     * @param location Location to compare with
//     * @param distance The distance between two locations
//     * @return true is the real distance is lower or equals than the distance parameter
//     */
//    public boolean isNearTo(Double2D location, double distance) {
//        if (this.is2DLocation()) {
//            return this.getLocation2D().distance(location) < distance;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * Compare two locations and return if the locations are near or not
//     * 
//     * @param location Location to compare with
//     * @param distance The distance between two locations
//     * @return true is the real distance is lower or equals than the distance parameter
//     */
//    public boolean isNearTo(Double3D location, double distance) {
//        if (this.is3DLocation()) {
//            return this.getLocation3D().distance(location) < distance;
//        } else {
//            return false;
//        }
//    }

}
