/**
 *  es.upm.dit.gsi
 *  30/04/2012
 */
package es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;

/**
 * 
 * @author Danny
 *
 */
public class Computer2DPortrayal extends Device2DPortrayal {
	
	//TODO ¿make an animation for attack in process? 
    protected double width;
    protected double height;
    
    public Computer2DPortrayal() {
		super();
		this.width = Values.Computer2DSide;
		this.height = Values.Computer2DSide;
	}
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;

        HashMap<String, Boolean> status = device.getStatus();
        if (status.get(Computer.STATUS_OK)) {
            graphics.setColor(Color.green);
        } else if (status.get(Computer.STATUS_OFF)) {
            graphics.setColor(Color.gray);
        } else if (status.get(Computer.STATUS_NOK)) {
            graphics.setColor(Color.red);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width/2);
        final int y = (int) (info.draw.y - height/2);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillRect(x, y, w, h);

        // Draw the devices ID
        if(Values.VISIBLE_ID){
	        graphics.setColor(Color.black);
	        graphics.drawString(device.getID(), x - 3, y+1);
        }
    }
    
	private static final long serialVersionUID = -1566002502140466217L;
}
