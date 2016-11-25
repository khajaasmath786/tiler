package com.esri.core.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 */
public class IntbackedScanCallback implements TileScanCallback {

    private final int width;
    private final int height;
    private final int[] matrix;
    private int burnValue = getPackedRGBA(128, 128, 128, 128); //grey

    public void setBurnValue(int burnValue) {
        this.burnValue = burnValue;
    }

    public IntbackedScanCallback(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new int[width * height];
        int background = getPackedRGBA(128, 0, 0, 0); //black
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = background;
        }
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
                matrix[offset + scanLineX] = burnValue;
            }
        }
    }

    private int getPackedRGBA(int a, int r, int g, int b) {
        return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255);
    }

    public byte[] getImage() {
        return BinaryImage.getImage(matrix, width, height);
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
        throw new NotImplementedException();
    }
}
