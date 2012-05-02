package es.upm.dit.gsi.shanks.hackerhan.agent.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.portrayal.ShanksAgent2DPortrayal;

public class HackeAgent2DPortrayal extends ShanksAgent2DPortrayal{

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
        graphics.setColor(Color.darkGray);
        graphics.fillOval(x, y, w, h);

        // Draw the devices ID
        graphics.setColor(Color.black);
        graphics.drawString(agent.getID(), x - 3, y+1);

    }
	
	private static final long serialVersionUID = -3437575479029360784L;
}
