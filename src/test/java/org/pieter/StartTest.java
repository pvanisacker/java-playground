package org.pieter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.pieter.bdc.input.HTTPIntervalInput;

public class StartTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testSomething() {
        assertEquals(1, 1);
    }

    @Test
    public void testReal() {

        HTTPIntervalInput input = new HTTPIntervalInput("localhost", 69, "lalal.txt");
        assertEquals("localhost", input.getHost());
        assertEquals(69, input.getPort());

    }
}
