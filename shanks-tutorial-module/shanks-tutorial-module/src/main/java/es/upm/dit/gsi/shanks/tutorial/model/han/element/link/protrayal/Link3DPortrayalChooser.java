package es.upm.dit.gsi.shanks.tutorial.model.han.element.link.protrayal;

import java.awt.Color;

import javax.media.j3d.TransformGroup;

import sim.field.network.Edge;
import sim.portrayal.LocationWrapper;
import sim.portrayal3d.network.SimpleEdgePortrayal3D;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.EthernetCable;

/**
 * @author a.carrera
 *
 */
public class Link3DPortrayalChooser extends Link3DPortrayal {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -750428357389857721L;
	
	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getModel(java.lang.Object, javax.media.j3d.TransformGroup)
	 */
	public TransformGroup getModel(Object object, TransformGroup model) {
		
		LocationWrapper wrapper = (LocationWrapper) object;
        Edge e = (Edge) (wrapper.getLocation());

        Link link = (Link) e.getInfo();
        SimpleEdgePortrayal3D p;
        
        if (link instanceof EthernetCable) {
        	p = new EthernetCable3DPortrayal();
//TODO        } else if (link instanceof HDMICable) {
//        	p = new HDMICable3DPortrayal();
        } else {
        	p = new SimpleEdgePortrayal3D(); //
        }
        

    	return p.getModel(object, model);

    }

	@Override
	public Color getLabelColor(Link paramLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getLinkColor(Link paramLink) {
		// TODO Auto-generated method stub
		return null;
	}

}
