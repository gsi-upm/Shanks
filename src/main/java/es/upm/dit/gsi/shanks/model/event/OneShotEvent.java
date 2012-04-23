package es.upm.dit.gsi.shanks.model.event;

import sim.engine.Steppable;

public abstract class OneShotEvent extends Event{

    public OneShotEvent(String id, Steppable launcher) {
        super(id, launcher);
    }

}
