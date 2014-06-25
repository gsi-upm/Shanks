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
package es.upm.dit.gsi.shanks.model.element.link.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.network.EdgeDrawInfo2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public abstract class Link2DPortrayal extends SimpleEdgePortrayal2D {

    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
        EdgeDrawInfo2D ei = (EdgeDrawInfo2D) info;
        Edge e = (Edge) object;

        final int startX = (int) ei.draw.x;
        final int startY = (int) ei.draw.y;
        final int endX = (int) ei.secondPoint.x;
        final int endY = (int) ei.secondPoint.y;
        final int midX = (int) (ei.draw.x + ei.secondPoint.x) / 2;
        final int midY = (int) (ei.draw.y + ei.secondPoint.y) / 2;

        graphics.setColor(Color.yellow);
        graphics.drawLine(startX, startY, endX, endY);
        
        Link link = (Link) e.getInfo();
        graphics.setColor(Color.blue);
        graphics.setFont(labelFont);
        int width = graphics.getFontMetrics().stringWidth(link.getID());
        graphics.drawString( link.getID(), midX - width / 2, midY );

    }
    
    private static final long serialVersionUID = 8224336342428392184L;
}
