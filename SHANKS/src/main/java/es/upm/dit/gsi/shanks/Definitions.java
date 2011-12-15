package es.upm.dit.gsi.shanks;


/**
 *  Definitions class
 *  
 *  Used for constants
 *  
 *  @author Daniel Lara
 *  @version 0.1
 * 
 */


public class Definitions {
	
	/**It indicates the broken status of a device*/
	public static final int BROKEN_STATE = 1; 	
	
	/**It indicates if a device is working correctly*/
	public static final int HEALTHY_STATUS = 0; 
	
	/**Represents the gateway*/
	public static final int GATEWAY = 0;
	
	/**Represents the OLTs*/
	public static final int OLT = 1;
	
	/**Represents the ONTs*/
	public static final int ONT = 2;
	
	/**Represents the first level of splitters*/
	public static final int SPLITTER1 = 3;
	
	/**Represents the second level of splitters*/
	public static final int SPLITTER2 = 4;	
	

}
