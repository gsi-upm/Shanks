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
package es.upm.dit.gsi.shanks.notification.util.test;

import es.upm.dit.gsi.shanks.notification.Notifable;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class NotifiedString implements Notifable {

    
    /**
     * @param id
     * @param source
     */
    public NotifiedString(String id, Object source) {
        NotificationManager.addNotifable(this);
        this.id = id;
        this.source = source;
    }

    /**
     * the variable identifier.  
     */
    String id;
    
    /**
     * current variable value. 
     */
    String elementValue;
    
    /**
     * the generated notification source.
     */
    Object source;   
    
    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getElementValue() {
        return this.elementValue;
    }

    @Override
    public void setElementValue(Object elementValue) {
        this.elementValue = (String) elementValue;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public void setSource(Object source) {
        this.source = source;
    }
    
    public String get() {
        return (String) this.getElementValue();
    }

    public void set(String value) {
        this.setElementValue(value);
    }

}
