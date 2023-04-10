/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import sets.SetOfNaturals;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ico0
 */
public class DipTest {

    private Dip instance;


    public DipTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2});
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }


    @Test
    public void testConstructorFromBadArrays() {

        assertDoesNotThrow(() -> new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2}));

        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{10, 20, 30, 40}, new int[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{10, 20, 30, 40, 50, 60}, new int[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{10, 20, 30, 40, 50, 60}, new int[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{10, 20, 30, 40, 50, 60}, new int[]{1}));

    }

    @Test
    public void testFormat() {
        // note: correct the implementation of the format(), not the test...
        String result = instance.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

    @Test
    @DisplayName("Test if the range of stars is from 1-12")
    public void testStarsRange() {

        SetOfNaturals allowedRange = new SetOfNaturals();
        allowedRange.add(IntStream.rangeClosed(1, Dip.RANGE_OF_STARS).boxed().mapToInt(i->i).toArray());

        instance.getStarsColl().forEach(integer -> assertTrue(allowedRange.contains(integer)));

    }

}
