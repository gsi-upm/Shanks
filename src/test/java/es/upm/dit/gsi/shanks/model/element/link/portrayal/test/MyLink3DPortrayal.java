package es.upm.dit.gsi.shanks.model.element.link.portrayal.test;

import java.awt.Color;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

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
        String status = link.getCurrentStatus();
        if (status.equals(MyLink.OK_STATUS)) {
            return Color.green;
        } else if (status.equals(MyLink.BROKEN_STATUS)) {
            return Color.red;
        } else {
            return Color.gray;
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLinkColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLinkColor(Link link) {
        String status = link.getCurrentStatus();
        if (status.equals(MyLink.OK_STATUS)) {
            return Color.blue;
        } else if (status.equals(MyLink.BROKEN_STATUS)) {
            return Color.red;
        } else {
            return Color.green;
        }
    }

}
