package es.upm.dit.gsi.shanks.model.element.link.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.network.EdgeDrawInfo2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public abstract class Link2DPortrayal extends SimpleEdgePortrayal2D {

    /**
     * 
     */
    private static final long serialVersionUID = 8224336342428392184L;

    
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
        EdgeDrawInfo2D ei = (EdgeDrawInfo2D) info;
        Edge e = (Edge) object;

        final int startX = (int) ei.draw.x;
        final int startY = (int) ei.draw.y;
        final int endX = (int) ei.secondPoint.x;
        final int endY = (int) ei.secondPoint.y;
        final int midX = (int) (ei.draw.x + ei.secondPoint.x) / 2;
        final int midY = (int) (ei.draw.y + ei.secondPoint.y) / 2;

        graphics.setColor(Color.black);
        graphics.drawLine(startX, startY, endX, endY);
        
        Device from = (Device) e.getFrom();
        Device to = (Device) e.getTo();
        
        for (Link linkFrom : from.getLinks()) {
            for (Link linkTo : to.getLinks()) {
                if (linkFrom.equals(linkTo)) {
                    graphics.drawString(linkFrom.getID(), midX, midY);
                }
            }
        }

    }
}
