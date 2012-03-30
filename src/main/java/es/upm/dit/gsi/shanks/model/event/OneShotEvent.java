package es.upm.dit.gsi.shanks.model.event;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public abstract class OneShotEvent extends Event{

    public OneShotEvent(String id, Steppable launcher) {
        super(id, launcher);
    }

}
