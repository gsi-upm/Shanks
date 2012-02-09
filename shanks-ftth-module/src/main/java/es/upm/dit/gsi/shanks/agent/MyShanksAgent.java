package es.upm.dit.gsi.shanks.agent;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.test.MyShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class MyShanksAgent extends ShanksAgent {

    private int numberOfResolvedFailures;
    private Logger logger = Logger.getLogger(MyShanksAgent.class.getName());

    /**
     * @param id
     * @param aslFilePath
     * @throws DuplicatedActionIDException
     */
    public MyShanksAgent(String id, String aslFilePath)
            throws DuplicatedActionIDException {
        super(id, aslFilePath);
        this.numberOfResolvedFailures = 0;
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 2187089758395179991L;
    public static final String NUMOFFAILURES = "failures";
    public static final String PENDING_FAILURES = "pending";

    @Override
    public List<Literal> updateBeliefs(ShanksSimulation simulation) {
        List<Literal> percepts = new ArrayList<Literal>();
        Set<Failure> currentFailures = simulation.getScenario()
                .getCurrentFailures();
        int numFailures = currentFailures.size();
        logger.finest("Perceive() " + this.getID() + " Number of failures: "
                + numFailures);

        percepts.add(ASSyntax.createLiteral(MyShanksAgent.NUMOFFAILURES,
                new Term[] { new NumberTermImpl(numFailures) }));
        return percepts;
    }

    @Override
    public void configActions() throws DuplicatedActionIDException {
        this.addAction(MyShanksAgentAction.FIX, MyShanksAgentAction.class);
    }

    /**
     * @return the numberOfResolvedFailures
     */
    public int getNumberOfResolvedFailures() {
        return numberOfResolvedFailures;
    }

    /**
     * Add 1 to resolved failures;
     */
    public void incrementNumberOfResolverFailures() {
        this.numberOfResolvedFailures++;
    }

}
