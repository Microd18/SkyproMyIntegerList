package module;

import exceptions.ElementNotFoundException;
import exceptions.InvalidIndexException;
import exceptions.NullItemException;
import interfaces.IntegerList;

import java.util.Arrays;

public class MyIntegerList implements IntegerList {

    public int SIZE = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Integer[] array;



    public MyIntegerList() {
        array = new Integer[DEFAULT_CAPACITY];
    }

    public MyIntegerList(int size) {
        array = new Integer[size];
    }

    @Override
    public Integer add(Integer item) {
        validateItem(item);

        if (array.length == SIZE) {
            grow();
        }

        return array[SIZE++] = item;
    }

    @Override
    public Integer add(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        if (array.length - 1 == SIZE) {
            grow();
        }
        if (index == SIZE) {
            array[SIZE++] = item;
            return item;
        }

        System.arraycopy(array, index, array, index + 1, SIZE - index);

        SIZE++;

        return array[index] = item;

    }

    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        return array[index] = item;
    }

    @Override
    public Integer remove(Integer item) {

        validateItem(item);

        int index = indexOf(item);

        if (index == -1) {
            throw new ElementNotFoundException();
        }

        System.arraycopy(array, index + 1, array, index, SIZE - index);

        SIZE--;

        return item;
    }

    @Override
    public Integer remove(int index) {
        validateIndex(index);

        Integer result = array[index];

        System.arraycopy(array, index + 1, array, index, SIZE - index);

        SIZE--;

        return result;
    }

    @Override
    public boolean contains(Integer item) {
        Integer[] storageCopy = toArray();
        sort(storageCopy);
        return binarySearch(storageCopy, item);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < SIZE; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = SIZE - 1; i > 0; i--) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return SIZE == 0;
    }

    @Override
    public void clear() {
        SIZE = 0;
        Arrays.fill(array, null);
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(array, SIZE);
    }

    private void grow() {
        array = Arrays.copyOf(array, (array.length * 2));
    }

    @Override
    public String toString() {
        return "MyArrayList{" + Arrays.toString(array) +
                '}';
    }

    public void validateIndex(int index) {
        if (index < 0 || index > SIZE)
            throw new InvalidIndexException();
    }

    public void validateItem(Integer item) {
        if (item == null) {
            throw new NullItemException();
        }
    }

    private void sort(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i;
            while (j > 0 && array[j - 1] >= temp) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = temp;
        }
    }

    private boolean binarySearch(Integer[] array, int element) {
        int min = 0;
        int max = array.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == array[mid]) {
                return true;
            }

            if (element < array[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

}
