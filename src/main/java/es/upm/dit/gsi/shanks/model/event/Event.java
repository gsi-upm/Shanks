package es.upm.dit.gsi.shanks.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.scenario.Scenario;

//Under construction


public abstract class Event {

    
    private Object generator;
    private String id;
    
    public List<String> propertiesToAdd;
    public List<String> propertiesToRemove;
    public HashMap<String, Object> valueToSet;
    
    public Event(String id, Object generator){
        this.id = id;
        this.generator = generator;
        
        this.propertiesToAdd = new ArrayList<String>();
        this.propertiesToRemove = new ArrayList<String>();
        this.valueToSet = new HashMap<String, Object>();
    }
    
    public String getID(){
        return this.id;
    }
    
    public Object getGenerator(){
        return this.generator;
    }
    
    public void changePropertyOfScenario(Scenario scenario, String property, Object value){
        Properties scenarioProperties = scenario.getProperties();
        if(scenarioProperties.contains(property)){
            scenarioProperties.put(property, value);
        }
    }
    
    //Se da el scenario del cual este evento eliminaria esas properties
    public void removePropertyOfScenario(Scenario scenario){
        Properties scenProperties = scenario.getProperties();
        for(String prop : propertiesToRemove){
            if(scenProperties.contains(prop)){
                scenProperties.remove(prop);
            }else{
                System.out.println("This scenario haven't that property");
            }
        }
    }
    
    
    //Se da el scenario del cual este evento añadiria properties
    public void addPropertyToScenario(Scenario scenario){
        Properties scenarioProperties = scenario.getProperties();
        for(String prop :propertiesToAdd){
            Object val = valueToSet.get(prop);
            if(!scenarioProperties.contains(prop)){
               scenarioProperties.put(prop, val);
            }
        }
    }
    
    
    //Todo en uno (añadir-eliminar)
    public void modifyPropertyOfScenario(Scenario scenario){
        Properties scenarioProperties = scenario.getProperties();
        for(String prop :propertiesToAdd){
            Object val = valueToSet.get(prop);
            scenarioProperties.put(prop, val);
        }
        for(String property : propertiesToRemove){
            if(scenarioProperties.contains(property)){
                scenarioProperties.remove(property);
            }
        }
        
        
    }
    
    public void changeGenerator(Object gen){
        this.generator = gen;
    }
    
    public abstract void addAffectedProperties(); 
    
    
    
    
    //Properties que este elemento eliminaria
    public void removeProperty(String property){
        this.propertiesToRemove.add(property);
    }
    
    
    //Properties que este elemento cambiaria
    public void addProperty(String property, Object value){
        this.propertiesToAdd.add(property);
        this.valueToSet.put(property, value);
    }
    
    
    
}
