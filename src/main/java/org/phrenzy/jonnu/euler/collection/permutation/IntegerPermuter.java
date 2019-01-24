package org.phrenzy.jonnu.euler.collection.permutation;

public class IntegerPermuter {

    public int[][] permute(final int n, final int offset) {

        final int[][] permutations = new int[factorial(n)][];

        final int[] work = new int[n];
        final int[] direction = new int[n];

        for (int i = 0; i < n; i++) {
            work[i] = i + offset;
            direction[i] = 0;
        }

        int step = 1;
        permutations[0] = copy(work);

        while (hasMobile(work, direction)) {

            final int curMobile = findLargestMobile(work, direction);

            // swap, b = (a += b -= a) - b;
            final int movePos = curMobile + (direction[curMobile] == 0 ? -1 : 1);
            work[movePos] = (work[curMobile] += work[movePos] -= work[curMobile]) - work[movePos];
            direction[movePos] = (direction[curMobile] += direction[movePos] -= direction[curMobile]) - direction[movePos];

            // reverse direction
            for (int i = 0; i < n; i++)
            if (work[i] > work[movePos]) {
                direction[i] = direction[i] == 0 ? 1 : 0;
            }

            // store
            permutations[step] = copy(work);
            step++;
        }

        return permutations;
    }

    private boolean hasMobile(final int[] work, final int[] direction) {

        for (int i = 0, n = work.length; i < n; i++) {
            if (isMobile(work, direction, i)) {
                return true;
            }
        }

        return false;
    }

    private boolean isMobile(final int[] work, final int[] direction, final int i) {

        // leftmost integer pointing to the left is not mobile
        // rightmost integer pointing to the right is not mobile
        if ((i == 0 && direction[i] == 0) || (i == work.length - 1 && direction[i] == 1)) {
            return false;
        }

        // An integer is mobile if, in the direction of its mobility, the
        // nearest integer is less than the current integer.
        if (i > 0 && direction[i] == 0 && work[i] > work[i - 1]) {
            return true;
        }

        if (i < work.length - 1 && direction[i] == 1 && work[i] > work[i + 1]) {
            return true;
        }

        if (i > 0 && i < work.length) {
            return (direction[i] == 0 && work[i] > work[i - 1]) || (direction[i] == 1 && work[i] > work[i + 1]);
        }

        return false;
    }

    private int findLargestMobile(final int[] work, final int[] direction) {

        int pos = -1;
        int largest = -1;

        for (int i = 0, n = work.length; i < n; i++) {
            if (isMobile(work, direction, i) && largest < work[i]) {
                largest = work[i];
                pos = i;
            }
        }

        return pos;
    }

    private int factorial(final int n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }

    private int[] copy(final int[] source) {
        int[] destination = new int[source.length];
        System.arraycopy(source, 0, destination, 0, source.length);
        return destination;
    }

}
