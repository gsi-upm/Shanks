/**
 * 
 */
package es.upm.dit.gsi.shanks.model.scenario.portrayal.exception;


/**
 * @author a.carrera
 *
 */
public class DuplicatedChartIDException extends Exception {

    public DuplicatedChartIDException(String chartID) {
        super("The chartID: " + chartID + " is duplicated. This chart will not be added to the simulation.");
    }

    /**
     * 
     */
    private static final long serialVersionUID = 6113895481665902247L;

}
