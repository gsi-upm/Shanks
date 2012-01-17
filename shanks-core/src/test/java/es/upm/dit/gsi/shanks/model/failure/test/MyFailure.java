package es.upm.dit.gsi.shanks.model.failure.test;

import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class MyFailure extends Failure {

    public MyFailure(String id, double occurrenceProbability) {
        super(id, occurrenceProbability);
    }

    @Override
    public void addPossibleAffectedElements() {
        this.addPossibleAffectedElements(MyDevice.class);
        this.addPossibleAffectedElements(MyLink.class);
    }

}
