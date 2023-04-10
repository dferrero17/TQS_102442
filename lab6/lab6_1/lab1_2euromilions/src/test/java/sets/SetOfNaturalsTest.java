/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
public class SetOfNaturalsTest {

    private SetOfNaturals setA;
    private SetOfNaturals setB;
    private SetOfNaturals setC;
    private SetOfNaturals setD;

    @BeforeEach
    public void setUp() {
        setA = new SetOfNaturals();
        setB = SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        setC = new SetOfNaturals();
        for (int i = 5; i < 50; i++) {
            setC.add(i * 10);
        }
        setD = SetOfNaturals.fromArray(new int[]{30, 40, 50, 60, 10, 20});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddBadArray() {
        int[] elems = new int[]{10, 20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }


    @Test
    public void testIntersectForNoIntersection() {
        assertFalse(setA.intersects(setB), "no intersection but was reported as existing");
        assertTrue(setB.intersects(setC), "Intersection found but was reported as non-existent");
        assertTrue(setB.intersects(setD), "Intersection found but was reported as non-existent");
    }

    @Test
    void testFromArray() {
        assertEquals(setB, SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60}));
    }

    @Test
    void testSize() {
        assertEquals(6, setB.size());
        assertEquals(45, setC.size());
        assertEquals(6, setD.size());
    }

    @Test
    void testContains() {
        assertTrue(setB.contains(20));
        assertFalse(setB.contains(100));
        assertTrue(setC.contains(100));
        assertTrue(setC.contains(80));
        assertFalse(setC.contains(52));
    }

    @Test
    void testDuplicatedElements() {

        assertThrows(IllegalArgumentException.class, () -> setB.add(20));
        assertThrows(IllegalArgumentException.class, () -> setC.add(70));

        assertThrows(IllegalArgumentException.class, () -> setB.add(new int[] {30, 32, 41}));
        assertThrows(IllegalArgumentException.class, () -> setB.add(new int[] {71, 50, 60}));

    }

    @Test
    void testNaturalNumbers() {

        assertThrows(IllegalArgumentException.class, () -> setB.add(0));
        assertThrows(IllegalArgumentException.class, () -> setB.add(-5));
        assertThrows(IllegalArgumentException.class, () -> setC.add(-15));

        assertThrows(IllegalArgumentException.class, () -> setB.add(new int[] {5,-1,0}));
        assertThrows(IllegalArgumentException.class, () -> setC.add(new int[] {5,0}));

    }

}
