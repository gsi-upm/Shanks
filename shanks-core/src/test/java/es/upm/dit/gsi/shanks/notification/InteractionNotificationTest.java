package es.upm.dit.gsi.shanks.notification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.ScenarioManager;
import es.upm.dit.gsi.shanks.model.event.OneShotEvent;
import es.upm.dit.gsi.shanks.model.event.PeriodicEvent;
import es.upm.dit.gsi.shanks.model.event.ProbabilisticEvent;
import es.upm.dit.gsi.shanks.model.event.agent.Action;
import es.upm.dit.gsi.shanks.model.event.networkelement.PeriodicNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.event.scenario.PeriodicScenarioEvent;
import es.upm.dit.gsi.shanks.model.event.scenario.ProbabilisticScenarioEvent;
import es.upm.dit.gsi.shanks.notification.util.test.TestAction;
import es.upm.dit.gsi.shanks.notification.util.test.TestAgent;
import es.upm.dit.gsi.shanks.notification.util.test.TestDefinitions;
import es.upm.dit.gsi.shanks.notification.util.test.TestDevice;
import es.upm.dit.gsi.shanks.notification.util.test.TestPeriodicNetworkElementEvent;
import es.upm.dit.gsi.shanks.notification.util.test.TestPeriodicScenarioEvent;
import es.upm.dit.gsi.shanks.notification.util.test.TestProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.notification.util.test.TestProbabilisticScenarioEvent;
import es.upm.dit.gsi.shanks.notification.util.test.TestScenario;

public class InteractionNotificationTest {
    
