package xyz;

import com.esri.core.geometry.Envelope2D;
import com.esri.core.geometry.Polygon;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Tile which knows how to encode itself with HBase-friendly keys
 *
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class TileCalculator {

    public static class Tile {

        public static byte[] cf = "d".getBytes();
        public static byte[] cimg = "i".getBytes();

        private static final int w = 256;
        private static final int h = 256;

        private final int x;
        private final int y;
        private final int z;

        public Tile(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        final int readLittleEndianInt(byte[] bytes) {
            int ch4 = bytes[0];
            int ch3 = bytes[1];
            int ch2 = bytes[2];
            int ch1 = bytes[3];
            return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
        }

        public Tile(byte[] encodedTile) {
            ByteBuffer buffer = ByteBuffer.wrap(encodedTile);

            byte[] dst = new byte[4];
            buffer.get(dst);
            this.x = readLittleEndianInt(dst);
            this.y = buffer.getInt();
            this.z = buffer.getInt();

        }

        public byte[] encode() {

            ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);

            //reverse order of first four bytes, as the x coordinate is stored in little endian form
            buffer.put((byte)(x));
            buffer.put((byte)(x >>>  8));
            buffer.put((byte)(x >>> 16));
            buffer.put((byte)(x >>> 24));

            buffer.putInt(this.y);
            buffer.putInt(this.z);
            return buffer.array();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Tile)) {
                return false;
            }
            Tile tile = (Tile) o;
            return x == tile.x && y == tile.y && z == tile.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }

        public Envelope2D getEnvelope() {
            Envelope2D envelope2D = new Envelope2D();
            envelope2D.setCoords(getX(x), getY(y), getX(x+1), getY(y+1));
            return envelope2D;
        }

        public Polygon getEnvelopeAsPolygon() {

            Polygon p = new Polygon();
            double x = getX(this.x);
            double y = getY(this.y);
            double x1 = getX(this.x + 1);
            double y1 = getY(this.y + 1);

            p.startPath(x, y);
            p.lineTo(x1, y);
            p.lineTo(x1, y1);
            p.lineTo(x, y1);
            p.closePathWithLine();
            return p;
        }

        public double getScale() {

            double x0 = getX(this.x);
            double x1 = getX(this.x + 1);
            return w / (x1 - x0);
        }

        @Override
        public String toString() {
            return "xyz-" + x + "-" + y + "-" + z;
        }

        /**
         * @param x  x coord for tile at zoom
         * @return longitude
         */
        private double getX(int x) {
            return  (360D / (2 << z)) * x - 180;
        }

        /**
         * @param y  y coord for tile at zoom
         * @return latitude
         */
        private double getY(int y) {
            return  (180D / (1 << z)) * y - 90;
        }
    }


    public static Tile getTile(Double lon, Double lat, int zoom) {
        /*
         * Looking for the first tile from left
         */
        int nX = 2 << zoom; //tiles for x at zoom
        int nY = 1 << zoom; //tiles for x at zoom

        double step = 360D / nX; //tile width
        int Xt = (int) Math.floor((lon + 180) / step);
        int Yt = (int) Math.floor((lat +  90) / step);

        return new Tile(Xt, Yt, zoom);
    }


    public static List<Tile> tilesForEnvelope(Envelope2D env, int zoom) {

        double xmin = env.xmin;
        double ymin = env.ymin;
        double xmax = env.xmax;
        double ymax = env.ymax;

        Tile ll = getTile(xmin, ymin, zoom);
        Tile ur = getTile(xmax, ymax, zoom);

        /*
        Could put tiles in array or provide zipped vectors
         */
        List<Tile> tiles = new ArrayList<>();


        //iterate ll to ur
        for (int i = ll.x; i <= ur.x; i++) {
            for (int j = ll.y; j <= ur.y; j++) {
                tiles.add(new Tile(i, j, zoom));
            }
        }
//        Tile[] tiles = new Tile[];
        return tiles;
    }
}
