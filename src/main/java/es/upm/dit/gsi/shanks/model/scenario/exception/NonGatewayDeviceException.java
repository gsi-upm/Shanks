package es.upm.dit.gsi.shanks.model.scenario.exception;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class NonGatewayDeviceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5956513072567874326L;

    public NonGatewayDeviceException(Scenario scenario, Device gateway,
            Link externalLink) {
        super("Device " + gateway.getID() + " in Scenario " + scenario.getID() + " cannot connecto to Link " + externalLink.getID() + " because the device is not a GatewayDevice");
    }
}
