package xyz.wgs84;

import com.esri.core.geometry.Envelope2D;

/**
 * Designed for object reuse hence no constructor params
 *
 * Created by willtemperley@gmail.com on 10-Mar-17.
 */
public class TileKey {

    private final double[] pixelScales = new double[2];
    private final double[] origin = new double[2];
    private final int[] dimensions = new int[2];
    private int proj = 0;

    public TileKey() { }

    public Envelope2D getEnvelope2D() {

        double yTop = origin[1];
        double xLeft = origin[0];
        double xRight = xLeft + (pixelScales[0] * dimensions[0]);
        double yBottom = yTop - (pixelScales[1] * dimensions[1]);

        return new Envelope2D(xLeft, yBottom, xRight, yTop);
    }

    public void setDimensions(int x, int y) {
        this.dimensions[0] = x;
        this.dimensions[1] = y;
    }

    public void setOrigin(double x, double y) {
        this.origin[0] = x;
        this.origin[1] = y;
    }

    public void setPixelScales(double x, double y) {
        this.pixelScales[0] = x;
        this.pixelScales[1] = y;
    }

    public double getOriginX() {
        return origin[0];
    }
    public double getOriginY() {
        return origin[1];
    }

    public double getPixelScaleX() {
        return pixelScales[0];
    }

    public double getPixelScaleY() {
        return pixelScales[1];
    }

    public int getWidth() {
        return dimensions[0];
    }

    public int getHeight() {
        return dimensions[1];
    }

    public int getProj() {
        return proj;
    }

    public void setProj(int proj) {
        this.proj = proj;
    }
}
