package es.upm.dit.gsi.shanks.model.failure.test;

import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class MyFailure extends Failure {

    public MyFailure() {
        super(MyFailure.class.getName(), 0.01);
    }

    @Override
    public void addPossibleAffectedElements() {
        this.addPossibleAffectedElements(MyDevice.class, MyDevice.NOK);
        this.addPossibleAffectedElements(MyLink.class, MyLink.BROKEN);
    }

}
