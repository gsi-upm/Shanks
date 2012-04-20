package es.upm.dit.gsi.shanks.model.failure.test;

import java.util.List;

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
        super(MyFailure.class.getName(), 0.9);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
    @Override
    public void addPossibleAffectedElements() {
        this.addPossibleAffectedElements(MyDevice.class, MyDevice.OK_STATUS, false);
        this.addPossibleAffectedElements(MyDevice.class, MyDevice.NOK_STATUS, false);
        this.addPossibleAffectedElements(MyDevice.class, MyDevice.HIGH_TEMP_STATUS, true);
        this.addPossibleAffectedElements(MyLink.class, MyLink.BROKEN_STATUS, false);
        this.addPossibleAffectedElements(MyLink.class, MyDevice.OK_STATUS, false);
        this.addPossibleAffectedProperties(MyDevice.class, MyDevice.TEMPERATURE_PROPERTY, 80);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
     */
    @Override
    public boolean isResolved() {
        List<NetworkElement> affectedElements = this.getAffectedElements();
        boolean resolved = false;
        for (NetworkElement element : affectedElements) {
            if (element instanceof MyDevice) {
                // Checking status / properties of the network element
                if (!element.getStatus().get(MyDevice.HIGH_TEMP_STATUS)) {
                    resolved = true;
                } else {
                    resolved = false;
                    break;
                }
            } else if (element instanceof MyLink) {
                // Checking status / properties of the network element
                if(element.getStatus().get(MyLink.OK_STATUS)) {
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
