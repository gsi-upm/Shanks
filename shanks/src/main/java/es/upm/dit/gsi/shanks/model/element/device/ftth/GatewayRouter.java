package es.upm.dit.gsi.shanks.model.element.device.ftth;

import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * Gateway class
 * 
 * Create the gateways of the scenario
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */
public class GatewayRouter extends Device {
    public GatewayRouter(String id, String status) {
        super(id);
    }

    private int ipConfiguration;
    private int NATProblem;
    private int DHCPProblem;

    public void setIPConf(int ip) {
        ipConfiguration = ip;
    }
    
    

    public void setNATProblem(int nat) {
        NATProblem = nat; // 1 if there is a problem, 0 in the other case
    }

    public void setDHCProblem(int dhc) {
        DHCPProblem = dhc; // 1 if there is a problem, 0 in other case
    }

    public int getIP() {
        return ipConfiguration;
    }

    public int getNATProblem() {
        return NATProblem;
    }

    public int getDHCProblem() {
        return DHCPProblem;
    }

}
