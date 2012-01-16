package es.upm.dit.gsi.shanks.model.element.link.portrayal;

import java.io.Serializable;

import sim.util.MutableDouble;

/**
 * Links class
 * 
 * This class draw the connections between the devices
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */
public abstract class LinkPortrayal extends MutableDouble implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3575152597887827354L;

    /**
     * Default constructor
     */
    public LinkPortrayal() {
    }
}
