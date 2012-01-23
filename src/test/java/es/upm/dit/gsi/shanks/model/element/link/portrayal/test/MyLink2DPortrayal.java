package es.upm.dit.gsi.shanks.model.element.link.portrayal.test;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.network.EdgeDrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

public class MyLink2DPortrayal extends Link2DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7612525235009977268L;

    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
        Edge e = (Edge) object;

        Link link = (Link) e.getInfo();
        if (link.getCapacity() == 2) {
            this.drawSimpleLink(link, object, graphics, info);
        } else if (link.getCapacity() > 2) {
//            this.drawSimpleLink(link, object, graphics, info);
            this.drawComplexLink(link, object, graphics, info);
        }

    }

    private void drawComplexLink(Link link, Object object, Graphics2D graphics,
            DrawInfo2D info) {

        EdgeDrawInfo2D ei = (EdgeDrawInfo2D) info;
        final double width = 20;
        final double height = 20;

        String status = link.getCurrentStatus();

        graphics.setColor(Color.black);
        if (status.equals(MyLink.OK_STATUS)) {
            graphics.setColor(Color.green);
        } else if (status.equals(MyLink.BROKEN_STATUS)) {
            graphics.setColor(Color.red);
        }


        final int startX = (int) ei.draw.x;
        final int startY = (int) ei.draw.y;
        final int endX = (int) ei.secondPoint.x;
        final int endY = (int) ei.secondPoint.y;
        final int midX = (int) (ei.draw.x + ei.secondPoint.x) / 2;
        final int midY = (int) (ei.draw.y + ei.secondPoint.y) / 2;
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.drawLine(startX, startY, endX, endY);
        graphics.fillRect(midX-(int)(width/2), midY-(int)(height/2), w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(link.getID(), midX - 5, midY);
    }

    private void drawSimpleLink(Link link, Object object, Graphics2D graphics,
            DrawInfo2D info) {
        EdgeDrawInfo2D ei = (EdgeDrawInfo2D) info;

        final int startX = (int) ei.draw.x;
        final int startY = (int) ei.draw.y;
        final int endX = (int) ei.secondPoint.x;
        final int endY = (int) ei.secondPoint.y;
        final int midX = (int) (ei.draw.x + ei.secondPoint.x) / 2;
        final int midY = (int) (ei.draw.y + ei.secondPoint.y) / 2;

        graphics.setColor(Color.black);
        String status = link.getCurrentStatus();
        if (status.equals(MyLink.OK_STATUS)) {
            graphics.setColor(Color.green);
        } else if (status.equals(MyLink.BROKEN_STATUS)) {
            graphics.setColor(Color.red);
        }
        graphics.drawLine(startX, startY, endX, endY);

        graphics.setColor(Color.blue);
        graphics.setFont(labelFont);
        int width = graphics.getFontMetrics().stringWidth(link.getID());
        graphics.drawString(link.getID(), midX - width / 2, midY);
    }

}
