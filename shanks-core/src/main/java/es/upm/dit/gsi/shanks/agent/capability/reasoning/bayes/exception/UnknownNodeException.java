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
public class UnknownNodeException extends ShanksException {

    public UnknownNodeException(ProbabilisticNetwork bn, String nodeName) {
        super("Unknown node " + nodeName + " for Bayesian Network " + bn.getName());
    }

    private static final long serialVersionUID = 8892713368194173714L;

}
