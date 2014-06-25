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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.test.MyShanksAgentAction;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public class MyFixAgent extends SimpleShanksAgent{

    private int resolved = 0;
    public MyFixAgent(String id, Logger logger) {
        super(id, logger);
        
    }

    @Override
    public void checkMail() {
        
        
    }

    @Override
    public void executeReasoningCycle(ShanksSimulation simulation) {
        int m = 0;
        int n = 0;
        MyShanksAgentAction act = new MyShanksAgentAction("FixAction", this);
        List <NetworkElement> ne = new ArrayList<NetworkElement>();
        for(String s : simulation.getScenario().getCurrentElements().keySet()){
            ne.add(simulation.getScenario().getCurrentElements().get(s));
        }
        for(NetworkElement networkel : ne){
            if(networkel.getStatus().get(MyDevice.OK_STATUS)){
                n++;
            }
        }  
        if(!simulation.getScenario().getCurrentFailures().isEmpty()){
            try {
                act.executeAction(simulation, this, ne);
            } catch (UnsupportedNetworkElementFieldException e) {
                e.printStackTrace();
            } catch (UnsupportedScenarioStatusException e) {
                e.printStackTrace();
            } catch (ShanksException e) {
                e.printStackTrace();
            }
        }
        for(NetworkElement networkel : ne){
            if(networkel.getStatus().get(MyDevice.OK_STATUS)){
                m++;
            }
        }  
        if(m==n){
            resolved++;
        }
        simulation.setNumOfResolvedFailures(resolved);
    }
        

//    @Override
//    public void executeReasoningCycle(ShanksSimulation simulation) {
//        int m = 0;
//        int n = 0;
//        
//        List <NetworkElement> ne = new ArrayList<NetworkElement>();
//        for(String s : simulation.getScenario().getCurrentElements().keySet()){
//            ne.add(simulation.getScenario().getCurrentElements().get(s));
//        }
//        n = ne.size();
//        if(!simulation.getScenario().getCurrentFailures().isEmpty()){
//            for(NetworkElement networkel : ne){
//                if(!networkel.getStatus().get(MyDevice.OK_STATUS)){
//                    networkel.getStatus().put(MyDevice.OK_STATUS, true);
//                    networkel.getStatus().put(MyDevice.NOK_STATUS, false);
//                    resolved++;
//                }
//            }
//            for(NetworkElement networkel : ne){
//                if(networkel.getStatus().get(MyDevice.OK_STATUS)){
//                    m++;
//                }
//            }
//            if(m == n){
//                simulation.setNumOfResolvedFailures(resolved);
//            }
//        }
//        
//    }
    private static final long serialVersionUID = -5688450551132532194L;
}
