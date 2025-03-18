package grading;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;

import sim.SimController;
import sim.Simulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(24)
public class ControllerTest {
    Scanner scan = new Scanner(System.in);
    SimController simC;
    Simulator sim;
    JFrame mjf;

    HashMap<String,Component> cIdents;
    List<Component> allComponents;
    private int dupno = 0;

    void pause()
    {
        // System.out.print("Press any key to continue . . . ");
        // scan.nextLine();
        JOptionPane.showMessageDialog(mjf,"pausing");
    }

    void sleep(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @BeforeEach
    public void setup(){
        simC = new SimController();
        sim = simC.getSimulator();

        Frame[] jf = JFrame.getFrames();

        // get all components

        Optional<Frame> scf = Arrays.stream(jf).filter(f->f.getTitle().equals("Sim Control")).findAny();
        mjf = (JFrame) scf.get();
        allComponents = SwUtils.getAllComponents(mjf);

        // Move from allComponents to Hashmap; decide what to identify them as
        cIdents = new HashMap<>();
        for(Component c: allComponents){
            String ident = SwUtils.cIdent(c);
            if( cIdents.containsKey(ident) ) ident = "duplicate" + dupno++;
            Component old =  cIdents.putIfAbsent(ident, c);
        }

    }

    @AfterEach
    public void teardown(){
        mjf.setTitle("Dead Controller"); // prevent re-activation
        simC.quit();

    }


    @Test
    @Order(15)
    @GradeValue(2)
    public void runButton() {
        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        b.doClick();
        int step1 = sim.getStep();
        String status1 = sim.getDetails();
        sleep(1000);
        int step2 = sim.getStep();
        String status2 = sim.getDetails();
        assertNotEquals(step1, step2);
        assertNotEquals(status1, status2);
    }

    @Test
    @Order(16)
    @GradeValue(2)
    public void runStopButton() {
        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        b.doClick();
        int step1 = sim.getStep();
        String status1 = sim.getDetails();
        sleep(1000);
        assertTrue(cIdents.containsKey("Stop"));
        b = (JButton) cIdents.get("Stop");
        b.doClick();
        int step2 = sim.getStep();
        String status2 = sim.getDetails();
        assertNotEquals(step1, step2);
        assertNotEquals(status1, status2);
        step1 = sim.getStep();
        status1 = sim.getDetails();
        assertEquals(step1, step2);
        assertEquals(status1, status2);
    }

    private JLabel findStatusLabel(){
        for(Component jc : cIdents.values()){
            if(! ( jc instanceof JLabel) ) continue;
            JLabel jcl = (JLabel) jc;
            String labText = jcl.getText().toUpperCase();
            if( labText.contains("FOX") ) return jcl;
            if( labText.contains("RABBIT") ) return jcl;
            if( labText.contains("STATUS") ) return jcl;
        }
        return null;
    }

    private JLabel findRunningLabel() {
        for (String id : cIdents.keySet()) {
            if (!id.toUpperCase().contains("RUNNING")) continue;
            if (!id.toUpperCase().contains("SIM")) continue;
            if (!(cIdents.get(id) instanceof JLabel)) continue;
            return (JLabel) cIdents.get(id);
        }
        return null;
    }

    @Order(1)
    @GradeValue(1)
    @Test
    public void statusLabel() {
        assertNotNull(findStatusLabel());
    }

    @Order(1)
    @GradeValue(1)
    @Test
    public void runningLabel() {
        assertNotNull(findRunningLabel());
    }

    @Order(10)
    @GradeValue(2)
    @Test
    public void stepButton() {
        assertTrue(cIdents.containsKey("Step"));
        JButton b = (JButton) cIdents.get("Step");

        for( int i =0; i<10; i++) {
            b.doClick();
            int step1 = sim.getStep();
            String status1 = sim.getDetails();

            b.doClick();
            int step2 = sim.getStep();
            String status2 = sim.getDetails();

            assertEquals(step1 + 1, step2);
        }
    }

    @Test
    @Order(11)
    @GradeValue(2)
    public void stepStatusLabel() {
        assertTrue(cIdents.containsKey("Step"));
        JButton b = (JButton) cIdents.get("Step");
        JLabel status = findStatusLabel();

        for( int i =0; i<10; i++) {
            b.doClick();
            int step1 = sim.getStep();
            String status1 = sim.getDetails();
            assertTrue(status.getText().contains(Integer.toString(step1)));
            assertTrue(status.getText().contains(status1));

            b.doClick();
            int step2 = sim.getStep();
            String status2 = sim.getDetails();
            assertTrue(status.getText().contains(Integer.toString(step2)));
            assertTrue(status.getText().contains(status2));

            assertEquals(step1 + 1, step2);
        }
    }

    @Test
    @Order(19)
    @GradeValue(2)
    public void runStopRunningLabel() {
        JLabel running = findRunningLabel();
        String labText = running.getText().toUpperCase();
        assertTrue(labText.contains("NOT"));

        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        b.doClick();
        labText = running.getText().toUpperCase();
        assertFalse(labText.contains("NOT"));

        assertTrue(cIdents.containsKey("Stop"));
        b = (JButton) cIdents.get("Stop");
        b.doClick();

        labText = running.getText().toUpperCase();
        assertTrue(labText.contains("NOT"));;
    }

    @Order(23)
    @GradeValue(2)
    @Test
    public void fasterButton() {

        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");
        
        assertTrue(cIdents.containsKey("Stop"));
        JButton s = (JButton) cIdents.get("Stop");

        assertTrue(cIdents.containsKey("Faster"));
        JButton f = (JButton) cIdents.get("Faster");
        f.doClick();

        b.doClick();
        int step1 = sim.getStep();
        sleep(3000);
        int step2 = sim.getStep();

        f.doClick();
        f.doClick();

        int step3 = sim.getStep();
        sleep(3000);
        int step4 = sim.getStep();
        
        s.doClick();

        assertTrue( (step2-step1) * 2 < (step4 - step3), "Did not speed up enough");
    }

    @Order(24)
    @GradeValue(2)
    @Test
    public void slowerButton() {

        assertTrue(cIdents.containsKey("Run"));
        JButton b = (JButton) cIdents.get("Run");

        assertTrue(cIdents.containsKey("Stop"));
        JButton s = (JButton) cIdents.get("Stop");
        
        assertTrue(cIdents.containsKey("Slower"));
        JButton f = (JButton) cIdents.get("Slower");
        f.doClick();

        b.doClick();
        int step1 = sim.getStep();
        sleep(3000);
        int step2 = sim.getStep();

        f.doClick();
        f.doClick();

        int step3 = sim.getStep();
        sleep(3000);
        int step4 = sim.getStep();
        
        s.doClick();

        assertTrue( (step2-step1) > 2 * (step4 - step3), "Did not slow down enough");
    }
}


/**
 * $Id: WindowUtils.java,v 1.16 2009/05/25 16:37:52 kschaefe Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Taken from http://www.java2s.com/Code/Java/Swing-JFC/GetAllComponentsinacontainer.htm
 *
 * Encapsulates various utilities for windows (ie: <code>Frame</code> and
 * <code>Dialog</code> objects and descendants, in particular).
 *
 * @author Richard Bair
 * @author modified E Brown Nov 2022
 */
class SwUtils {
    public static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }

    /* string for identing component in tests */
    public static String cIdent(Component c){

        if( c instanceof JFrame) return ((JFrame) c).getTitle();
        if( c instanceof JLabel) return ((JLabel) c).getText();
        if( c instanceof JButton) return ((JButton) c).getText();
        if( c instanceof JMenu) return ((JMenu) c).getText();
        if( c instanceof JMenuItem) return ((JMenuItem) c).getText();
        if( c.getName() != null ) return c.getName();
        return c.toString();
    }
}

