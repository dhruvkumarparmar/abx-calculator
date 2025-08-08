package com.dhruv.abx.math;

import com.dhruv.abx.exception.InvalidInputException;
import com.dhruv.abx.exception.NegativeBaseException;
import com.dhruv.abx.exception.OverflowException;

public final class AbxCalculator {
    private AbxCalculator() {}

    /**
     * Compute a * b^x without using Math.pow().
     * Uses identity: b^x = exp(x * ln(b)) with CustomMath.exp/ln.
     */
    public static double compute(double a, double b, double x)
            throws InvalidInputException, NegativeBaseException, OverflowException {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(x) ||
            Double.isInfinite(a) || Double.isInfinite(b) || Double.isInfinite(x)) {
            throw new InvalidInputException("Inputs must be finite real numbers.");
        }
        if (b <= 0.0) {
            throw new NegativeBaseException("Base b must be greater than 0 for real-valued result.");
        }

        // Handle quick cases
        if (b == 1.0) return a;              // b^x == 1
        if (x == 0.0) return a;              // b^0 == 1
        if (a == 0.0) return 0.0;

        double ln_b = CustomMath.ln(b);
        if (Double.isNaN(ln_b)) throw new InvalidInputException("ln(b) not defined for b <= 0.");
        double y = x * ln_b;
        double pow = CustomMath.exp(y);
        double result = a * pow;

        if (Double.isInfinite(result)) {
            throw new OverflowException("Result too large to represent (overflow).");
        }
        if (result == 0.0 && a != 0.0 && pow != 0.0) {
            // underflow to zero
            return 0.0;
        }
        return result;
    }
}
