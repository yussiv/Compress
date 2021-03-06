package fi.yussiv.squash.util;

/**
 * A Dynamically sized array of bytes.
 */
public class ByteArray {

    private static final int INITIAL_SIZE = 1024;
    private byte[] array;
    private int size;

    public ByteArray() {
        this.array = new byte[INITIAL_SIZE];
        this.size = 0;
    }

    public void add(byte b) {
        if (size == array.length) {
            resizeArray();
        }
        array[size++] = b;
    }

    public void add(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            add(array[i]);
        }
    }

    public void set(int index, byte b) {
        array[index] = b;
    }

    public int size() {
        return size;
    }

    public byte[] getBytes() {
        byte[] output = new byte[size];
        for (int i = 0; i < size; i++) {
            output[i] = array[i];
        }
        return output;
    }

    private void resizeArray() {
        byte[] newArray = new byte[size * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public byte get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return array[index];
    }

    public ByteArray duplicate() {
        byte[] newArr = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            newArr[i] = array[i];
        }
        ByteArray duplicate = new ByteArray();
        duplicate.array = newArr;
        duplicate.size = this.size;
        return duplicate;
    }
    
    /**
     * Doesn't really reset the array contents, only sets the size to zero, so
     * it will be filled from the beginning next time.
     */
    public void clear() {
        size = 0;
    }
}
