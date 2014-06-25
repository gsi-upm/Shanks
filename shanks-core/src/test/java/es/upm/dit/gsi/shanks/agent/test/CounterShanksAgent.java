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
package es.upm.dit.gsi.shanks.agent.test;

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;

/**
 * 
 * Simple agent, simply counts steps in the simulation.
 * 
 * @author Alberto Mardomingo
 */
public class CounterShanksAgent extends SimpleShanksAgent{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7824028426007073312L;

    public CounterShanksAgent(String id, Logger logger) {
        super(id, logger);
    }

    @Override
    public void checkMail() {
        // Do nothing
    }

    @Override
    public void executeReasoningCycle(ShanksSimulation simulation) {
        if( simulation instanceof MyShanksSimulation) ;
            ((MyShanksSimulation)simulation).increaseCounter();
    }
}
