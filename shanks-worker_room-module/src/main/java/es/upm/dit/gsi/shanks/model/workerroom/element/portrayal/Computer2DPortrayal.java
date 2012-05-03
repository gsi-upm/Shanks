package es.upm.dit.gsi.shanks.model.workerroom.element.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Computer;

public class Computer2DPortrayal extends Device2DPortrayal implements Portrayal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3822126119695392777L;

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        HashMap<String, Boolean> status = device.getStatus();
        List<String> okStatus = new ArrayList<String>();
        List<String> nokStatus = new ArrayList<String>();
        
        for(String s : status.keySet()){
            if(s.equals(Computer.STATUS_OK)){
            	if(status.get(s))
            		okStatus.add(s);
            }
        }
        if (okStatus.size() == 1) {
            graphics.setColor(Color.green);
        } else{
            graphics.setColor(Color.red); 
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillOval(x, y, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);

    }
}
