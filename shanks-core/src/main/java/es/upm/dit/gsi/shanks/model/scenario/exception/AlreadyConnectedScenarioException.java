package es.upm.dit.gsi.shanks.model.scenario.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class AlreadyConnectedScenarioException extends ShanksException {

    public AlreadyConnectedScenarioException(Scenario scenario, Device gateway,
            Link externalLink) {
        super("Scenario " + scenario.getID() + " is already conneted to Link " + externalLink.getID() + " through GatewayDevice " + gateway.getID());
    }

    private static final long serialVersionUID = -8820859290215327876L;
}
