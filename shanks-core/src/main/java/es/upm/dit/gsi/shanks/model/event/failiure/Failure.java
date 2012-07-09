package es.upm.dit.gsi.shanks.model.event.failiure;

import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;

/**
 * 
 * @author darofar
 * @version 0.3.1
 * 
 */

public abstract class Failure extends ProbabilisticNetworkElementEvent {

    private Logger logger = Logger.getLogger(Failure.class.getName());
    private boolean active;
    private boolean resolved;

    /**
     * ¡¡¡This constructor must be override!!!
     */
    public Failure(Steppable generator) {
        super("_"+System.currentTimeMillis(), generator, null);
        this.active = false;
        this.resolved = true;
        logger.finer("New Failure: "+this);
    }
    
    public Failure(String id, Steppable generator, double prob) {
        super(id+"_"+System.currentTimeMillis(), generator, prob);
        this.active = false;
        this.resolved = true;
        logger.finer("New Failure: "+this);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.event.Event#launchEvent()
     */
    @Override
    public void launchEvent() throws ShanksException {
        super.launchEvent();
        this.resolved = false;
        this.active = true;
    }

    /**
     * @return true if failure is active, false if not.
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * 
     */
    public void setAsResolved(){
        this.resolved = true;
        this.active = false;
    }

    /**
     * To know if a failure is resolved, the
     * 
     * @return true if the failure is resolved, false if not
     */
    public boolean isResolved() {
        
        if(!this.resolved){
            for(NetworkElement ne: this.affectedElements){
                for(String field: this.possibleAffectedElements.get(ne).keySet()){
                    if(ne.getProperties().containsKey(field)){
                        if(ne.getProperty(field).equals(this.possibleAffectedElements.get(ne).get(field)))
                            return false;
                    } else if(ne.getStatus().containsKey(field)){
                        if(ne.getStatus().get(field).equals(this.possibleAffectedElements.get(ne).get(field)))
                            return false;
                    }
                }            
            }
//        for(Scenario sc: this.affectedScenarios){
//            for(String field: this.possibleAffectedScenarios.get(sc).keySet()){
//                if(sc.getCurrentStatus().equals(field)){
//                    return false;
//                }
//            }            
//        }
        }
        return true;
    }
}