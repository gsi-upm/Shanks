package es.upm.dit.gsi.shanks;

/**
 * Gateway class
 * 
 * Create the gateways of the scenario
 * 
 * @author Daniel Lara
 * @version 0.1
 *
 */
public class Gateway extends Device {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -4404570654854838121L;

	public Gateway(String id, int status, int temperature, int type) {
		super(id, status, temperature, type);
	}
	
    private int ipConfiguration;
    private int NATProblem;
    private int DHCPProblem;
    
    public void setIPConf(int ip){
    	ipConfiguration = ip;
    }
    
    public void setNATProblem(int nat){
    	NATProblem = nat; // 1 if there is a problem, 0 in the other case
    }
    
    public void setDHCProblem (int dhc){
    	DHCPProblem = dhc; //1 if there is a problem, 0 in other case
    }
    
    public int getIP(){
    	return ipConfiguration;
    }
    
    public int getNATProblem(){
    	return NATProblem;
    }
    
    public int getDHCProblem(){
    	return DHCPProblem;
    } 
    
}
