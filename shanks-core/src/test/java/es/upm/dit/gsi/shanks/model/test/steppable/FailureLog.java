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
 * 
 */
package es.upm.dit.gsi.shanks.model.test.steppable;

import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.JasonShanksAgent;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.test.MyJasonShanksAgent;

/**
 * @author a.carrera
 *
 */
public class FailureLog implements Steppable {


    public Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    /**
     * 
     */
    private static final long serialVersionUID = 2669002521740395423L;

    @Override
    public void step(SimState sim) {
        ShanksSimulation simulation = (ShanksSimulation) sim;
        for (ShanksAgent agent : simulation.getAgents()) {
            if (agent instanceof JasonShanksAgent) {
                logger.info("Total failures resolved by Agent: "
                        + agent.getID()
                        + ": "
                        + ((MyJasonShanksAgent) agent)
                                .getNumberOfResolvedFailures());
            }
        }
    }

}
