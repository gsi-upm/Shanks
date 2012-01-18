package es.upm.dit.gsi.shanks.model.element.device.portrayal;

import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

public abstract class Device2DPortrayal extends SimplePortrayal2D {

    /**
     * 
     */
    private static final long serialVersionUID = -5132594800897540031L;
    
    abstract public void draw(Object object, Graphics2D graphics, DrawInfo2D info);
}
