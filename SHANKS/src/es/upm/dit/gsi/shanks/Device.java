package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ImageIcon;


import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;


/**
 * Device class
 * 
 * This is used for create a common Device
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */

public class Device extends SimplePortrayal2D implements Steppable{ 
	
	private static final long serialVersionUID = 5533704302816153428L;
	public Logger log = Logger.getLogger("Device");
    private String id; 
    private int status; // The status of the device (1 for broken, 0 for healthy)
    private int temperature; 
    private List<Link> linkList; //The different devices that are connected
    private int linkNumber; 
    private int type; //The type of the device

    public Device(String id, int status, int temperature, int type){
    	this.id = id;
    	this.status = status;
    	this.temperature = temperature;
    	this.type = type;
    }
    
    
/**
 *	Return the status of the device
 * 
 * @return status The state of the device
 */
    public int getStatus(){
    	return status;
    }
   
    
 /**
  *	Return the id of the device
  * 
  * @return id The id of the device
  */
    public String getID(){
    	return id;
    }
    
    
/**
 * Return the temperature of the device
 *    
 * @return temperature The temeprature of the device
 */
    public int getTemperature(){
    	return temperature;
    }

    
    
/**
 * Return the connections between the device
 * 
 * @return linkList A list with the different connections
 */
    public List<Link> getLinks(){
    	return linkList;
    }
  
/**
 * Return the link number of the device
 * 
 * @return linkNumber The number of the link
 */
    public int getLinkNumber(){
    	return linkNumber;
    }
 
    
/**
 * Change the status of the device 
 *   
 * @param status The new state of the device
 */
    public void setStatus(int status){
    	this.status = status;
    }

    
/**
 * Return the type of the device
 * 
 * @see Definitions
 * @return type The type of the device
 */
    public int getType(){
    	return type;
    }
  
    
/**
 * Change the type of the Device
 * 
 * @param t The type of the device
 */
    public void setType(int t){
    	this.type = t;
    }

	public void step(SimState state) {
		
	}

	
/**
 * Load an Image in order to use it in the GUI
 * @param filename The direction of the image
 * @return An ImageIcon with the image
 */
	public static Image loadImage(String filename){ 
	    return new ImageIcon(Model.class.getResource(filename)).getImage(); 
	    }

	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

		final double width = 20;
		final double height = 20;
		
		//If they are broken the platform draw theme in red, if they are OK draw them in green
		switch(this.status){
		case Definitions.BROKEN_STATE:
			graphics.setColor(Color.red);
			break;
		case Definitions.HEALTHY_STATUS:
			graphics.setColor(Color.green);
			break;
		}
		

		// Draw the devices
		final int x = (int) (info.draw.x - width / 2.0);
		final int y = (int) (info.draw.y - height / 2.0);
		final int w = (int) (width);
		final int h = (int) (height);
		graphics.fillOval(x, y, w, h);

		// Draw the devices ID ID
		graphics.setColor(Color.black);
		Device d = ((Device) (object));
		log.info("DEVICE A DIBUJAR " + d);
		log.info("ID DEL DEVICE "+ d.getID());
		String id = d.getID();
		log.info("DIBUJA " + id);
		graphics.drawString(id, x - 3, y);

	}

}

