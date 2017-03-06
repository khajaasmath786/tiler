package xyz.tms;

import com.esri.core.geometry.Envelope2D;
import com.esri.core.geometry.Polygon;

import java.nio.ByteBuffer;

/**
 * Created by willtemperley@gmail.com on 19-Jan-17.
 */
public class TmsTile {

    public static byte[] cf = "d".getBytes();
    public static byte[] cimg = "i".getBytes();

    private static final int w = 256;
    private static final int h = 256;

    private final int x;
    private final int y;
    private int z;

    public TmsTile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private int readLittleEndianInt(byte[] bytes) {
        int ch4 = bytes[0];
        int ch3 = bytes[1];
        int ch2 = bytes[2];
        int ch1 = bytes[3];
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
    }

    public TmsTile(byte[] encodedTile) {
        ByteBuffer buffer = ByteBuffer.wrap(encodedTile);

        byte[] dst = new byte[4];
        buffer.get(dst);
        this.x = readLittleEndianInt(dst);
        this.y = buffer.getInt();
        if (buffer.hasRemaining()) {
            this.z = buffer.getInt();
        }
    }

    public byte[] encode() {

        ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);

        //reverse order of first four bytes, as the x coordinate is stored in little endian form
        buffer.put((byte) (getX()));
        buffer.put((byte) (getX() >>> 8));
        buffer.put((byte) (getX() >>> 16));
        buffer.put((byte) (getX() >>> 24));

        buffer.putInt(this.getY());
        buffer.putInt(this.z);
        return buffer.array();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TmsTile)) {
            return false;
        }
        TmsTile tmsTile = (TmsTile) o;
        return getX() == tmsTile.getX() && getY() == tmsTile.getY() && z == tmsTile.z;
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + z;
        return result;
    }

    public Envelope2D getEnvelope() {
        Envelope2D envelope2D = new Envelope2D();
        envelope2D.setCoords(getX(getX()), getY(getY()), getX(getX() + 1), getY(getY() + 1));
        return envelope2D;
    }

    public Polygon getEnvelopeAsPolygon() {

        Polygon p = new Polygon();
        double x = getX(this.getX());
        double y = getY(this.getY());
        double x1 = getX(this.getX() + 1);
        double y1 = getY(this.getY() + 1);

        p.startPath(x, y);
        p.lineTo(x1, y);
        p.lineTo(x1, y1);
        p.lineTo(x, y1);
        p.closePathWithLine();
        return p;
    }

    public double getScale() {

        double x0 = getX(this.getX());
        double x1 = getX(this.getX() + 1);
        return w / (x1 - x0);
    }

    @Override
    public String toString() {
        return "xyz-" + getX() + "-" + getY() + "-" + z;
    }

    /**
     * @param x x coord for tile at zoom
     * @return longitude
     */
    private double getX(int x) {
        return (360D / (2 << z)) * x - 180;
    }

    /**
     * @param y y coord for tile at zoom
     * @return latitude
     */
    private double getY(int y) {
        return (180D / (1 << z)) * y - 90;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
