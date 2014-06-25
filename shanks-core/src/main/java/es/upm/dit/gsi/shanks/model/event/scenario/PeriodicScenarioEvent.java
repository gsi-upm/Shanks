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
package es.upm.dit.gsi.shanks.model.event.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.PeriodicEvent;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public abstract class PeriodicScenarioEvent extends PeriodicEvent {
    private List<Scenario> affectedScenarios;
    private HashMap<Class<? extends Scenario>, String> possibleAffected;

    public PeriodicScenarioEvent(String name, Steppable generator, int period) {
        super(name, generator, period);

        this.affectedScenarios = new ArrayList<Scenario>();
        this.possibleAffected = new HashMap<Class<? extends Scenario>, String>();

        this.addPossibleAffected();

    }

    public abstract void addPossibleAffected();

    public void changeProperties()
            throws UnsupportedNetworkElementFieldException {

    }

    @Override
    public void changeStatus() throws ShanksException {
        List<? extends Scenario> scenarios = this.affectedScenarios;
        for (Scenario scen : scenarios) {
            for (Class<?> c : possibleAffected.keySet()) {
                if (c.equals(scen.getClass())) {
                    scen.setCurrentStatus(possibleAffected.get(c));
                }
            }
        }

    }

    public abstract void interactWithNE();

    public void addAffectedScenario(Scenario s) {
        affectedScenarios.add(s);
    }

    public void addAffectedElement(NetworkElement ne) {

    }

    /**
     * @return the currentAffectedElements if the failure is active, null if not
     */
    public List<Scenario> getCurrentAffectedScenarios() {
        return affectedScenarios;
    }

    /**
     * Remove this element, but not modify the status. When the failure will be
     * deactive, this removed element will keep the actual status
     * 
     * @param element
     */
    public void removeAffectedElement(Scenario scen) {
        this.affectedScenarios.remove(scen);
    }

    /**
     * @return the possibleAffectedElements
     */
    public HashMap<Class<? extends Scenario>, String> getPossibleAffectedScenarios() {
        return possibleAffected;
    }

    public void addPossibleAffectedStatus(Class<? extends Scenario> c,
            String state) {
        this.possibleAffected.put(c, state);
    }

    /**
     * @param elementClass
     */
    public void removePossibleAffectedElements(
            Class<? extends NetworkElement> elementClass) {
        if (this.possibleAffected.containsKey(elementClass)) {
            this.possibleAffected.remove(elementClass);
        }
    }

    /**
     * 
     */
    public List<?> getAffected() {
        return this.getCurrentAffectedScenarios();
    }

}
