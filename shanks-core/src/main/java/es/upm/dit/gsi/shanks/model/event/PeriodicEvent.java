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

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public abstract class PeriodicEvent extends Event{

    private int period;
    
    public PeriodicEvent(String id, Steppable launcher, int period) {
        super(id, launcher);
        this.period = period;
    }

    public int getPeriod(){
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    
    public abstract void addPossibleAffected();

    public abstract void addAffectedElement(NetworkElement ne);
    
    public abstract void addAffectedScenario(Scenario scen);

    public abstract void changeProperties() throws ShanksException;
 
    public abstract void changeStatus() throws ShanksException;

    public abstract void interactWithNE();

}
