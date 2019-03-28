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
package es.upm.dit.gsi.shanks.notification;

/**
 * ValueNotifactions are user-made notifications that trace the value of certain 
 * field, variable or whatever it wants to save for consult later. In this case  a 
 * notification has the form:
 * 
 *      source - elementID - value 
 *      
 * @author darofar
 *
 */
public class ValueNotification extends Notification {

    /**
     * The identifier of the element to trace.
     */
    private String elementID = null;
    
    /**
     * Value of the element for the current step of the simulation. This value is only for 
     * the use of the user.  
     */
    private Object value;
    
    /**
     * Default constructor. A ElementValueNotification needs the parameters to set a 
     * Notification plus an element identifier and the current value from that element.
     * 
     * @param id
     * @param when
     * @param source
     * @param elementID
     * @param value
     */
    public ValueNotification(String id, long when, Object source,
            String elementID, Object value) {
        super(id, when, source);
        this.elementID = elementID;
        this.value = value;
    }

    /**
     * @return 
     *          the element identifier. 
     */
    public String getElementID() {
        return elementID;
    }

    /**
     * @return 
     *          the saved value of the element. 
     */
    public Object getValue() {
        return value;
    }

}
