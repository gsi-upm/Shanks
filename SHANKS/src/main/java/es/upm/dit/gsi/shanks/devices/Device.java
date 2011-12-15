package es.upm.dit.gsi.shanks.devices;

import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.Definitions;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal3d.SimplePortrayal3D;


/**
 * Device class
 * 
 * This is used for create a common Device
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */

public class Device extends SimplePortrayal3D implements Steppable{ 
	
	private static final long serialVersionUID = 5533704302816153428L;
	public Logger log = Logger.getLogger("Device");
    private String id; 
    protected int status; // The status of the device (1 for broken, 0 for healthy)
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



}

