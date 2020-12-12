/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;

/**
 * A helper class that aims to provide comparable growth performance to ArrayList, but on primitive
 * arrays. Common array operations are implemented for efficient use in dynamic containers.
 *
 * All methods in this class assume that the length of an array is equivalent to its capacity and
 * NOT the number of elements in the array. The current size of the array is always passed in as a
 * parameter.
 *
 * @hide
 */
public final class GrowingArrayUtils {

    /**
     * Appends an element to the end of the array, growing the array if there is no more room.
     * @param array The array to which to append the element. This must NOT be null.
     * @param currentSize The number of elements in the array. Must be less than or equal to
     *                    array.length.
     * @param element The element to append.
     * @return the array to which the element was appended. This may be different than the given
     *         array.
     */
    @UnsupportedAppUsage
    public static <T> T[] append(T[] array, int currentSize, T element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            @SuppressWarnings("unchecked")
            T[] newArray = ArrayUtils.newUnpaddedArray(
                    (Class<T>) array.getClass().getComponentType(), growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Primitive int version of {@link #append(Object[], int, Object)}.
     */
    @UnsupportedAppUsage
    public static int[] append(int[] array, int currentSize, int element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            int[] newArray = ArrayUtils.newUnpaddedIntArray(growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Primitive long version of {@link #append(Object[], int, Object)}.
     */
    public static long[] append(long[] array, int currentSize, long element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            long[] newArray = ArrayUtils.newUnpaddedLongArray(growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Primitive boolean version of {@link #append(Object[], int, Object)}.
     */
    public static boolean[] append(boolean[] array, int currentSize, boolean element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            boolean[] newArray = ArrayUtils.newUnpaddedBooleanArray(growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Primitive float version of {@link #append(Object[], int, Object)}.
     */
    public static float[] append(float[] array, int currentSize, float element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            float[] newArray = ArrayUtils.newUnpaddedFloatArray(growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Inserts an element into the array at the specified index, growing the array if there is no
     * more room.
     *
     * @param array The array to which to append the element. Must NOT be null.
     * @param currentSize The number of elements in the array. Must be less than or equal to
     *                    array.length.
     * @param element The element to insert.
     * @return the array to which the element was appended. This may be different than the given
     *         array.
     */
    public static <T> T[] insert(T[] array, int currentSize, int index, T element) {
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

        @SuppressWarnings("unchecked")
        T[] newArray = ArrayUtils.newUnpaddedArray((Class<T>)array.getClass().getComponentType(),
                growSize(currentSize));
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    /**
     * @param array       当前的数组列表
     * @param currentSize 当前的数组大小
     * @param index       插入的索引值
     * @param element     插入的元素
     */
    public static int[] insert(int[] array, int currentSize, int index, int element) {
        assert currentSize <= array.length;

        // 不需要扩容
        if (currentSize + 1 <= array.length) {
            // 进行一次数组拷贝
            // 将array数组内从index移到index + 1，即后移一个元素。共移了 currentSize - index 个，
            // 也就是说将index之后的所有元素都后移一个位置
            // 即从index开始后移一位，那么就留出 index 的位置来插入新的值。
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            // 然后将现在数组的index的位置处元素存储为element
            array[index] = element;
            return array;
        }
        //需要扩容，构建新的数组，新的数组大小由growSize() 计算得到
        int[] newArray = ArrayUtils.newUnpaddedIntArray(growSize(currentSize));
        // //这里再分 3 段赋值。首先将原数组中 index 之前的数据复制到新数组中
        // 1:将index之前的元素都拷贝到新的数组中
        System.arraycopy(array, 0, newArray, 0, index);
        // 2:将index索引处的元素设置为element
        newArray[index] = element;
        // 将Index索引之后的元素拷贝到新的数组中
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    /**
     * Primitive long version of {@link #insert(Object[], int, int, Object)}.
     */
    public static long[] insert(long[] array, int currentSize, int index, long element) {
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

        long[] newArray = ArrayUtils.newUnpaddedLongArray(growSize(currentSize));
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    /**
     * Primitive boolean version of {@link #insert(Object[], int, int, Object)}.
     */
    public static boolean[] insert(boolean[] array, int currentSize, int index, boolean element) {
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

        boolean[] newArray = ArrayUtils.newUnpaddedBooleanArray(growSize(currentSize));
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    /**
     * Given the current size of an array, returns an ideal size to which the array should grow.
     * This is typically double the given size, but should not be relied upon to do so in the
     * future.
     * 扩容为8，或者当前容量的2倍
     */
    public static int growSize(int currentSize) {
        return currentSize <= 4 ? 8 : currentSize * 2;
    }

    // Uninstantiable
    private GrowingArrayUtils() {}
}
