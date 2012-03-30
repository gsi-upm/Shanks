package es.upm.dit.gsi.shanks.agent.action;

import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

import java.util.List;

import sim.engine.Steppable;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.model.event.Action;

/**
 * 
 * Class to represent the actions that are executed by JasonShanksAgents
 * 
 * @author a.carrera
 *
 */
public abstract class JasonShanksAgentAction extends Action {

    public JasonShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);

    }

    /**
     * @param simulation 
     * @return 
     */
    public boolean executeAction(ShanksSimulation simulation, ShanksAgent agent, List<Term> arguments) {
        //JASON -> conversion de Terms a NetworkElements
        for (Term e : arguments) {
            if (e instanceof StringTermImpl) {
                StringTermImpl s = (StringTermImpl) e;
                String name = s.getString();
            }
        }
        //add elements as affected
        return false;
        
        //launch event
    }
    
}
