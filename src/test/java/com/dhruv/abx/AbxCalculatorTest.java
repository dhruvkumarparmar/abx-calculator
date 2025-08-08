package com.dhruv.abx;

import com.dhruv.abx.exception.InvalidInputException;
import com.dhruv.abx.exception.NegativeBaseException;
import com.dhruv.abx.exception.OverflowException;
import com.dhruv.abx.math.AbxCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AbxCalculatorTest {

    private static double relErr(double expected, double actual) {
        return Math.abs(expected - actual) / Math.max(1.0, Math.abs(expected));
    }

    @Test
    public void testSimpleIntegers() throws Exception {
        double r = AbxCalculator.compute(2.0, 3.0, 4.0);
        assertTrue(relErr(162.0, r) < 1e-9);
    }

    @Test
    public void testBaseOne() throws Exception {
        double r = AbxCalculator.compute(5.0, 1.0, 1000.0);
        assertEquals(5.0, r, 1e-12);
    }

    @Test
    public void testNegativeExponent() throws Exception {
        double r = AbxCalculator.compute(2.5, 2.0, -2.0);
        assertTrue(relErr(0.625, r) < 1e-9);
    }

    @Test
    public void testFractionalBase() throws Exception {
        double r = AbxCalculator.compute(5.0, 0.5, 3.0);
        assertTrue(relErr(0.625, r) < 1e-9);
    }

    @Test
    public void testInvalidBase() {
        assertThrows(NegativeBaseException.class, () -> AbxCalculator.compute(1.0, 0.0, 2.0));
        assertThrows(NegativeBaseException.class, () -> AbxCalculator.compute(1.0, -3.0, 2.0));
    }

    @Test
    public void testLargeOverflow() {
        assertThrows(OverflowException.class, () -> AbxCalculator.compute(1.0, 10.0, 400.0));
    }

    @Test
    public void testZeroA() throws Exception {
        double r = AbxCalculator.compute(0.0, 2.0, 100.0);
        assertEquals(0.0, r, 0.0);
    }
}
