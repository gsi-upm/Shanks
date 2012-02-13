package es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.GatewayRouter;

import java.awt.Color;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;

public class GatewayRouter3DPortrayal extends Device3DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6098592801212219122L;

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
        if (status.equals(DeviceDefinitions.OK_STATUS)) {
            return Color.green;
        } else if (status.equals(DeviceDefinitions.NOK_STATUS)) {
            return Color.red;
        } else if (status.equals(DeviceDefinitions.UNKOWN_STATUS)) {
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
        if (status.equals(DeviceDefinitions.OK_STATUS)) {
            return Color.blue;
        } else if (status.equals(DeviceDefinitions.NOK_STATUS)) {
            return Color.red;
        } else if (status.equals(DeviceDefinitions.UNKOWN_STATUS)) {
            return Color.gray;
        } else {
            return Color.black;
        }
    }

}
