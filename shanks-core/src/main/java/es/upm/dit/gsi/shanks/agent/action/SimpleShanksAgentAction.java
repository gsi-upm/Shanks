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
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.action;

import java.util.ArrayList;
import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.event.agent.Action;

/**
 * Class that represents action that can be executed by a Shanks Agent.
 * 
 * @author
 */
public abstract class SimpleShanksAgentAction extends Action {

    /**
     * List of network elements that can be affected by the action execution.
     */
    public List<NetworkElement> affected;

    /**
     * 
     * @param id
     *            Action Identifier.
     * @param launcher
     *            Steppable responsible of launching th action. Usually will be
     *            the agent that has the action, but can be any other steppable.
     */
    public SimpleShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);
        this.affected = new ArrayList<NetworkElement>();
    }

    /**
     * Method called when the action has to be performed.
     * 
     * @param simulation
     *            The hole shanks simulation instance, form which the action can
     *            consult parameters or other data that needs to perform his
     *            action.
     * @param agent
     *            The instance of the agent that contains this action.
     * @param arguments
     *            Extra arguments needed to perform the action.
     * @throws ShanksException
     *             An exception may be thrown due to problems trying to changing
     *             the states or properties of affected elements.
     */
    public void executeAction(ShanksSimulation simulation, ShanksAgent agent,
            List<NetworkElement> arguments) throws ShanksException {
        for (NetworkElement ne : arguments) {
            this.addAffectedElement(ne);
        }
        this.launchEvent();
    }
}
