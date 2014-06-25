/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package es.upm.dit.gsi.shanks.model.event.failiure;

import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;

/**
 * 
 * @author darofar
 * @version 0.3.1
 * 
 */

public abstract class Failure extends ProbabilisticNetworkElementEvent {

    private boolean active;
//    private boolean resolved;

    /**
     * ¡¡¡This constructor must be override!!!
     */
    public Failure(Steppable generator, Logger logger) {
        super("_"+System.currentTimeMillis(), generator, null, logger);
        this.active = false;
//        this.resolved = true;
        logger.finer("New Failure: "+this);
    }
    
    public Failure(String id, Steppable generator, double prob, Logger logger) {
        super(id+"_"+System.currentTimeMillis(), generator, prob, logger);
        this.active = false;
//        this.resolved = true;
        logger.finer("New Failure: "+this);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.event.Event#launchEvent()
     */
    @Override
    public void launchEvent() throws ShanksException {
        super.launchEvent();
//        this.resolved = false;
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
//        this.resolved = true;
        this.active = false;
    }

    /**
     * To know if a failure is resolved, the
     * 
     * @return true if the failure is resolved, false if not
     */
    public abstract boolean isResolved();
        
//        if(!this.resolved){
//            for(NetworkElement ne: this.affectedElements){
//                for(String field: this.possibleAffectedElements.get(ne).keySet()){
//                    if(ne.getProperties().containsKey(field)){
//                        if(ne.getProperty(field).equals(this.possibleAffectedElements.get(ne).get(field)))
//                            return false;
//                    } else if(ne.getStatus().containsKey(field)){
//                        if(ne.getStatus().get(field).equals(this.possibleAffectedElements.get(ne).get(field)))
//                            return false;
//                    }
//                }            
//            }
////        for(Scenario sc: this.affectedScenarios){
////            for(String field: this.possibleAffectedScenarios.get(sc).keySet()){
////                if(sc.getCurrentStatus().equals(field)){
////                    return false;
////                }
////            }            
////        }
//        }
//        return true;
//    }
}