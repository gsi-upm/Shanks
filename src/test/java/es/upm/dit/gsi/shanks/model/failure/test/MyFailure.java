package es.upm.dit.gsi.shanks.model.failure.test;

import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 *
 */
public class MyFailure extends Failure {

    /**
     * 
     */
    public MyFailure() {
        super(MyFailure.class.getName(), 0.01);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
    @Override
    public void addPossibleAffectedElements() {
        this.addPossibleAffectedElements(MyDevice.class, MyDevice.NOK_STATUS);
        this.addPossibleAffectedElements(MyLink.class, MyLink.BROKEN_STATUS);
    }

}
