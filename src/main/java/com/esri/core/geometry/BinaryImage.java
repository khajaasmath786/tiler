package com.esri.core.geometry;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.BitSet;

/**
 * Created by willtemperley@gmail.com on 15-Nov-16.
 */
public class BinaryImage {

    private static int getPackedRGBA(int a, int r, int g, int b) {
        return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255);
    }

    public static byte[] getImage(BitSet bitSet, int width, int height) {

        int burnValue = getPackedRGBA(255, 255, 255, 255); //white
        int black = getPackedRGBA(255, 0, 0, 0); //white

        int[] matrix = new int[bitSet.size()];
        for (int i = 0; i < bitSet.size(); i++) {
            boolean b = bitSet.get(i);
            if (b) matrix[i] = burnValue;
            else matrix[i] = black;
        }
        DataBufferInt buffer = new DataBufferInt(matrix, matrix.length);

        int[] bandMasks = {0xFF0000, 0xFF00, 0xFF, 0xFF000000}; // ARGB (yes, ARGB, as the masks are R, G, B, A always) order
        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, bandMasks, null);

        ColorModel cm = ColorModel.getRGBdefault();
        BufferedImage image = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "PNG", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    static byte[] getImage(int[] matrix, int width, int height) {

        DataBufferInt buffer = new DataBufferInt(matrix, matrix.length);

        int[] bandMasks = {0xFF0000, 0xFF00, 0xFF, 0xFF000000}; // ARGB (yes, ARGB, as the masks are R, G, B, A always) order
        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, bandMasks, null);

        ColorModel cm = ColorModel.getRGBdefault();
        BufferedImage image = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "PNG", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
