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
 * This interface defines an object that will be saved automatically each time
 * the NotificationManager step its trigger. 
 * 
 * Ha sido creado con la intención de que los usuarios puedan crear variables 
 * tipo pensadas para medir estadisticas en la simulación. dichas variables 
 * implementarán esta interfaz, lo que les otorga el comportamiento automático 
 * de generar una notificación cada vez que el paso NotificationManager se ejecute. 
 * 
 * @author darofar
 */
public abstract interface Notifable {
    
    /**
     * @return 
     *      the variable identifier. 
     */
    abstract public String getID();

    /**
     * @param id 
     *          the variable identifier.
     */
    abstract public void setId(String id);

    /**
     * @return 
     *          the current value of the variable.
     */
    abstract public Object getElementValue();

    /**
     * @param elementValue
     *         sets the current value of the variable to the given value. 
     */
    abstract public void setElementValue(Object elementValue);

    /**
     * @return
     *          the object that originates the variable notification. 
     */
    abstract public Object getSource();
    
    /**
     * @return
     *          the object that originates the variable notification. 
     */
    abstract public void setSource(Object source);

}
