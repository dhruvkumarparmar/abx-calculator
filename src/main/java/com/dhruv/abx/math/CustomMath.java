package com.dhruv.abx.math;

/**
 * Minimal custom math utilities to avoid Math.pow() and demonstrate "from scratch" approach.
 * NOTE: For production, prefer java.lang.Math. This class is educational and suitable for D2/D3.
 */
public final class CustomMath {
    private CustomMath() {}

    /** Absolute value */
    public static double abs(double x) { return x < 0 ? -x : x; }

    /** e^x via series with simple range reduction. */
    public static double exp(double x) {
        // clamp extremes to avoid overflow/underflow
        if (x > 700) return Double.POSITIVE_INFINITY;
        if (x < -745) return 0.0;

        // range reduction: e^(x) = (e^(x/N))^N with N=8
        int N = 8;
        double y = x / N;
        double t = 1.0;
        double term = 1.0;
        for (int n = 1; n <= 40; n++) {
            term *= y / n;
            t += term;
            if (abs(term) < 1e-16) break;
        }
        // repeated squaring (N times multiply)
        double result = 1.0;
        for (int i = 0; i < N; i++) result *= t;
        return result;
    }

    /**
     * Natural log via atanh series on (0, ∞): ln(x) = 2 * [ z + z^3/3 + z^5/5 + ... ], where
     * z = (x-1)/(x+1). Converges best when x close to 1. Use scaling by powers of 2.
     */
    public static double ln(double x) {
        if (x <= 0) return Double.NaN;
        // scale x to m * 2^k with m in [0.75, 1.5] approx using binary scaling
        int k = 0;
        double m = x;
        // bring m near 1 using powers of 2
        while (m > 1.5) { m *= 0.5; k++; }
        while (m < 0.75) { m *= 2.0; k--; }

        double z = (m - 1.0) / (m + 1.0);
        double z2 = z * z;
        double sum = 0.0;
        double term = z;
        for (int n = 1; n <= 99; n += 2) {
            sum += term / n;
            term *= z2;
            if (abs(term) < 1e-16) break;
        }
        double ln_m = 2.0 * sum;
        // ln(x) = ln(m) + k*ln(2)
        return ln_m + k * LN2;
    }

    public static final double LN2 = 0.6931471805599453d;
}
