package fi.yussiv.squash;

import static fi.yussiv.squash.io.FileIO.*;
import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.io.HuffmanFile;
import fi.yussiv.squash.io.HuffmanParser;
import fi.yussiv.squash.io.HuffmanWrapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import fi.yussiv.squash.ui.GUI;
import static fi.yussiv.squash.ui.GUI.run;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Let's squash some files, shall we?");
        run(new GUI(), 600, 250);
//        encodeHuff();
//        encodeLZW();
        //timeTest();
    }

    public static void timeTest() throws IOException {
        String filename = "test.txt";
        byte[] input = readBytesFromFile(filename);

        runAndPrintResult(input, 1024);
        runAndPrintResult(input, 4096);
        runAndPrintResult(input, 16384);
        runAndPrintResult(input, 65536);
        runAndPrintResult(input, 262144);
        runAndPrintResult(input, 1048576);

        runAndPrintResult(truncateBytes(input, 1024), 65536);
        runAndPrintResult(truncateBytes(input, 2048), 65536);
        runAndPrintResult(truncateBytes(input, 4096), 65536);
        runAndPrintResult(truncateBytes(input, 8192), 65536);
        runAndPrintResult(truncateBytes(input, 16384), 65536);
        runAndPrintResult(truncateBytes(input, 32768), 65536);
        runAndPrintResult(truncateBytes(input, 65536), 65536);
        runAndPrintResult(truncateBytes(input, 131072), 65536);
        runAndPrintResult(truncateBytes(input, 262144), 65536);
        runAndPrintResult(truncateBytes(input, 524288), 65536);
        runAndPrintResult(truncateBytes(input, 1048576), 65536);
    }

    public static byte[] truncateBytes(byte[] bytes, int length) {
        byte[] out = new byte[length];
        for (int i = 0; i < length; i++) {
            out[i] = bytes[i];
        }
        return out;
    }

    public static void runAndPrintResult(byte[] input, int dictionarySize) {

        long start = System.nanoTime();
        byte[] encoded = LZW.encode(input, dictionarySize);
        LZW.decode(encoded, dictionarySize);
        long end = System.nanoTime();
        System.out.println("Dictionary size:" + dictionarySize + "\tinput size:" + input.length + "  \tencoded size:" + encoded.length + "\ttime:" + (end - start) / 1000 + " us");
    }

    public static void encodeLZW() throws IOException {
        String filename = "test.txt";
        System.out.println("--LZW--");

        byte[] inputLZW = readBytesFromFile(filename);
        System.out.println("Input size: " + inputLZW.length + " bytes");

        byte[] encodedLZW = LZW.encode(inputLZW);
        System.out.println("LZW encoded size: " + encodedLZW.length + " bytes");

        writeBytesToFile("out/encoded.lzw", encodedLZW);
        System.out.println(">> Saved to out/encoded.lzw");

        System.out.println("\n<< Reading from out/encoded.lzw");
        byte[] readLZW = readBytesFromFile("out/encoded.lzw");

        byte[] decodedLZW = LZW.decode(readLZW);
        System.out.println("Decoded size: " + decodedLZW.length + " bytes");

        writeBytesToFile("out/decoded.lzw.txt", decodedLZW);
        System.out.println(">> Saved to out/decoded.lzw.txt");
    }

    public static void encodeHuff() throws Exception {
        String filename = "test.txt";
        System.out.println("");
        System.out.println("--Huffman--");

        byte[] input = readBytesFromFile(filename);
        System.out.println("Input size: " + input.length + " bytes");

        HuffmanTree tree = Huffman.generateParseTree(input);
        byte[] encoded = Huffman.encode(input, tree);
        System.out.println("Encoded size: " + encoded.length + " bytes");

        // save file
        byte[] fileBytes = HuffmanFile.getBytes(tree, encoded);
        System.out.println("filebytes: tree=" + (fileBytes.length - encoded.length) + " data=" + encoded.length);
        writeBytesToFile("out/encoded.huff", fileBytes);
        System.out.println(">> Saved to out/encoded.huff");

        System.out.println("\n<< Reading from out/encoded.huff");
        byte[] encodedFile = readBytesFromFile("out/encoded.huff");
        
        HuffmanParser parser = new HuffmanParser(encodedFile);
        byte[] decoded = Huffman.decode(parser.getData(), parser.getHuffmanTree());
        System.out.println("Decoded size: " + decoded.length + " bytes");

        writeBytesToFile("out/decoded.huff.txt", decoded);
        System.out.println(">> Saved to out/decoded.huff.txt");

    }

}
