/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception;

import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * @author a.carrera
 *
 */
public class UnknowkNodeStateException extends ShanksException {

    public UnknowkNodeStateException(ProbabilisticNetwork bn, String nodeID,
            String status) {
        super("Unknown status " + status + " in node " + nodeID + " for Bayesian Network " + bn.getName());
    }

    private static final long serialVersionUID = 7465482396265626568L;

}
