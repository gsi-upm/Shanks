package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.network.EdgeDrawInfo2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;


/**
 * Links class
 * 
 * This class draw the connections between the devices 
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */
public class Links extends SimpleEdgePortrayal2D {
	
	
/**
 * Default constructor	
 */
	public Links() {
	}

	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		EdgeDrawInfo2D ei = (EdgeDrawInfo2D) info;
		Edge e = (Edge) object;

		final int startX = (int) ei.draw.x;
		final int startY = (int) ei.draw.y;
		final int endX = (int) ei.secondPoint.x;
		final int endY = (int) ei.secondPoint.y;
		final int midX = (int) (ei.draw.x+ei.secondPoint.x)/2;
		final int midY = (int) (ei.draw.y+ei.secondPoint.y)/2;

		graphics.setColor(Color.black);
		graphics.drawLine(startX, startY, endX, endY);
	}
}
