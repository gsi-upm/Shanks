package es.upm.dit.gsi.shanks.model.failure;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

/**
 * DeviceErrors class
 * 
 * Make the possible erros of the devices
 * 
 * @author Daniel Lara
 * @author a.carrera
 * @version 0.1.1
 * 
 */

public abstract class Failure extends SimplePortrayal2D {

    //TOIMP hace que pueda haber fallos complejos igual que scenarios complejos
    
    
    /** DeviceErrors parametres */
    private static final long serialVersionUID = -5684572432145540188L;
    public String type;
    public boolean activated;

    public static List<NetworkElement> deverrors = new ArrayList<NetworkElement>();
// TOIMP create NetworkElement class
    
    /**
     * Constructor of the class
     * 
     * @param name
     *            The type of the error
     */
    public Failure(String type) {
        this.type = type;
        this.activated = false;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Used to activate a failure
     * 
     */
    public void activateFailure() {
        this.activated = true;
    }

    /**
     * Used to deactivate a failure
     * 
     */
    public void deactivateFailure() {
        this.activated = false;
    }

    /**
     * @return
     */
    public boolean getStatus() {
        return activated;
    }

    /* (non-Javadoc)
     * @see sim.portrayal.SimplePortrayal2D#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        final double width = 20;
        final double height = 20;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Problem generated ----> " + this.getType(), x - 3,
                y);
    }

    // TODO Aquí antes se generaban todos los errores, ahora se deben generar en
    // otra clase

}
