/**
 *  es.upm.dit.gsi
 *  30/04/2012
 */
package es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Computer;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

/**
 * 
 * @author Danny
 *
 */
public class Computer2DPortrayal extends Device2DPortrayal {
	
	//TODO ¿make an animation for attack in procces? 
	
	private int number;
    public Computer2DPortrayal() {
		super();
		this.number = 0;
	}
    
    public Computer2DPortrayal(int number) {
		super();
		this.number = number;
	}
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 25;
        final double height = 25;

        HashMap<String, Boolean> status = device.getStatus();
        if (status.get(Computer.STATUS_OK)) {
            graphics.setColor(Color.green);
        } else if (status.get(Computer.STATUS_OFF)) {
            graphics.setColor(Color.gray);
        } else if (status.get(Computer.STATUS_NOK)) {
            graphics.setColor(Color.red);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width/2)*(1+this.number);
        final int y = (int) (info.draw.y - height/2);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillRect(x, y, w, h);

        // Draw the devices ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y+1);
    }
	private static final long serialVersionUID = -1566002502140466217L;
}
