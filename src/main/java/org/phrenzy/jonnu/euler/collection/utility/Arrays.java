package org.phrenzy.jonnu.euler.collection.utility;

import java.lang.reflect.Array;

public class Arrays {

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(final Class<T[]> type, final int x) {
        return (T[]) Array.newInstance(type, x);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] createArray(final Class<T[]> type, final int x, final int y) {
        return (T[][]) Array.newInstance(type, x, y);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] createJaggedArray(final Class<T[]> type, final int x) {
        return (T[][]) Array.newInstance(type, x);
    }

    /**
     * Concatenate two arrays together.
     *
     * Example:
     *  concat([1, 2, 3], [9, 8, 7]) = [1, 2, 3, 9, 8, 7]
     *
     * @param head Head array
     * @param tail Tail array
     * @param <T>
     * @return The result of concatenation
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] concat(final T[] head, final T[] tail) {
        T[] concatenated = createArray((Class<T[]>) head.getClass(), head.length + tail.length);
        System.arraycopy(head, 0, concatenated, 0, head.length);
        System.arraycopy(tail, 0, concatenated, head.length, tail.length);
        return concatenated;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] slice(T[] source, final int start, final int end) {
        T[] slice = createArray((Class<T[]>) source.getClass(), end - start);
        System.arraycopy(source, start, slice, 0, end - start);
        return slice;
    }

    public static <T> T[] slice(final T[] source, final int start) {
        return slice(source, start, source.length);
    }

}
