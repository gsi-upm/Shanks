package es.upm.dit.gsi.shanks.model.element.link.portrayal.test;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;

public class MyLink3DPortrayal extends Link3DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7761302141125482551L;

    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {
        model = super.getModel(object, model);
//        model.get
        //TODO check this
        
        return model;
    }

}
