package es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

public class ServerPortrayal extends Device2DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4989129984386602558L;
	private static final String SERVER_PATH = "C:/Users/William Wallace/Universidad/Beca/MAGNETO-SHANKS/shanks-magneto/imagenes/server.png";
	private static final String SERVER_ERROR_PATH = "C:/Users/William Wallace/Universidad/Beca/MAGNETO-SHANKS/shanks-magneto/imagenes/serverError.png";
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;
       
        HashMap<String, Boolean> status = device.getStatus();
        
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        
        if(status.get(Server.STATUS_OK)){
        	this.putImage(SERVER_PATH, x, y, 2*w, 2*h, graphics);
        }else{
        	this.putImage(SERVER_ERROR_PATH, x, y, 2*w, 2*h, graphics);
        }

       
        //If you want put and image use this method, and comment the previous five lines
        
       
        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);
    }
}
