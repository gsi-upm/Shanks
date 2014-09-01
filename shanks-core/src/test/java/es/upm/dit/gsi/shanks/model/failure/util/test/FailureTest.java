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
package es.upm.dit.gsi.shanks.model.failure.util.test;

import java.util.List;
import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.event.failure.Failure;

/**
 * @author a.carrera
 *
 */
public class FailureTest extends Failure {

    public FailureTest(Steppable launcher, Logger logger) {
        this(FailureTest.class.getName(), launcher, 0.0, logger);
    }
    
    public FailureTest(Steppable launcher, double prob, Logger logger) {
        this(FailureTest.class.getName(), launcher, prob, logger);
    }
    
    public FailureTest(String id, Steppable launcher, double prob, Logger logger) {
        super(FailureTest.class.getName(), launcher, prob, logger);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
     */
    @Override
    public boolean isResolved() {
        List<NetworkElement> affectedElements = this.getCurrentAffectedElements();
        boolean resolved = false;
        for (NetworkElement element : affectedElements) {
            if (element instanceof MyDevice) {
                // Checking status / properties of the network element
                if (!element.getStatus().get(MyDevice.HIGH_TEMP_STATUS)) {
                    resolved = true;
                } else {
                    resolved = false;
                    break;
                }
            } else if (element instanceof MyLink) {
                // Checking status / properties of the network element
                if(element.getStatus().get(MyLink.OK_STATUS)) {
                    resolved = true;
                } else {
                    resolved = false;
                    break;
                }
            }
        }
        return resolved;
    }

    @Override
    public void changeOtherFields()
            throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedElementState(MyDevice.class, MyDevice.OK_STATUS, false);
        this.addPossibleAffectedElementState(MyDevice.class, MyDevice.NOK_STATUS, false);
        this.addPossibleAffectedElementState(MyDevice.class, MyDevice.HIGH_TEMP_STATUS, true);
        this.addPossibleAffectedElementState(MyLink.class, MyLink.BROKEN_STATUS, false);
        this.addPossibleAffectedElementState(MyLink.class, MyDevice.OK_STATUS, false);
        this.addPossibleAffectedElementProperty(MyDevice.class, MyDevice.TEMPERATURE_PROPERTY, 80.0);
    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }



}
