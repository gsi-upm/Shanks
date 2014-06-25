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

import java.util.List;

/**
 * A InteractionNotification store information of Events, cause in SHANKS all interactions
 * between two elements are defined by them. This information have a relational
 * entity with the form:
 * 
 *      source => interaction => target
 * 
 * @author darofar
 *
 */
public class InteractionNotification extends Notification {
    
    /**
     * Name of the class that define the interaction itself. 
     */
    private String interaction = null;
    
    /**
     * Interaction's target objects.
     */
    private List<Object> target = null;
    
    /**
     * Default constructor. A InteractionNotification needs the parameters to set up a 
     * notification plus a the name of the class that defines the interaction and the 
     * target object that the interactions affect. 
     *   
     * @param id
     * @param when
     * @param source
     * @param interaction
     * @param target
     */
    public InteractionNotification(String id, long when, Object source,
            String interaction, List<Object> target) {
        super(id, when, source);
        this.interaction = interaction;
        this.target = (List<Object>) target;
    }

    /**
     * @return 
     *          the name of the class that defines the  interaction.
     */
    public String getInteraction() {
        return interaction;
    }

    /**
     * @return 
     *          a list of interaction's target objects 
     */
    public List<Object> getTarget() {
        return target;
    }
}