package es.upm.dit.gsi.shanks.model.element.device.portrayal;

import java.util.logging.Logger;

import javax.media.j3d.TransformGroup;

import sim.portrayal3d.SimplePortrayal3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;

public abstract class Device3DPortrayal extends SimplePortrayal3D {


    /**
     * 
     */
    private static final long serialVersionUID = 6319038986129405512L;
    public Logger log = Logger.getLogger(Device3DPortrayal.class.getName());

    public String getLabel(Device device) {
        return device.getID();
    }

    public abstract TransformGroup getModel(Object object, TransformGroup model);

}