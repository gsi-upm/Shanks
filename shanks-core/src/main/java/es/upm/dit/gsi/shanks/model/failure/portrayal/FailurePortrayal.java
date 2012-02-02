package es.upm.dit.gsi.shanks.model.failure.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import es.upm.dit.gsi.shanks.model.failure.Failure;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

public class FailurePortrayal extends SimplePortrayal2D {

    /**
     * 
     */
    private static final long serialVersionUID = -4608431112883034651L;

    /*
     * (non-Javadoc)
     * 
     * @see sim.portrayal.SimplePortrayal2D#draw(java.lang.Object,
     * java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Failure failure = (Failure) object;
        int x = (int) info.draw.x;
        int y = (int) info.draw.y;

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Failure ID ----> "
                + failure.getID(), x, y);
    }

}
