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
package es.upm.dit.gsi.shanks.agent.action.test;

import jason.asSyntax.Term;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.JasonShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.test.MyJasonShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.event.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class MyJasonShanksAgentAction extends JasonShanksAgentAction {

    public MyJasonShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);
        // TODO Auto-generated constructor stub
    }

    public static final String FIX = "fix";
    private Logger logger = Logger
            .getLogger(Logger.GLOBAL_LOGGER_NAME);

    public boolean executeAction(ShanksSimulation simulation,
            ShanksAgent agent, List<Term> arguments) {

        Set<Failure> failures = simulation.getScenario().getCurrentFailures();
        int number = failures.size();
        if (number==0) {
            return true;
        }
        int random = simulation.random.nextInt(number);
        Failure f = (Failure) failures.toArray()[random];
        List<NetworkElement> elements = f.getCurrentAffectedElements();
        for (NetworkElement element : elements){
            Class<? extends NetworkElement> c = element.getClass();
            if (c.equals(MyDevice.class)) {
                element.getProperties().put(MyDevice.TEMPERATURE_PROPERTY, 25);
//                element.getStatus().put(MyDevice.OK_STATUS, true);
//                element.getStatus().put(MyDevice.NOK_STATUS, false);
//                element.getStatus().put(MyDevice.HIGH_TEMP_STATUS, false);
            } else if (c.equals(MyLink.class)) {
                element.getProperties().put(MyLink.DISTANCE_PROPERTY, 3.5);
//                element.getStatus().put(MyLink.OK_STATUS, true);
//                element.getStatus().put(MyLink.BROKEN_STATUS, false);
            }
        }


        ((MyJasonShanksAgent) agent).incrementNumberOfResolverFailures();
        // END OF THE ACTION
        simulation.getScenarioManager().checkFailures(simulation);
        logger.finer("Number of current failures: "
                + simulation.getScenario().getCurrentFailures().size());
        return true;
    }

    @Override
    public void addPossibleAffected() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAffectedScenario(Scenario scen) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