    static Logger logger = Logger.getLogger(InteractionNotificationTest.class.getName());

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));

    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Create a single notifications a check its fields. 
     */
    @Test
    public void createNotification() {

        InteractionNotification iNotification = new InteractionNotification(
                TestDefinitions.NOTIFICATION_ID, TestDefinitions.NOTIFICATION_WHEN,
                TestDefinitions.NOTIFICATION_SOURCE, TestDefinitions.IN_INTERACTION,
                TestDefinitions.IN_TARGET);

        Assert.assertTrue(iNotification.getId().equals(TestDefinitions.NOTIFICATION_ID));
        Assert.assertTrue(iNotification.getWhen() == TestDefinitions.NOTIFICATION_WHEN);
        Assert.assertTrue(iNotification.getSource().equals(
                TestDefinitions.NOTIFICATION_SOURCE));
        Assert.assertTrue(iNotification.getInteraction().equals(
                TestDefinitions.IN_INTERACTION));
        Assert.assertTrue(iNotification.getTarget().equals(
                TestDefinitions.IN_TARGET));
    }

    /**
     * create a number of notifications and add them to a NotificationManager.  
     */
    @Test
    public void createAndAddNotificationsToNotificationManager() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i, 
                    TestDefinitions.NOTIFICATION_WHEN, TestDefinitions.NOTIFICATION_SOURCE, 
                    TestDefinitions.IN_INTERACTION, TestDefinitions.IN_TARGET));
        }
        
        try {
            NotificationManager nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            Assert.assertEquals(ln, (nm.getByType(InteractionNotification.class)));
            ln.add(new InteractionNotification(null, 0, nm, null, null));
            Assert.assertNotSame(ln, (nm.getByType(InteractionNotification.class)));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(""+e);
        }
    }
    
    /**
     * Create a list of notifications, add them to a NotificationManager and them 
     * make ID queries to the NotificationManager. 
     */
    @Test
    public void checkIDQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i, 
                    TestDefinitions.NOTIFICATION_WHEN, TestDefinitions.NOTIFICATION_SOURCE, 
                    TestDefinitions.IN_INTERACTION, TestDefinitions.IN_TARGET));
        }
        
        ArrayList<Notification> obtained_ln = new ArrayList<Notification>();
        NotificationManager nm = null;
        try {
            nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            obtained_ln= nm.getByType(InteractionNotification.class);
            Assert.assertEquals(ln, obtained_ln);
            for (Notification expected: ln){
                Assert.assertEquals(expected, nm.getByID(expected.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(""+e);
        }
    }
    
    /**
     * Create a list of notifications, add them to a NotificationManager and them 
     * make step queries to the NotificationManager. 
     */
    @Test
    public void checkStepQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        HashMap<String, Integer> stepCount = new HashMap<String, Integer>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            Long step = Math.round(TestDefinitions.NOTIFICATION_WHEN*Math.random());
            if(stepCount.containsKey(step.toString())){
                stepCount.put(step.toString(), stepCount.get(step.toString())+1);
            } else {
                stepCount.put(step.toString(), 1);
            }
            ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i, step, 
                    TestDefinitions.NOTIFICATION_SOURCE, TestDefinitions.IN_INTERACTION, 
                    TestDefinitions.IN_TARGET));
        }
        
        NotificationManager nm = null;
        try {
            nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            Assert.assertEquals(ln, nm.getByType(InteractionNotification.class));
            
            for(String step:stepCount.keySet()){
                List<Notification> obtained = nm.getByStep(Integer.parseInt(step));
                Assert.assertTrue(obtained.size()==stepCount.get(step).intValue());
                for (Notification n: obtained){
                    Assert.assertTrue(ln.contains(n));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(""+e);
        }
    }
    
    /**
     * Create a list of notifications, add them to a NotificationManager and them 
     * make step queries to the NotificationManager. 
     */
    @Test
    public void checkSourceQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        HashMap<Object, Integer> sourceCount = new HashMap<Object, Integer>();
        ArrayList<Object> allSources = new ArrayList<Object>();
        
        try {
            for(int i=0; i<TestDefinitions.SOURCES_SIZE+1; i++)
                allSources.add(new TestAgent(TestDefinitions.AGENT_ID+i,
                      0, new NotificationManager(TestDefinitions.getSimulation(0))));
            
            for (int i=0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
                Object source = null;
                switch((int)(Math.round(TestDefinitions.SOURCES_SIZE*Math.random()))) {
                case 0:
                    source = allSources.get(0);
                    break;
                case 1:
                    source = allSources.get(1);
                    break;    
                case 2:
                    source = allSources.get(2);
                    break;
                case 3:
                    source = allSources.get(3);
                    break;
                case 4:
                    source = allSources.get(4);
                    break;
                case 5:
                    source = allSources.get(5);
                    break;
                default:
                    break;
                }
                if(sourceCount.containsKey(source)){
                    sourceCount.put(source, sourceCount.get(source)+1);
                } else {
                    sourceCount.put(source, 1);
                }
                ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i,
                        TestDefinitions.NOTIFICATION_WHEN, source, TestDefinitions.IN_INTERACTION, 
                        TestDefinitions.IN_TARGET));
            }
            NotificationManager nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            Assert.assertEquals(ln, nm.getByType(InteractionNotification.class));
            
            for(Object source:sourceCount.keySet()){
                List<Notification> obtained = nm.getBySource(source);
                Assert.assertTrue(obtained.size()==sourceCount.get(source).intValue());
                for (Notification n: obtained){
                    Assert.assertTrue(ln.contains(n));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(""+e);
        }
    }
    
    /**
     * Create a list of notifications, add them to a NotificationManager and them 
     * make step queries to the NotificationManager. 
     */
    @Test
    public void checkInteractionQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        HashMap<String, Integer> interactionCount = new HashMap<String, Integer>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            String interaction = null;
            switch((int)(Math.round(2*Math.random()))) {
            case 0:
                interaction = OneShotEvent.class.getName();
                break;
            case 1:
                interaction = PeriodicEvent.class.getName();
                break;    
            case 2:
                interaction = ProbabilisticEvent.class.getName();
                break;
            }
            if(interactionCount.containsKey(interaction)){
                interactionCount.put(interaction, interactionCount.get(interaction)+1);
            } else {
                interactionCount.put(interaction, 1);
            }
            ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i, TestDefinitions.NOTIFICATION_WHEN,  
                    TestDefinitions.NOTIFICATION_SOURCE, interaction, TestDefinitions.IN_TARGET));
        }
        
        NotificationManager nm = null;
        try {
            nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            Assert.assertEquals(ln, nm.getByType(InteractionNotification.class));
            
            for(String interaction:interactionCount.keySet()){
                List<Notification> obtained = nm.getByInteraction(interaction);
                Assert.assertTrue(obtained.size()==interactionCount.get(interaction).intValue());
                for (Notification n: obtained){
                    Assert.assertTrue(ln.contains(n));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(""+e);
        }
    }
    
    /**
     * Create a list of notifications, add them to a NotificationManager and them 
     * make queries by target to the NotificationManager. 
     */
    @Test
    public void checkTargetQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        HashMap<Object, Integer> targetCount = new HashMap<Object, Integer>();
        
        try {
            ArrayList<Object> allTargets = new ArrayList<Object>();
            
            for(int i=0; i<TestDefinitions.TARGETS_SIZE; i++)
                allTargets.add(new TestDevice(TestDefinitions.DEVICE_ID+i, null, false, logger));
            
            for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
                ArrayList<Object> usedTargets = new ArrayList<Object>();
                for (int j=0; j<TestDefinitions.TARGETS_SIZE; j++){
                    Object tempTarget = null;
                    switch((int)(Math.round((TestDefinitions.TARGETS_SIZE-1)*Math.random()))) {
                    case 0:
                        tempTarget = allTargets.get(0);
                        break;
                    case 1:
                        tempTarget = allTargets.get(1);
                        break;    
                    case 2:
                        tempTarget = allTargets.get(2);
                        break;
                    case 3:
                        tempTarget = allTargets.get(3);
                        break;
                    case 4:
                        tempTarget = allTargets.get(4);
                        break;
                    default:
                        break;
                    }
                    if(targetCount.containsKey(tempTarget)){
                        targetCount.put(tempTarget, targetCount.get(tempTarget)+1);
                    } else {
                        targetCount.put(tempTarget, 1);
                    }
                    usedTargets.add(tempTarget);
                }
                ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i,
                        TestDefinitions.NOTIFICATION_WHEN, TestDefinitions.NOTIFICATION_SOURCE, 
                        TestDefinitions.IN_INTERACTION, usedTargets));
            }
            
            NotificationManager nm = null;
            nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            Assert.assertEquals(ln, nm.getByType(InteractionNotification.class));
            
            for(Object target:targetCount.keySet()){
                List<Notification> obtained = nm.getByTarget(target);
                Assert.assertTrue(obtained.size()==targetCount.get(target).intValue());
                for (Notification n: obtained){
                    Assert.assertTrue(ln.contains(n));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(""+e);
        }
        
    }
    
    /**
     * Create a list of notifications, add them to a NotificationManager and them 
     * make queries by ElementId to the NotificationManager. 
     */
    @Test
    public void checkElementIDQueries() {
        ArrayList<Notification> ln = new ArrayList<Notification>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            ln.add(new InteractionNotification(TestDefinitions.NOTIFICATION_ID+i, 
                    TestDefinitions.NOTIFICATION_WHEN, TestDefinitions.NOTIFICATION_SOURCE, 
                    TestDefinitions.IN_INTERACTION, TestDefinitions.IN_TARGET));
        }
        try {
            NotificationManager nm = new NotificationManager(ln, null, TestDefinitions.getSimulation(0), logger);
            Assert.assertEquals(ln, (nm.getByType(InteractionNotification.class)));
            ln.add(new InteractionNotification(null, 0, nm, null, null));
            Assert.assertNotSame(ln, (nm.getByType(InteractionNotification.class)));
            List<ValueNotification> obtained = nm.getByElementID(TestDefinitions.VN_ELEMENT_ID);
            Assert.assertEquals(null, obtained);
        } catch (Exception e) {
            Assert.fail();
        }
    }
    
    /**
     * Create a list of notifications making different events get launched. 
     */
    @Test
    public void createNotificationsFromEvents() {
        try {
            ScenarioManager sm = new ScenarioManager(new TestScenario("TestScenario", 
                                            TestScenario.TEST_STATE, new Properties(), logger));
            NotificationManager nm = new NotificationManager(TestDefinitions.getSimulation(0));
            
            // Network Element Events           
            ProbabilisticNetworkElementEvent probNEE = new TestProbabilisticNetworkElementEvent(sm, logger);
            probNEE.launchEvent();
            PeriodicNetworkElementEvent perNEE = (PeriodicNetworkElementEvent)new TestPeriodicNetworkElementEvent(
                    sm);
            perNEE.launchEvent();
            
            //Scenario Events
            ProbabilisticScenarioEvent probSE = new TestProbabilisticScenarioEvent(sm);
            probSE.launchEvent();
            PeriodicScenarioEvent perSE = new TestPeriodicScenarioEvent(sm);
            perSE.launchEvent();
            
            // Action Event
            Action act = new TestAction(TestDefinitions.EVENT_ID+Action.class.getName(), sm);
            act.launchEvent();
            
            ArrayList<Notification> ln = nm.getByType(InteractionNotification.class);
            for (Notification in: ln){
                String interaction = ((InteractionNotification)in).getInteraction();
                if( interaction.equals(TestProbabilisticScenarioEvent.class.getName()) ||
                        interaction.equals(TestProbabilisticNetworkElementEvent.class.getName()) ||
                        interaction.equals(TestPeriodicScenarioEvent.class.getName()) ||
                        interaction.equals(TestPeriodicNetworkElementEvent.class.getName()) ||
                        interaction.equals(TestAction.class.getName())) {
                    break;
                } else {
                    Assert.fail();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    
    /**
     * Create a list of notifications making different events get launched. 
     */
    @Test
    public void createNotificationsOnSimulationRunTime() {
        
        try {
            ShanksSimulation sim = TestDefinitions.getSimulation(1);
            sim.start();
            do
                if (!sim.schedule.step(sim))
                    break;
            while (sim.schedule.getSteps() < TestDefinitions.IN_ITERATIONS);
            NotificationManager nm = sim.getNotificationManager();
            ArrayList<Notification> ln = nm.getByType(InteractionNotification.class);
            Assert.assertTrue(ln.size()>0);
            sim.finish();
            for (Notification in: ln){
                String interaction = ((InteractionNotification)in).getInteraction();
                if( interaction.equals(TestProbabilisticScenarioEvent.class.getName()) ||
                        interaction.equals(TestProbabilisticNetworkElementEvent.class.getName()) ||
                        interaction.equals(TestPeriodicScenarioEvent.class.getName()) ||
                        interaction.equals(TestPeriodicNetworkElementEvent.class.getName()) ||
                        interaction.equals(TestAgent.class.getName())) {
                } else {
                    Assert.fail();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    

}
