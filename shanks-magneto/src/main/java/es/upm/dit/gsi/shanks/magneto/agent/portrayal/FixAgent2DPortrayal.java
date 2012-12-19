package es.upm.dit.gsi.shanks.magneto.agent.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;

import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.portrayal.ShanksAgent2DPortrayal;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgent;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgentBecario;

public class FixAgent2DPortrayal extends ShanksAgent2DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5002663845504302383L;

	private static final String AGENT_IMAGE_PATH = "C:/Users/William Wallace/Universidad/Beca/MAGNETO-SHANKS/shanks-magneto/imagenes/worker.jpg";
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        SimpleShanksAgent agent = (SimpleShanksAgent) object;
        
        final double width = 5;
        final double height = 5;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);        
        this.putImage(AGENT_IMAGE_PATH, x, y, w*3, h*4, graphics);

        // Draw the devices ID ID
        graphics.setColor(Color.blue);
        graphics.drawString(agent.getID(), x - 3, y);

    }
}
