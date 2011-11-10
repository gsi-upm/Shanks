package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

/**
 * DeviceErrors class
 * 
 * Make the possible erros of the devices
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */

public class DeviceErrors extends SimplePortrayal2D {
	
	/**DeviceErrors parametres and diferent types of errors*/
	private static final long serialVersionUID = -5684572432145540188L;
	public String name;
	public boolean trigger;
	public static DeviceErrors ipconf;
	public static DeviceErrors natproblem;
	public static DeviceErrors dhcproblem;
	public static DeviceErrors laser;
	public static DeviceErrors bitrate;
	public static DeviceErrors connection;
	
	public static List<DeviceErrors> deverrors = new ArrayList<DeviceErrors>();
	
	
	/**
	 * Constructor of the class
	 * @param name	The name of the error
	 * @param trigger TRUE if the error happens, FALSE in the other case
	 */
	public DeviceErrors(String name, boolean trigger){
		this.name = name;
		this.trigger = trigger;			
	}
	
	/**
	 * Put the name to the error
	 * @param name The name of the error
	 */
	public void setName(String name){
		this.name = name;	
	}
	
	/**
	 * Give the name of the error
	 * @return name The name of the error
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Used to indicate if an error is happening
	 * @param trigger
	 */
	public void setTrigger(boolean trigger){
		this.trigger = trigger;
	}
	
	public boolean getTrigger(){
		return trigger;
	}
	
	/**
	 * This method is used to create predefined errors
	 *
	 */
	public static void createDeviceErrors(){
		ipconf = new DeviceErrors("IPCongifuration Problem", false);
		natproblem = new DeviceErrors("NAT Problem", false);
		dhcproblem = new DeviceErrors("DHC Problem", false); 
		laser = new DeviceErrors("Laser Problem", false);
		bitrate = new DeviceErrors("Bit rate Problem", false);
		connection = new DeviceErrors("Connection Problem", false);
		deverrors.add(ipconf);
		deverrors.add(natproblem);
		deverrors.add(dhcproblem);
		deverrors.add(laser);
		deverrors.add(bitrate);
		deverrors.add(connection);
	}
	
	/**
	 * This method return the trigger of a DeviceError
	 * @param de The Device Error that we can see its trigger
	 * @return TRUE if the error is happening, FALSE in other case
	 */
	public static boolean getProblemStatus(DeviceErrors de){
		return de.getTrigger();
	}
	
	
	/**
	 * Set what device is broken depending of the error
	 * 
	 */
	public static void setDeviceWithProblems(){
		if(getProblemStatus(ipconf) || getProblemStatus(natproblem) || getProblemStatus(dhcproblem)){
			for(Device d: Model.ftth){
				if(d.getType() == Definitions.GATEWAY){
					d.setStatus(1);
				}
			}
		}
		if(getProblemStatus(bitrate)){
			for(Device d : Model.ftth){
				if(d.getType() == Definitions.OLT || d.getType()==Definitions.ONT){
					d.setStatus(1);
				}
			}
		}
		if(getProblemStatus(laser)){
			for(Device d : Model.ftth){
				if(d.getType() == Definitions.OLT || d.getType()==Definitions.ONT){
					d.setStatus(1);
				}
			}
		}
		if(getProblemStatus(connection)){
			for(Device d : Model.ftth){
				d.setStatus(1);
			}
		}
	}

	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

		final double width = 20;
		final double height = 20;		

		// Draw the devices
		final int x = (int) (info.draw.x - width / 2.0);
		final int y = (int) (info.draw.y - height / 2.0);
		final int w = (int) (width);
		final int h = (int) (height);
		
//		graphics.setColor(Color.black);
//		graphics.fillOval(x, y, w, h);

		// Draw the devices ID ID
		graphics.setColor(Color.black);
		graphics.drawString(ScenarioManager.dev.getName(), x - 3, y);
	}


}
	
	

