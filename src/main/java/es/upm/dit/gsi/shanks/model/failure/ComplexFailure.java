/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.failure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.exception.UnsupportedElementInFailureException;

/**
 * @author a.carrera
 *
 */
public abstract class ComplexFailure extends Failure {

    private List<Failure> addedFailures;
    
    /**
     * @param type
     */
    public ComplexFailure(String id, double occurrenceProbability) {
        super(id,occurrenceProbability);
        this.addedFailures = new ArrayList<Failure>();
    }
    
    /**
     * Add a failure to a simple
     * 
     * @param f The failure that must be deactivated when it is added
     * @return true if it is added, false if not
     */
    public boolean addSimpleFailure(Failure f) {
        if (!this.addedFailures.contains(f)) {
            HashMap<NetworkElement,String> elements = f.getAffectedElements();
            for (NetworkElement element : elements.keySet()) {
                try {
                    super.addAffectedElement(element, elements.get(element));
                } catch (UnsupportedElementInFailureException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            return false;
        }
    }
    Esto es para que no compile :P
    //TODO queda darle alguna vuelta...

}
