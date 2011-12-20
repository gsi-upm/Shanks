package es.upm.dit.gsi.shanks.agent;

import es.upm.dit.gsi.shanks.model.device.Device;

/**
 * Message class
 * 
 * This class represent the messages send between the agents (it isn't in use
 * yet)
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

// This class is not in use yet
public class Message {
    private Device sender;

    public Message(Device sender) {
        this.sender = sender;
    }

    public Device getSender() {
        return sender;
    }

}
