package fi.yussiv.squash.domain;

import java.io.Serializable;

/**
 * Utility data structure used in the building of a huffman encoding/decoding
 * dictionary.
 */
public class HuffmanTree implements Serializable {

    private byte value;
    private long count;
    private HuffmanTree right;
    private HuffmanTree left;

    public HuffmanTree() {
        this((byte) 0);
    }

    public HuffmanTree(byte value) {
        this.value = value;
        this.count = 0;
        this.right = null;
        this.left = null;
    }

    public HuffmanTree(long count, HuffmanTree left, HuffmanTree right) {
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public long getCount() {
        return count;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public HuffmanTree getRight() {
        return right;
    }

    public HuffmanTree getLeft() {
        return left;
    }

    public void incrementCount() {
        count++;
    }

    public void setRight(HuffmanTree right) {
        this.right = right;
    }

    public void setLeft(HuffmanTree left) {
        this.left = left;
    }

    public boolean isLeafNode() {
        // tree is either full or empty, so only checking one child
        return left == null;
    }
}
