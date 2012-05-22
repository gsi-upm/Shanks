package es.upm.dit.gsi.shanks.model.event;

import sim.engine.Steppable;

public abstract class ProbabilisticEvent extends Event{

    private double prob;
    
    public ProbabilisticEvent(String name, Steppable generator, double prob) {
        super(name, generator);
        this.prob = prob;
    }

    public double getProb(){
        return prob;
    }
    public void setProb(double prob) {
        this.prob = prob;
    }


}
