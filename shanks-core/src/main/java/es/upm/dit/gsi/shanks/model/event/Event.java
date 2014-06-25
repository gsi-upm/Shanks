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
package es.upm.dit.gsi.shanks.model.event;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

/**
 * Make the possible events
 * 
 * @author Daniel Lara
 * 
 */

public abstract class Event {

    private String id;
    private Steppable launcher;
    
    /**
     * Constructor of the class
     * 
     * @param id
     * @param launcher
     */
    public Event(String id, Steppable launcher) {
        this.id = id;
        this.launcher = launcher;     
    }
    
    public Event() {
    }

    /**
     * @return the id
     */
    public String getID(){
        return id;
    }

    /**
     * @return the occurrenceProbability
     */
    public Steppable getLauncher(){
        return launcher;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLauncher(Steppable launcher) {
        this.launcher = launcher;
    }



    /**
     * Used to generate the event.
     * 
     * @throws UnsupportedNetworkElementFieldException 
     * @throws UnsupportedScenarioStatusException 
     * 
     */
    public void launchEvent() throws ShanksException {
        this.changeStatus();
        this.changeProperties();
        this.interactWithNE();
        this.generateNotification();
    }

    private void generateNotification() {
        NotificationManager.addNotification(this, this.getClass().getName());
    }
    
    /**
     * Add classes using addPossibleAffectedElements method
     */
    public abstract void addPossibleAffected();

    //TODO we can make a ShanksEventException that mask all individual exceptions. 
    public abstract void addAffectedElement(NetworkElement ne) throws ShanksException ;
    
    public abstract void addAffectedScenario(Scenario scen);
    
    public abstract void changeProperties() throws ShanksException;
    
    public abstract void changeStatus() throws ShanksException;
    
    public abstract void interactWithNE();

    public abstract List<?> getAffected();


}

