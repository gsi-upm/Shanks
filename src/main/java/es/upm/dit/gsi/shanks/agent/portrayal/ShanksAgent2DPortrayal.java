package es.upm.dit.gsi.shanks.agent.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;

public abstract class ShanksAgent2DPortrayal extends SimplePortrayal2D {

    /**
     * 
     */
    private static final long serialVersionUID = -5132594800897540031L;

    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        ShanksAgent agent = (ShanksAgent) object;
        final double width = 20;
        final double height = 20;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);        
        graphics.setColor(Color.gray);
        graphics.fillOval(x, y, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(agent.getID(), x - 3, y);

    }
}
