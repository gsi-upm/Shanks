package es.upm.dit.gsi.shanks.model.failure.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import es.upm.dit.gsi.shanks.model.failure.Failure;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

public class Failure2DPortrayal extends SimplePortrayal2D {

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

        Failure failure = (Failure) object;;
        final double width = 5;
        final double height = 5;

        // Draw the devices
        int x = (int) (info.draw.x - width / 2.0);
        int y = (int) (info.draw.y - height / 2.0);
        int w = (int) (width);
        int h = (int) (height);        
        graphics.setColor(Color.gray);
        graphics.fillOval(x, y-2, w, h);
        x = (int) ((int) info.draw.x + (width / 2.0));
        y = (int) info.draw.y;

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Failure ID ----> "
                + failure.getID(), x+3, y+3);
    }

}
