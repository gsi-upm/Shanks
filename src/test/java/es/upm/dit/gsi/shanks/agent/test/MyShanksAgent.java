package es.upm.dit.gsi.shanks.agent.test;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.test.MyShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;

public class MyShanksAgent extends ShanksAgent {

    private int numberOfResolvedFailures;
    
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
    public static final String REPAIR = "repair";
    public static final String ASK = "ask";
    public static final String COWORKER = "coworker";
    public static final String RELAX = "relax";

    @Override
    public List<Literal> updateBeliefs(ShanksSimulation simulation) {
        List<Literal> percepts = new ArrayList<Literal>();
        if (simulation.getScenario().getCurrentFailures().size() > 4) {
            percepts.add(ASSyntax.createLiteral(MyShanksAgent.REPAIR, new Term[] {}));
        } else if ((this.getID().equals("resolverAgent1")) && (simulation.getScenario().getCurrentFailures().size() > 0)) {
//            System.out.println("Agent " + this.getID() + " asking...");
            String coworker;
            percepts.add(ASSyntax.createLiteral(MyShanksAgent.ASK, new Term[] {}));
            coworker = "resolverAgent2";
            percepts.add(ASSyntax.createLiteral(MyShanksAgent.COWORKER,
                    new Term[] { Literal.parseLiteral(coworker) }));
        } else {
            percepts.add(ASSyntax.createLiteral(MyShanksAgent.RELAX, new Term[] {}));
        }
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
