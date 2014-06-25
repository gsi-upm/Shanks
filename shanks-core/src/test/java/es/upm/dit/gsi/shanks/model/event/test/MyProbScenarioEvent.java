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
package es.upm.dit.gsi.shanks.model.event.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.scenario.ProbabilisticScenarioEvent;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class MyProbScenarioEvent extends ProbabilisticScenarioEvent{

    public MyProbScenarioEvent(Steppable generator) {
        super(MyProbScenarioEvent.class.getName(), generator, 0.50);
       
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedStatus(MyScenario.class, MyScenario.CLOUDY);
        
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }

}
