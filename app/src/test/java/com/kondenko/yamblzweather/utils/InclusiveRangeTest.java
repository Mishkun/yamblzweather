package com.kondenko.yamblzweather.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mishkun on 22.07.2017.
 */
public class InclusiveRangeTest {
    @Test
    public void mustBeInRange() throws Exception {
        InclusiveRange inclusiveRange = new InclusiveRange(-1, 1);
        assertTrue(inclusiveRange.inRange(0));
        assertTrue(inclusiveRange.inRange(-1));
        assertTrue(inclusiveRange.inRange(1));

        InclusiveRange inclusiveRange1 = new InclusiveRange(1);
        assertTrue(inclusiveRange1.inRange(1));
    }

    @Test
    public void mustBeOutOfRange() throws Exception {
        InclusiveRange inclusiveRange = new InclusiveRange(-1, 1);
        assertFalse(inclusiveRange.inRange(-2));
        assertFalse(inclusiveRange.inRange(2));

        InclusiveRange inclusiveRange1 = new InclusiveRange(1);
        assertFalse(inclusiveRange1.inRange(2));
        assertFalse(inclusiveRange1.inRange(0));
    }

    @Test
    public void mustBeEqual() throws Exception {
        InclusiveRange inclusiveRange = new InclusiveRange(-1, 1);
        InclusiveRange inclusiveRange1 = new InclusiveRange(1, -1);
        assertTrue(inclusiveRange.equals(inclusiveRange1));
        assertTrue(inclusiveRange1.equals(inclusiveRange));

        InclusiveRange inclusiveRange2 = new InclusiveRange(1, 1);
        InclusiveRange inclusiveRange3 = new InclusiveRange(1);
        assertTrue(inclusiveRange2.equals(inclusiveRange3));
        assertTrue(inclusiveRange3.equals(inclusiveRange2));
    }


    @Test
    public void mustBeNotEqual() throws Exception{
        InclusiveRange inclusiveRange = new InclusiveRange(-1, 1);
        InclusiveRange inclusiveRange1 = new InclusiveRange(1, -2);
        assertFalse(inclusiveRange.equals(inclusiveRange1));
        assertFalse(inclusiveRange1.equals(inclusiveRange));

        InclusiveRange inclusiveRange2 = new InclusiveRange(2);
        InclusiveRange inclusiveRange3 = new InclusiveRange(1);
        assertFalse(inclusiveRange2.equals(inclusiveRange3));
        assertFalse(inclusiveRange3.equals(inclusiveRange2));

        assertFalse(inclusiveRange.equals(inclusiveRange2));
    }
    @Test
    public void testHashCode() throws Exception {
        InclusiveRange inclusiveRange = new InclusiveRange(-1, 1);
        InclusiveRange inclusiveRange1 = new InclusiveRange(1, -1);
        assertEquals(inclusiveRange.hashCode(), inclusiveRange1.hashCode());
    }

}