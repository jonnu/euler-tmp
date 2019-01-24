package org.phrenzy.jonnu.euler.problem;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.phrenzy.jonnu.euler.Problem;
import org.phrenzy.jonnu.euler.collection.combinatoric.Combination;
import org.phrenzy.jonnu.euler.collection.combinatoric.IntegerCombination;
import org.phrenzy.jonnu.euler.math.prime.PrimeCache;

public class Euler51 implements Problem {

    private final PrimeCache primeCache = new PrimeCache();
    private final IntegerCombination combination = new IntegerCombination();
    //private final IntegerCombination combination = new IntegerCombination();

    /**
     * By replacing the 1st digit of the 2-digit number *3, it turns out that six
     * of the nine possible values: 13, 23, 43, 53, 73, and 83, are all prime.
     *
     * By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit
     * number is the first example having seven primes among the ten generated numbers,
     * yielding the family: 56003, 56113, 56333, 56443, 56663, 56773, and 56993.
     * Consequently 56003, being the first member of this family, is the smallest prime
     * with this property.
     *
     * Find the smallest prime which, by replacing part of the number (not necessarily
     * adjacent digits) with the same digit, is part of an eight prime value family.
     */
    public void solve() {

        primeCache.sieve(20000000);
        for (int prime : primeCache.primes()) {
            permutePrimes(prime, 8);
        }
    }

    private boolean hasNPrimeFamily(final int[] values, final int size) {

        int familySize = (int) IntStream.of(values)
                .filter(primeCache::isPrime)
                .count();

        return familySize == size;
    }

    private void permutePrimes(int input, int familySize) {

        primeCache.sieve(getMaximumValueOfLength(getNumberOfDigits(input)));
        int[][] combinations = combination.combinations(IntStream.rangeClosed(0, getNumberOfDigits(input)).toArray(), 3);

        Arrays.stream(combinations)
                .filter(permutation -> getNumberOfDigits(input) >= permutation.length)
                .forEach(permutation -> {

            int[] primeFamily = Arrays.stream(permuteDigitsAtPositions(input, permutation))
                    .filter(x -> getNumberOfDigits(x) == getNumberOfDigits(input))
                    .filter(primeCache::isPrime)
                    .toArray();

            if (hasNPrimeFamily(primeFamily, familySize) && Arrays.binarySearch(primeFamily, input) >= 0) {
                System.out.println("Input: " + input + "; Permutation of digit replacement: " + Arrays.toString(permutation));
                System.out.println("Produces prime family: " + Arrays.toString(primeFamily) + " (primes: " + primeFamily.length + ")");
                System.exit(0);
            }
        });
    }

    private int[] permuteDigitsAtPosition(int input, int index) {

        int digitPower = getNthDigitExtracted(input, index);
        int normalized = input - digitPower;

        return IntStream.rangeClosed(0, 9)
                .map(x -> getNthDigitPower(input, index) * x)
                .map(x -> normalized + x)
                .toArray();
    }

    private int[] permuteDigitsAtPosition(int input, int[] indexes) {
        return IntStream.of(indexes)
                .mapToObj(index -> permuteDigitsAtPosition(input, index))
                .flatMapToInt(IntStream::of)
                .distinct()
                .toArray();
    }

    private int[] permuteDigitsAtPositions(int input, int[] indexes) {

        int digitPower = IntStream.of(indexes)
                .map(index -> getNthDigitExtracted(input, index))
                .sum();

        int normalized = input - digitPower;

        return IntStream.rangeClosed(0, 9)
                .map(x -> IntStream.of(indexes).map(index -> getNthDigitPower(input, index) * x).sum())
                .map(x -> normalized + x)
                .toArray();
    }

    private int getNthPower(int n) {
        return (int) Math.pow(10, n);
    }

    private int getNthDigit(int input, int n) {
        final int power = (int) Math.log10(input) - n;
        return (int) (input / Math.pow(10, power)) % 10;
    }

    private int getNthDigitPower(int input, int n) {
        return getNthPower((int) Math.log10(input) - n);
    }

    private int getNthDigitExtracted(int input, int n) {
        return getNthDigit(input, n) * getNthDigitPower(input, n);
    }

    private int getNumberOfDigits(int input) {
        return (int) Math.log10(input) + 1;
    }

    private int getMaximumValueOfLength(int length) {
        return IntStream.rangeClosed(0, length)
                .map(index -> getNthPower(index) * 9)
                .sum();
    }
}
