package es.upm.dit.gsi.shanks.model.event.networkelement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.ProbabilisticEvent;
import es.upm.dit.gsi.shanks.model.event.exception.DuplicateAffectedElementOnEventException;
import es.upm.dit.gsi.shanks.model.event.exception.UnsupportedElementByEventException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public abstract class ProbabilisticNetworkElementEvent extends
        ProbabilisticEvent {

    private Logger logger;
    protected List<NetworkElement> affectedElements;
    protected List<Scenario> affectedScenarios;
    protected HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> possibleAffectedElements;
    protected HashMap<Class<? extends Scenario>, HashMap<String, Object>> possibleAffectedScenarios;

    public ProbabilisticNetworkElementEvent(String name, Steppable generator,
            Double prob, Logger logger) {
        super(name, generator, prob);
        this.logger = logger;
        this.affectedElements = new ArrayList<NetworkElement>();
        this.affectedScenarios = new ArrayList<Scenario>();
        this.possibleAffectedElements = new HashMap<Class<? extends NetworkElement>, HashMap<String, Object>>();
        this.possibleAffectedScenarios = new HashMap<Class<? extends Scenario>, HashMap<String, Object>>();

        this.addPossibleAffected();
    }

    public void addPossibleAffectedScenario(
            Class<? extends Scenario> scenarioClass,
            HashMap<String, Object> affectedFields) {
        // Make it on the hard way to get check each element.
        // this.possibleAffectedScenarios.put(scenarioClass, affectedFields);
        for (String fieldKey : affectedFields.keySet()) {
            this.addPossibleAffectedScenarioField(scenarioClass, fieldKey,
                    affectedFields.get(fieldKey));
        }
    }

    public void addPossibleAffectedScenarioState(
            Class<? extends Scenario> scenarioClass, String state, Object value) {
        this.addPossibleAffectedScenarioField(scenarioClass, state, value);
    }

    // NOTE: this method will have sense when properties are implemented for
    // scenarios.
    // public void addPossibleAffectedScenarioProperty(Class<? extends Scenario>
    // scenarioClass, String property, Object value){
    // this.addPossibleAffectedScenarioField(scenarioClass, property, value);
    // }

    public void addPossibleAffectedScenarioField(
            Class<? extends Scenario> scenarioClass, String field, Object value) {
        if (this.possibleAffectedScenarios.containsKey(scenarioClass)) {
            HashMap<String, Object> affectedFields = this.possibleAffectedScenarios
                    .get(scenarioClass);
            if (!affectedFields.containsKey(field)) {
                affectedFields.put(field, value);
            } else {
                this.logger
                        .warning("Trying to add a new affected field that already has beeen defined. May be you has define "
                                + "two values or maybe there is two fields with the same name. \nHowever the second value "
                                + "was ignored.\nScenarioClass: "
                                + scenarioClass.getName()
                                + "\nField: "
                                + field
                                + "\nValue: " + value);
            }
        } else {
            HashMap<String, Object> affectedFields = new HashMap<String, Object>();
            affectedFields.put(field, value);
            this.possibleAffectedScenarios.put(scenarioClass, affectedFields);
        }
    }

    public void addPossibleAffectedElement(
            Class<? extends NetworkElement> elementClass,
            HashMap<String, Object> affectedFields) {
        // Make it on the hard way to get check each element.
        // this.possibleAffectedElements.put(elementClass, affectedFields);
        for (String fieldKey : affectedFields.keySet()) {
            this.addPossibleAffectedElementField(elementClass, fieldKey,
                    affectedFields.get(fieldKey));
        }

    }

    public void addPossibleAffectedElementProperty(
            Class<? extends NetworkElement> elementClass, String property,
            Object value) {
        // NOTE: if in the future we will distinguish between change properties
        // or change states, then the three methods make sense.
        // meanwhile we keep this three methods, although only one of them makes
        // sense, for the user better understanding.
        this.addPossibleAffectedElementField(elementClass, property, value);
    }

    public void addPossibleAffectedElementState(
            Class<? extends NetworkElement> elementClass, String state,
            Object value) {
        // NOTE: if in the future we will distinguish between change properties
        // or change states, then the three methods make sense.
        // meanwhile we keep this three methods, although only one of them makes
        // sense, for the user better understanding.
        this.addPossibleAffectedElementField(elementClass, state, value);
    }

    public void addPossibleAffectedElementField(
            Class<? extends NetworkElement> elementClass, String field,
            Object value) {
        if (this.possibleAffectedElements.containsKey(elementClass)) {
            HashMap<String, Object> affectedFields = this.possibleAffectedElements
                    .get(elementClass);
            if (!affectedFields.containsKey(field)) {
                affectedFields.put(field, value);
            } else {
                this.logger
                        .warning("Trying to add a new possible affected field that already has beeen defined. May be you has define "
                                + "two values or maybe there is two fields with the same name. \nHowever the second value "
                                + "was ignored.\nElementClass: "
                                + elementClass.getName()
                                + "\nField: "
                                + field
                                + "\nValue: " + value);
            }
        } else {
            HashMap<String, Object> affectedFields = new HashMap<String, Object>();
            affectedFields.put(field, value);
            this.possibleAffectedElements.put(elementClass, affectedFields);
        }
    }

    @Override
    public void addAffectedElement(NetworkElement el)
            throws ShanksException {
        if (this.possibleAffectedElements.containsKey(el.getClass())) {

            if ((!this.affectedElements.contains(el)))
                affectedElements.add(el);
            else {
                this.logger
                        .warning("Has been tried to add a duplicate affected element. The element has not been added."
                                + "\nProbabilisticEvent: "
                                + this.getClass().getName()
                                + "\nElement: "
                                + el);
                throw new DuplicateAffectedElementOnEventException(el);
            }

        } else {
            this.logger
                    .fine("Has been tried to add an affected element that doesn't match with possible affected types. "
                            + "The element has not been added."
                            + "\nProbabilisticEvent: "
                            + this.getClass().getName() + "\nElement: " + el);
            throw new UnsupportedElementByEventException(el);
        }

    }

    public void addAffectedElements(List<NetworkElement> ln)
            throws ShanksException {
        for (NetworkElement ne : ln)
            this.addAffectedElement(ne);
    }

    @Override
    public void addAffectedScenario(Scenario scen) {
        if (!this.affectedScenarios.contains(scen))
            affectedScenarios.add(scen);
        else
            this.logger
                    .warning("Has been tried to add a duplicate affected element. The element has not been added."
                            + "\nElement: " + scen);
    }

    public void addAffectedScenarios(List<Scenario> lscen) {
        for (Scenario sc : lscen)
            this.addAffectedScenario(sc);
    }

    /**
     * Remove this element, but not modify the status. When the failure will be
     * deactive, this removed element will keep the actual status
     * 
     * @param element
     */
    public boolean removeAffectedElement(NetworkElement element) {
        return this.affectedElements.remove(element);
    }

    public boolean removeAffectedScenario(Scenario sc) {
        return this.affectedScenarios.remove(sc);
    }

    public boolean removePossibleAffectedElements(
            Class<? extends NetworkElement> elementClass) {
        if (this.possibleAffectedElements.containsKey(elementClass)) {
            this.possibleAffectedElements.remove(elementClass);
            return true;
        }
        return false;
    }

    public boolean removePossibleAffectedScenarios(
            final Class<? extends Scenario> scenarioClass) {
        if (this.possibleAffectedScenarios.containsKey(scenarioClass)) {
            this.possibleAffectedScenarios.remove(scenarioClass);
            return true;
        }
        return false;
    }

    @Override
    public void changeProperties()
            throws ShanksException {
        List<? extends NetworkElement> elements = new ArrayList<NetworkElement>(
                this.affectedElements);
        for (NetworkElement el : this.affectedElements) {
            for (Class<?> c : possibleAffectedElements.keySet()) {
                if (c.equals(el.getClass())) {
                    for (String s : possibleAffectedElements.get(c).keySet()) {
                        if (el.getProperties().containsKey(s)) {
                            el.updatePropertyTo(s, possibleAffectedElements
                                    .get(c).get(s));
                            elements.remove(el);
                        }
                    }
                }
            }
            el.checkStatus();
        }
        if (!elements.isEmpty()) {
            // WARNNING! No all elements defined have been modified.
        }

    }

    @Override
    public void changeStatus() throws ShanksException {
        List<? extends NetworkElement> elements = new ArrayList<NetworkElement>(
                this.affectedElements);
        for (Class<?> c : possibleAffectedElements.keySet()) {
            for (NetworkElement el : this.affectedElements) {
                if (c.equals(el.getClass())) {
                    for (String s : possibleAffectedElements.get(c).keySet()) {
                        if (el.getStatus().containsKey(s)) {
                            el.updateStatusTo(s,
                                    (Boolean) possibleAffectedElements.get(c)
                                            .get(s));
                            elements.remove(el);
                        }
                    }
                }
                el.checkProperties();
            }
        }
        if (!elements.isEmpty()) {
            // WARNNING! No all elements defined have been modified.
            // this.logger.warning("");
        }

        List<? extends Scenario> scenarios = this.affectedScenarios;
        for (Class<?> c : possibleAffectedScenarios.keySet()) {
            for (Scenario sc : scenarios) {
                if (c.equals(sc.getClass())) {
                    for (String s : possibleAffectedScenarios.get(c).keySet()) {
                        sc.setCurrentStatus(s);
                        // if(sc.getStatus().containsKey(s)){
                        // sc.updateStatusTo(s, (Boolean)
                        // possibleAffectedElements.get(c).get(s));
                        // }
                    }
                }
            }
        }
    }

    /**
     * 
     * @throws UnsupportedNetworkElementFieldException
     */
    public abstract void changeOtherFields()
            throws UnsupportedNetworkElementFieldException;

    /**
     * @return the currentAffectedElements if the failure is active, null if not
     */
    public List<NetworkElement> getCurrentAffectedElements() {
        return affectedElements;
    }

    /**
     * @return the possibleAffectedElements
     */
    public HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> getPossibleAffectedElements() {
        return possibleAffectedElements;
    }

    /**
     * @return the possibleAffectedElements
     */
    public HashMap<Class<? extends Scenario>, HashMap<String, Object>> getPossibleAffectedScenarios() {
        return possibleAffectedScenarios;
    }

    @Override
    public List<?> getAffected() {
        return this.getCurrentAffectedElements();
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.event.Event#launchEvent()
     */
    @Override
    public void launchEvent() throws ShanksException {
        super.launchEvent();

        // This may be moved to EVENT.
        this.changeOtherFields();
    }

    // public static void main(String args[]) {
    // ArrayList<String> original = new ArrayList<String>();
    // for(int i = 0; i<5; i++)
    // original.add(""+i);
    // for(String s: original){
    // original.remove(s);
    // System.out.println(s);
    // }
    // List<String> igualado = original;
    // List<String> construido = new ArrayList<String>(original);
    // original.remove(0);
    // igualado.remove(1);
    // construido.remove(2);
    // System.out.println("org\t igu\t cons");
    // for(int i=0; i<4; i++){
    // if(i<original.size())
    // System.out.print(original.get(i)+"\t ");
    // else
    // System.out.println(" \t ");
    // if(i<igualado.size())
    // System.out.print(igualado.get(i)+"\t ");
    // else
    // System.out.print(" \t ");
    // if(i<construido.size())
    // System.out.println(construido.get(i));
    // }
    // }

}
