/**
 * es.upm.dit.gsi.shanks
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

/**
 * Portrayal for wireless device implementing a Smartphone.
 *  
 * @author Danny
 *
 */
public class Smartphone2DPortrayal extends Device2DPortrayal {

	private int number;
	public Smartphone2DPortrayal() {
		super();
		this.number=0;
	}

	public Smartphone2DPortrayal(int number) {
		super();
		this.number = number;
	}

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 5;
        final double height = 5;

        HashMap<String, Boolean> status = device.getStatus();
        if (status.get(WirelessDevice.STATUS_OK)) {
            graphics.setColor(Color.green);
        } else if (status.get(WirelessDevice.STATUS_OFF)) {
            graphics.setColor(Color.gray);
        } else if (status.get(WirelessDevice.STATUS_NOK)) {
            graphics.setColor(Color.red);
        } 

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0)*(1+this.number);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillRect(x, y, w, h);

        // Draw the devices ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y+1);

    }
	
	private static final long serialVersionUID = 3567210184105065167L;

}
