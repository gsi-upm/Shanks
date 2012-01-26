package es.upm.dit.gsi.shanks.model.element.device.portrayal.test;

import java.awt.Color;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;

/**
 * @author a.carrera
 *
 */
public class MyDevice3DPortrayal extends Device3DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7701586034482637653L;

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getModel(java.lang.Object, javax.media.j3d.TransformGroup)
     */
    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {
        return super.getModel(object, model);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getDeviceColor(es.upm.dit.gsi.shanks.model.element.device.Device)
     */
    public Color getDeviceColor(Device device) {
        String status = device.getCurrentStatus();
        if (status.equals(MyDevice.OK_STATUS)) {
            return Color.green;
        } else if (status.equals(MyDevice.NOK_STATUS)) {
            return Color.red;
        } else if (status.equals(MyDevice.UNKOWN_STATUS)) {
            return Color.gray;
        } else {
            return Color.black;
        }
    }
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getLabelColor(es.upm.dit.gsi.shanks.model.element.device.Device)
     */
    public Color getLabelColor(Device device) {
        String status = device.getCurrentStatus();
        if (status.equals(MyDevice.OK_STATUS)) {
            return Color.blue;
        } else if (status.equals(MyDevice.NOK_STATUS)) {
            return Color.red;
        } else if (status.equals(MyDevice.UNKOWN_STATUS)) {
            return Color.gray;
        } else {
            return Color.black;
        }
    }

}
