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
package es.upm.dit.gsi.shanks.model.element.link.test;

import java.util.HashMap;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 *
 */
public class MyLink extends Link {

    public static final String OK_STATUS = "OK";
    public static final String BROKEN_STATUS = "BROKEN";
    
    public static final String DISTANCE_PROPERTY = "Distance";
    public static final String LINK_TYPE_PROPERTY = "LinkType";
    
    /**
     * @param id
     * @param initialState
     * @param capacity
     * @throws UnsupportedNetworkElementFieldException
     */
    public MyLink(String id, String initialState, int capacity, Logger logger) throws ShanksException {
        super(id, initialState, capacity, logger);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyLink.OK_STATUS);
        this.addPossibleStatus(MyLink.BROKEN_STATUS);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws ShanksException {
        HashMap<String, Boolean> status = this.getStatus();
        if (status.get(MyLink.BROKEN_STATUS)) {
            this.changeProperty(MyLink.DISTANCE_PROPERTY, 0.0);
        } else if (status.get(MyLink.OK_STATUS)) {
            this.changeProperty(MyLink.DISTANCE_PROPERTY, 3.5);
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(MyLink.LINK_TYPE_PROPERTY, "Ethernet");
        this.addProperty(MyLink.DISTANCE_PROPERTY, 3.5);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws ShanksException {
        Double distance = (Double) this.getProperty(MyLink.DISTANCE_PROPERTY);
        if (distance>0){
            this.updateStatusTo(MyLink.BROKEN_STATUS, false);
            this.updateStatusTo(MyLink.OK_STATUS, true);
        }
    }

}
