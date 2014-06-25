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
package es.upm.dit.gsi.shanks.model.element.device.portrayal.test;

import java.awt.Color;
import java.util.HashMap;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;

/**
 * @author a.carrera
 *
 */
@SuppressWarnings("restriction")
public class MyDevice3DPortrayal extends Device3DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7701586034482637653L;

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getModel(java.lang.Object, javax.media.j3d.TransformGroup)
     */
    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {
        return super.getModel(object, model);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getDeviceColor(es.upm.dit.gsi.shanks.model.element.device.Device)
     */
    public Color getDeviceColor(Device device) {
        HashMap<String, Boolean> status = device.getStatus();
        if (status.get(MyDevice.OK_STATUS)) {
            return Color.green;
        } else if (status.get(MyDevice.NOK_STATUS)) {
            return Color.red;
        } else if (status.get(MyDevice.HIGH_TEMP_STATUS)) {
            return Color.gray;
        } else {
            return Color.black;
        }
    }
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getLabelColor(es.upm.dit.gsi.shanks.model.element.device.Device)
     */
    public Color getLabelColor(Device device) {
        HashMap<String, Boolean> status = device.getStatus();
        if (status.equals(MyDevice.OK_STATUS)) {
            return Color.blue;
        } else if (status.equals(MyDevice.NOK_STATUS)) {
            return Color.red;
        } else if (status.equals(MyDevice.HIGH_TEMP_STATUS)) {
            return Color.gray;
        } else {
            return Color.black;
        }
    }

}
