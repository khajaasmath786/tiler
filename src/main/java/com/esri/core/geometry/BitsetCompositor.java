package com.esri.core.geometry;

import java.util.BitSet;

/**
 * Created by willtemperley@gmail.com on 15-Nov-16.
 */
public class BitsetCompositor {

    private final byte[] bytes;

    public BitsetCompositor(int nPixels) {
        bytes = new byte[nPixels / 8];
    }

    /**
     * Bitwise or all bytes
     * @param newBytes
     */
    public void or(byte[] newBytes) {
        for (int i = 0; i < newBytes.length; i++) {
            bytes[i] |= newBytes [i]; //todo would it be faster to | using 32bit ints?
        }
    }

    /**
     * @return a bitset corresponding to where pixels are set in the image
     */
    public BitSet getBitset() {

        /*
        Unfortunately there's no way to get a bitset back from the binary without testing each bit
         */
        BitSet bitSet = new BitSet(bytes.length * 8);

        for (int i = 0; i < bytes.length; i++) {
            byte aByte = bytes[i];
            /*
            Test each bit in the byte
             */
            for (int position = 0; position < 8; position++) {
                boolean bit = getBit(aByte, position);
                if (bit) bitSet.set((i * 8) + position);
            }
        }

        return bitSet;
    }

    /**
     * Tests a if bit at position pos is set in a byte
     *
     * @param b the byte to test
     * @param pos which position in the byte to test
     * @return is the bit set or not
     */
    private boolean getBit(byte b, int pos) {
        return ((b >> pos) & (byte)1) != 0;
    }
}
