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

import jason.asSyntax.Term;

import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.model.event.agent.Action;

/**
 * 
 * Class to represent the actions that are executed by JasonShanksAgents
 * 
 * @author a.carrera
 * 
 */
public abstract class JasonShanksAgentAction extends Action {

    public JasonShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);

    }

    /**
     * Method called when this action instance is required. This method must be
     * override by the user who wants to define a JasonAction.
     * 
     * the following lines of code can be used as advise of the resulting code.
     * 
     * 
     * //JASON -> Terms to NetworkElements convertion. for (Term e : arguments){
     * if (e instanceof StringTermImpl) { StringTermImpl s = (StringTermImpl) e;
     * String name = s.getString();
     * simulation.getScenario().getCurrentElements().get(name); } } //add
     * elements as affected //launch event
     * 
     * 
     * @param simulation
     *            The hole shanks simulation instance, form which the action can
     *            consult parameters or other data that needs to perform his
     *            action.
     * @param agent
     *            The instance of the agent that contains this action.
     * @param arguments
     *            Extra arguments needed to perform the action.
     * @return false or true whether the action has been performed correctly or
     *         not.
     */
    abstract public boolean executeAction(ShanksSimulation simulation,
            ShanksAgent agent, List<Term> arguments);

}
