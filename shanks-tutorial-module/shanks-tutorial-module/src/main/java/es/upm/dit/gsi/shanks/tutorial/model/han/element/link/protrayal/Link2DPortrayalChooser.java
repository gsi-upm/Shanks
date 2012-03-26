package es.upm.dit.gsi.shanks.tutorial.model.han.element.link.protrayal;

import java.awt.Graphics2D;

import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.EthernetCable;

/**
 * @author a.carrera
 *
 */
public class Link2DPortrayalChooser extends Link2DPortrayal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5175384280440185808L;

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
        Edge e = (Edge) object;

        Link link = (Link) e.getInfo();
        SimpleEdgePortrayal2D p;
        
        if (link instanceof EthernetCable) {
        	p = new EthernetCable2DPortrayal();
//TODO        } else if (link instanceof HDMICable) {
//        	p = new HDMICable2DPortrayal();
        } else {
        	p = new SimpleEdgePortrayal2D(); //
        }
        

    	p.draw(e, graphics, info);

    }

}
