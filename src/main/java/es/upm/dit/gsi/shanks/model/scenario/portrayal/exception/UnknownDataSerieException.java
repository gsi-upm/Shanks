/**
 * 
 */
package es.upm.dit.gsi.shanks.model.scenario.portrayal.exception;

/**
 * @author a.carrera
 *
 */
public class UnknownDataSerieException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7856426220244631003L;

    public UnknownDataSerieException(String chartID, String dataSerieID) {
        super("There is no data serie: " + dataSerieID + " for chart: " + chartID);
    }

}
