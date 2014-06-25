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
package es.upm.dit.gsi.shanks.model.event.failiure.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class Failure2DPortrayal extends SimplePortrayal2D {

    //TODO i think this class is non-useful. Need to be eliminated. 
    /*
     * (non-Javadoc)
     * 
     * @see sim.portrayal.SimplePortrayal2D#draw(java.lang.Object,
     * java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Failure failure = (Failure) object;;
        final double width = 5;
        final double height = 5;

        // Draw the devices
        int x = (int) (info.draw.x - width / 2.0);
        int y = (int) (info.draw.y - height / 2.0);
        int w = (int) (width);
        int h = (int) (height);        
        graphics.setColor(Color.gray);
        graphics.fillOval(x, y-2, w, h);
        x = (int) ((int) info.draw.x + (width / 2.0));
        y = (int) info.draw.y;

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Failure ID ----> "
                + failure.getID(), x+3, y+3);
    }

    private static final long serialVersionUID = 4089725392194583078L;
}
