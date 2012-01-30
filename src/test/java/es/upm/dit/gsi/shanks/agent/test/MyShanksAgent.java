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

    /**
     * @param id
     * @param aslFilePath
     * @throws DuplicatedActionIDException
     */
    public MyShanksAgent(String id, String aslFilePath)
            throws DuplicatedActionIDException {
        super(id, aslFilePath);
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 2187089758395179991L;

    @Override
    public List<Literal> updateBeliefs(ShanksSimulation simulation) {
        List<Literal> percepts = new ArrayList<Literal>();
        percepts.add(ASSyntax.createLiteral("myself",
                new Term[] { Literal.parseLiteral(this.getID()) }));
        if (simulation.getScenario().getCurrentFailures().size() > 4) {
            percepts.add(ASSyntax.createLiteral("repair", new Term[] {}));
        } else if (simulation.getScenario().getCurrentFailures().size() > 0) {
            String coworker;
            percepts.add(ASSyntax.createLiteral("ask", new Term[] {}));
            coworker = "resolverAgent2";
            percepts.add(ASSyntax.createLiteral("coworker",
                    new Term[] { Literal.parseLiteral(coworker) }));
        } else {
            percepts.add(ASSyntax.createLiteral("relax", new Term[] {}));
        }
        return percepts;
    }

    @Override
    public void configActions() throws DuplicatedActionIDException {
        this.addAction("fix", MyShanksAgentAction.class);
    }

}
