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
package es.upm.dit.gsi.shanks.model.element.link.portrayal.test;

import java.awt.Color;
import java.util.HashMap;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

@SuppressWarnings("restriction")
public class MyLink3DPortrayal extends Link3DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7761302141125482551L;

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getModel(java.lang.Object, javax.media.j3d.TransformGroup)
     */
    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {

        return super.getModel(object, model);
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLabelColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLabelColor(Link link) {
        HashMap<String, Boolean> status = link.getStatus();
        if (status.get(MyLink.OK_STATUS)) {
            return Color.green;
        } else if (status.get(MyLink.BROKEN_STATUS)) {
            return Color.red;
        } else {
            return Color.gray;
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLinkColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLinkColor(Link link) {
        HashMap<String, Boolean> status = link.getStatus();
        if (status.get(MyLink.OK_STATUS)) {
            return Color.blue;
        } else if (status.get(MyLink.BROKEN_STATUS)) {
            return Color.red;
        } else {
            return Color.green;
        }
    }

}
