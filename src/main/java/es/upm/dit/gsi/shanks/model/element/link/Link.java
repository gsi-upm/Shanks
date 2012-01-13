package es.upm.dit.gsi.shanks.model.element.link;

import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * Link class
 * 
 * This class represents the links than connect the different devices
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public class Link extends NetworkElement {
    private List<Device> linkedDevices;

    public Link(String id) {
        super(id);
    }

    public List<Device> getLinkedDevices() {
        return linkedDevices;
    }

    //TODO add devices
    
}
