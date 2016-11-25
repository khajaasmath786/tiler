package com.esri.core.geometry;


import java.util.BitSet;

/**
 *
 */
public class BinaryScanCallback implements TileScanCallback {

    private final int width;
    private final int height;
    private final BitSet bitSet;

    public BinaryScanCallback(int width, int height) {
        this.width = width;
        this.height = height;
        bitSet = new BitSet(width * height);
    }

    @Override
    public void drawScan(int[] scans, int scanCount) {

        /*
        Scanlines come in triplets - x1, x2 and the y coord
        Need to  burn values between x1 and x2 on the inverted y coordinate
         */
        for (int i = 0; i < scans.length; i+=3) {
            int y = height - scans[i+2] - 1;// image coordinates
            int offset = y * width;

            for (int scanLineX = scans[i]; scanLineX < scans[i+1]; scanLineX++) {
                int idx = offset + scanLineX;
                bitSet.set(idx);
            }
        }

    }

//    public byte[] getBitSetArray() {
//        return bitSet.toByteArray();
//    }

    @Override
    public byte[] getImage() {
        return BinaryImage.getImage(bitSet, width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public byte[] getBitSet() {
        return bitSet.toByteArray();
    }
}
