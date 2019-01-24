package org.phrenzy.jonnu.euler.collection.combinatoric;

public class IntegerCombination {

    public int[][] combinations(final int[] set, final int size) {
        return combinations(set, size, 0);
    }

    private int[][] combinations(final int[] set, final int size, int index) {

        //n! / ((n - m)! * m!) = combinations.
        int[][] combinations = new int[factorial(set.length) / (factorial(set.length - size) * factorial(size))][];

        if (size > set.length || size <= 0) {
            return combinations;
        }

        if (size == 1) {

            for (int i = 0; i < set.length; i++) {
                combinations[i] = new int[] { set[i] };
            }

            return combinations;
        }

        // Assert {1 < k < set.length}

        // Algorithm description:
        // To get k-combinations of a set, we want to join each element
        // with all (k-1)-combinations of the other elements. The set of
        // these k-sized sets would be the desired result. However, as we
        // represent sets with lists, we need to take duplicates into
        // account. To avoid producing duplicates and also unnecessary
        // computing, we use the following approach: each element i
        // divides the list into three: the preceding elements, the
        // current element i, and the subsequent elements. For the first
        // element, the list of preceding elements is empty. For element i,
        // we compute the (k-1)-computations of the subsequent elements,
        // join each with the element i, and store the joined to the set of
        // computed k-combinations. We do not need to take the preceding
        // elements into account, because they have already been the i:th
        // element so they are already computed and stored. When the length
        // of the subsequent list drops below (k-1), we cannot find any
        // (k-1)-combs, hence the upper limit for the iteration:
        //combs = [];

        for (int i = 0; i < set.length - size + 1; i++) {

            int[] head = slice(set, i, i + 1);
            int[][] tail = combinations(slice(set, i + 1), size - 1);

            for (int j = 0; j < tail.length; j++) {
                combinations[index++] = concat(head, tail[j]);
            }
        }

        return combinations;
    }

    private static int[] slice(int[] source, int start, int end) {
        int[] slice = new int[end - start];
        System.arraycopy(source, start, slice, 0, end - start);
        return slice;
    }

    private static int[] slice(int[] source, int start) {
        return slice(source, start, source.length);
    }

    private static int[] concat(int[] head, int[] tail) {

        int[] concatenated = new int[head.length + tail.length];
        System.arraycopy(head, 0, concatenated, 0, head.length);
        System.arraycopy(tail, 0, concatenated, head.length, tail.length);

        return concatenated;
    }

    private static int factorial(final int n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }
}
