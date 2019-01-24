package org.phrenzy.jonnu.euler.math.prime;

import java.util.BitSet;

public class PrimeCache {

    private final BitSet bitset;
    private int lastLimit;

    public PrimeCache() {
        bitset = new BitSet();
        bitset.set(0, Integer.MAX_VALUE, true);
        lastLimit = 2;
        bitset.set(0, 2, false);
    }

    public boolean isPrime(final int value) {

        if (value > lastLimit) {
            System.out.println("value: " + value + "; limit: " + lastLimit);
            sieve(value);
        }

        return bitset.get(value);
    }

    public void sieve(final int limit) {

        if (limit <= lastLimit) {
            return;
        }

        for (int i = 2; i < Math.sqrt(limit); i++) {
            if (bitset.get(i)) {
                for (int j = i * 2; j < limit; j += i) {
                    bitset.clear(j);
                }
            }
        }

        lastLimit = limit;
    }

    public int[] primes() {

        final BitSet subset = bitset.get(0, lastLimit);

        int[] primes = new int[subset.cardinality()];
        for (int i = subset.nextSetBit(0), j = 0; i >= 0; i = subset.nextSetBit(i + 1)) {
            primes[j++] = i;
        }

        return primes;
    }

}

