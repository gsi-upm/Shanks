package es.upm.dit.gsi.shanks.model.element.device.portrayal;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.element.device.Device;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

public abstract class Device2DPortrayal extends SimplePortrayal2D {

    /**
     * 
     */
    private static final long serialVersionUID = -5132594800897540031L;
    public Logger log = Logger.getLogger(Device2DPortrayal.class.getName());

    public String getLabel(Device device) {
        return device.getID();
    }
    
    public abstract void draw(Object object, Graphics2D graphics, DrawInfo2D info);

}
