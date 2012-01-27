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
    public MyShanksAgent(String id, String aslFilePath) throws DuplicatedActionIDException {
        super(id, aslFilePath);
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 2187089758395179991L;

    @Override
    public List<Literal> updateBeliefs(ShanksSimulation simulation) {        
        List<Literal> percepts = new ArrayList<Literal>();
        percepts.add(ASSyntax.createLiteral("repair", new Term[] { ASSyntax.createNumber(1) }));
        return percepts;
    }

    @Override
    public void configActions() throws DuplicatedActionIDException {
        this.addAction("fix", MyShanksAgentAction.class);
    }


}
