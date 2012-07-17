package es.upm.dit.gsi.shanks.tutorial.model.element.devices.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;

/**
 * @author Daniel Lara
 * 
 * This class is used to represent graphically the computers
 * 
 */

public class Computer2DPortrayal extends Device2DPortrayal{
	
	private static final long serialVersionUID = 2560868713505503360L;

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;
        
        System.out.println("DEVICE: " + device.getID() + " ETHERNET CONNECTADO? " + device.getProperty(Computer.PROPERTY_ETHERNET_CONNECTION));

        HashMap<String, Boolean> status = device.getStatus();
        //if the device is OK green, off is blue and if the computer have problems red
        if (status.get(Computer.STATUS_OK) && !status.get(Computer.STATUS_DISCONNECTED) 
        		&& !status.get(Computer.STATUS_HIGHTEMP) && !status.get(Computer.STATUS_OFF)) {
            graphics.setColor(Color.green);
        } else if ((status.get(Computer.STATUS_HIGHTEMP) || status.get(Computer.STATUS_DISCONNECTED)) && !status.get(Computer.STATUS_OFF) ){
            graphics.setColor(Color.red);
        } else if(status.get(Computer.STATUS_OFF)){
            graphics.setColor(Color.blue);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillOval(x, y, w, h);
        
        //If you want put and image use this method, and comment the previous five lines 
        //this.putImage(path, x, y, w, h, graphics);
        
        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);
    }

}
