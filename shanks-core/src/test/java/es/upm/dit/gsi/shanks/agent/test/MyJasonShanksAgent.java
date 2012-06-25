package es.upm.dit.gsi.shanks.agent.test;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.JasonShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.test.MyJasonShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class MyJasonShanksAgent extends JasonShanksAgent {

    private int numberOfResolvedFailures;
    private Logger logger = Logger.getLogger(MyJasonShanksAgent.class.getName());

    /**
     * @param id
     * @param aslFilePath
     * @throws DuplicatedActionIDException
     */
    public MyJasonShanksAgent(String id, String aslFilePath)
            throws ShanksException {
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

        percepts.add(ASSyntax.createLiteral(MyJasonShanksAgent.NUMOFFAILURES,
                new Term[] { new NumberTermImpl(numFailures) }));
        return percepts;
    }

    @Override
    public void configActions() throws DuplicatedActionIDException {
        this.addAction(MyJasonShanksAgentAction.FIX, MyJasonShanksAgentAction.class);
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
