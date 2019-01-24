package org.phrenzy.jonnu.euler.math.utility;

public class Sequence {

    public static int factorial(final int n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }
}
