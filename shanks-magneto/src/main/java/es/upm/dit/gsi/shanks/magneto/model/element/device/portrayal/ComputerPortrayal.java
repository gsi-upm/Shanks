package es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;

import es.upm.dit.gsi.shanks.magneto.model.element.device.Computer;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

public class ComputerPortrayal extends Device2DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7215891600767658072L;
	private static final String COMPUTER_PATH = "C:/Users/William Wallace/Universidad/Beca/MAGNETO-SHANKS/shanks-magneto/imagenes/Computer.png";
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;
        
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
       
        HashMap<String, Boolean> status = device.getStatus();
       
        if(status.get(Computer.STATUS_ON)){
        	this.putImage(COMPUTER_PATH, x, y, 2*w, 2*h, graphics);
        }

      
       
        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);
    }


}
