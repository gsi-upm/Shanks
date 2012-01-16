package es.upm.dit.gsi.shanks.model.failure.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

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

        final double width = 20;
        final double height = 20;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Problem generated ----> "
                + this.getClass().getName(), x - 3, y);
    }

}
