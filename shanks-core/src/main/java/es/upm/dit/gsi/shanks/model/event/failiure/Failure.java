package es.upm.dit.gsi.shanks.model.event.failiure;

import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;

/**
 * 
 * @author darofar
 * @author a.carrera
 * @version 0.3.1
 * 
 */

public abstract class Failure extends ProbabilisticNetworkElementEvent {

    private Logger logger = Logger.getLogger(Failure.class.getName());
    private boolean active;
    private boolean resolved;

    public Failure(String id, Steppable generator, double prob) {
        super(id+"_"+System.currentTimeMillis(), generator, prob);
        this.active = false;
        this.resolved = false;
        logger.finer("New Failure: "+this);
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
    }


    /**
     * To know if a failure is resolved, the
     * 
     * @return true if the failure is resolved, false if not
     */
    public boolean isResolved() {
        if(this.resolved){
            return true;
        }
        
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
        return true;
    }
    
    
    
//    public boolean isResolved() {
//        // FIXME check this method, maybe... if two failures affect to one
//        // element at the same time... the old status is a not valid check
//        boolean resolved = false;
//        for (NetworkElement element : this.affectedElements.keySet()) {
//            if (element.getCurrentStatus().equals(
//                    this.oldStatesOfAffectedElements.get(element))) {
//                resolved = true;
//            } else {
//                resolved = false;
//            }
//        }
//        return resolved;
//    }

}
