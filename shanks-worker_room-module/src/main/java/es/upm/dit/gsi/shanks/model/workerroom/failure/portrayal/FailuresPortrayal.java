package es.upm.dit.gsi.shanks.model.workerroom.failure.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import sim.portrayal.SimplePortrayal2D;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure2DPortrayal;

public class FailuresPortrayal extends SimplePortrayal2D implements Portrayal{

	private static final long serialVersionUID = 8824600605053073060L;
	
	
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

        int x = (int) (info.draw.x - width / 2.0);
        int y = (int) (info.draw.y - height / 2.0);
        graphics.setColor(Color.black);
        graphics.drawString("Failure ID ----> "
                + failure.getID(), x+3, y+3);
    }
	
}
