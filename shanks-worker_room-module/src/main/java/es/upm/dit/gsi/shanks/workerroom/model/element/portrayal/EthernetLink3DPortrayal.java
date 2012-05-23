package es.upm.dit.gsi.shanks.workerroom.model.element.portrayal;

import java.awt.Color;
import java.util.HashMap;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.link.EthernetLink;

@SuppressWarnings("restriction")
public class EthernetLink3DPortrayal extends Link3DPortrayal{

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getModel(java.lang.Object, javax.media.j3d.TransformGroup)
     */
    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {

        return super.getModel(object, model);
    }
	
	@Override
	public Color getLabelColor(Link link) {
		HashMap<String, Boolean> status = link.getStatus();
        if (status.get(EthernetLink.STATUS_OK)) {
            return Color.green;
        }else{
            return Color.red;
        }
	}

	@Override
	public Color getLinkColor(Link link) {
		HashMap<String, Boolean> status = link.getStatus();
        if (status.get(EthernetLink.STATUS_OK)) {
            return Color.green;
        }else{
            return Color.red;
        }
	}

	private static final long serialVersionUID = 5912536626075582807L;	
}
