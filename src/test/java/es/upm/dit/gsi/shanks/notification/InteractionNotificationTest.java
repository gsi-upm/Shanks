package es.upm.dit.gsi.shanks.notification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.notification.test.TestDefinitions;

public class InteractionNotificationTest {

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
                TestDefinitions.IN_ID, TestDefinitions.IN_WHEN,
                TestDefinitions.IN_SOURCE, TestDefinitions.IN_INTERACTION,
                TestDefinitions.IN_TARGET);

        Assert.assertTrue(iNotification.getId().equals(TestDefinitions.IN_ID));
        Assert.assertTrue(iNotification.getWhen() == TestDefinitions.IN_WHEN);
        Assert.assertTrue(iNotification.getSource().equals(
                TestDefinitions.IN_SOURCE));
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
            ln.add(new InteractionNotification(TestDefinitions.IN_ID+i, 
                    TestDefinitions.IN_WHEN, TestDefinitions.IN_SOURCE, 
                    TestDefinitions.IN_INTERACTION, TestDefinitions.IN_TARGET));
        }
        
        try {
            NotificationManager nm = new NotificationManager(ln, null, TestDefinitions.getSimulation());
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
     * make queries to the NotificationManager. 
     */
    @Test
    public void checkIDQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            ln.add(new InteractionNotification(TestDefinitions.IN_ID+i, 
                    TestDefinitions.IN_WHEN, TestDefinitions.IN_SOURCE, 
                    TestDefinitions.IN_INTERACTION, TestDefinitions.IN_TARGET));
        }
        
        ArrayList<Notification> obtained_ln = new ArrayList<Notification>();
        NotificationManager nm = null;
        try {
            nm = new NotificationManager(ln, null, TestDefinitions.getSimulation());
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
     * make queries to the NotificationManager. 
     */
    @Test
    public void checkStepQueries() {
        
        ArrayList<Notification> ln = new ArrayList<Notification>();
        HashMap<String, Integer> stepCount = new HashMap<String, Integer>();
        
        for (int i = 0; i < TestDefinitions.NOTIFICATIONS_SIZE; i++) {
            Long step = Math.round(TestDefinitions.IN_WHEN*Math.random());
            if(stepCount.containsKey(step.toString())){
                stepCount.put(step.toString(), stepCount.get(step.toString())+1);
            } else {
                stepCount.put(step.toString(), 1);
            }
            ln.add(new InteractionNotification(TestDefinitions.IN_ID+i, step, 
                    TestDefinitions.IN_SOURCE, TestDefinitions.IN_INTERACTION, 
                    TestDefinitions.IN_TARGET));
        }
        
        ArrayList<Notification> obtained_ln = new ArrayList<Notification>();
        NotificationManager nm = null;
        try {
            nm = new NotificationManager(ln, null, TestDefinitions.getSimulation());
            obtained_ln= nm.getByType(InteractionNotification.class);
            Assert.assertEquals(ln, obtained_ln);
            
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
     * Create a list of notifications making different events get launched. 
     */
    @Test
    public void createNotificationsFromEvents() {

//        Assert.assertTrue(catched);
    }
    

}
