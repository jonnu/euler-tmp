package org.phrenzy.jonnu.euler.math.prime;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EratosthenesSieve implements Iterator<Integer> {

    private final BitSet cache;

    private int delta;
    private int limit;
    private int index;

    public EratosthenesSieve() {
        cache = new BitSet();
        limit = 0;
        index = 0;
        delta = 0;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return cache.nextSetBit(index + 1) > 0;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Integer next() {

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return cache.nextSetBit(index++);
    }


    private void sieve(final int maximum) {

        if (maximum <= limit) {
            return;
        }

        for (int i = 2; i < Math.sqrt(maximum); i++) {
            if (cache.get(i)) {
                for (int j = 2 * i; j < maximum; j += i) {
                    cache.clear(j);
                }
            }
        }

        limit = maximum;
    }

    private void sieve(final int minimum, final int maximum) {


    }


    private void segment() {

    }

    // @TODO segmented sieve of eratosthenes.
    // use interface from: https://github.com/hickford/primesieve-python
    private void sieve(final int minimum, final int maximum, final int delta) {

        assert minimum % 2 == 0;
        assert maximum % 2 == 0;
        assert minimum < maximum;

        int maximumRoot = (int) Math.sqrt(maximum);
        assert minimum > maximumRoot;

        int segment = delta * 2;

        BitSet deltaCache = new BitSet(delta);

        sieve(maximumRoot);
        int[] primes = collect(maximumRoot);
        int[] offset = new int[primes.length];

        int position = minimum;
        while (position < maximum) {

            // reset bits
            deltaCache.set(0, delta, true);

            //for p,q in ps,qs
            //  for i from q to delta step p
            //    sieve[i] := False
            for (int i = 0, p = primes[i], q = offset[i]; q < delta; i += p) {
                deltaCache.clear(i);
            }

            for (int i = 0; i < primes.length; i++) {
                offset[i] = deltaOffset(primes[i], offset[i], delta);
            }

            position = position + 2 * delta;
        }
    }

    private static int deltaOffset(final int prime, final int offset, final int delta) {
        return (offset - delta) % prime;
    }

    private int[] collect(final int maximum) {

        final BitSet subset = cache.get(0, limit);
        final int[] primes = new int[subset.cardinality()];

        int collected = 0;
        for (int i = subset.nextSetBit(0); i >= 0 && i <= maximum; i = subset.nextSetBit(i + 1)) {
            primes[collected++] = i;
        }

        final int[] result = new int[collected];
        System.arraycopy(primes, 0, result, 0, collected);

        return result;
    }

}
