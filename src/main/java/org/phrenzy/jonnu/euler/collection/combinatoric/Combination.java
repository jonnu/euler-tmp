package org.phrenzy.jonnu.euler.collection.combinatoric;

import static org.phrenzy.jonnu.euler.collection.utility.Arrays.concat;
import static org.phrenzy.jonnu.euler.collection.utility.Arrays.createArray;
import static org.phrenzy.jonnu.euler.collection.utility.Arrays.createJaggedArray;
import static org.phrenzy.jonnu.euler.collection.utility.Arrays.slice;
import static org.phrenzy.jonnu.euler.math.utility.Sequence.factorial;

public class Combination<T> {

    public T[][] combinations(final T[] set, final int size) {
        return combinations(set, size, 0);
    }

    @SuppressWarnings("unchecked")
    private Class<T[]> getType(T[] object) {
        return (Class<T[]>) object.getClass();
    }

    private T[][] combinations(final T[] set, final int size, int index) {

        Class<T[]> type = getType(set);

        //n! / ((n - m)! * m!) = combinations.
        T[][] combinations = createJaggedArray(type,factorial(set.length) / (factorial(set.length - size) * factorial(size)));

        if (size > set.length || size <= 0) {
            return combinations;
        }

        if (size == 1) {

            for (int i = 0; i < set.length; i++) {
                T[] combination = createArray(type, 1);
                combination[0] = set[i];
                combinations[i] = combination;
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

            T[] head = slice(set, i, i + 1);
            T[][] tail = combinations(slice(set, i + 1), size - 1);

            for (int j = 0; j < tail.length; j++) {
                combinations[index++] = concat(head, tail[j]);
            }
        }

        return combinations;
    }
}
