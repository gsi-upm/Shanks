package es.upm.dit.gsi.shanks.model.failure.test;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
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

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
     */
    @Override
    public boolean isResolved() {
        Set<NetworkElement> affectedElements = this.getAffectedElements().keySet();
        boolean resolved = false;
        for (NetworkElement element : affectedElements) {
            if (element instanceof MyDevice) {
                // Checking status / properties of the network element
                if (element.getCurrentStatus().equals(MyDevice.OK_STATUS)) {
                    resolved = true;
                } else {
                    resolved = false;
                    break;
                }
            } else if (element instanceof MyLink) {
                // Checking status / properties of the network element
                if (element.getCurrentStatus().equals(MyLink.OK_STATUS)) {
                    resolved = true;
                } else {
                    resolved = false;
                    break;
                }
            }
        }
        return resolved;
    }

}
