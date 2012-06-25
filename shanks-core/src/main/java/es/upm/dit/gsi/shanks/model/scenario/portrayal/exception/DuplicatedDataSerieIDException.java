package es.upm.dit.gsi.shanks.model.scenario.portrayal.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * @author a.carrera
 *
 */
public class DuplicatedDataSerieIDException extends ShanksException {

    public DuplicatedDataSerieIDException(String chartID, String dataSerieID) {
        super("Duplicated data serie: " + dataSerieID + " for chart: " + chartID);
    }

    private static final long serialVersionUID = -443446844820390707L;

}
