package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Smartphone;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;


public class Smartphone2DPortrayal extends Device2DPortrayal {

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 5;
        final double height = 5;

     // TODO Adapt the hole thing to HashMap String/boolean. 
        HashMap<String, Boolean> status = device.getStatus();
        if (status.equals(Smartphone.STATUS_OK)) {
            graphics.setColor(Color.green);
        } else if (status.equals(Smartphone.STATUS_OFF)) {
            graphics.setColor(Color.black);
        } else if (status.equals(Smartphone.STATUS_DISCONNECTED)||status.equals(Smartphone.STATUS_DISCHARGED)) {
            graphics.setColor(Color.gray);
        } else if (status.equals(Smartphone.STATUS_OUT_OF_RANGE)) {
        	graphics.setColor(Color.yellow);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillRect(x, y, w, h);

        // Draw the devices ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);

    }
	
	private static final long serialVersionUID = 3567210184105065167L;

}
