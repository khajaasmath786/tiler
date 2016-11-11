package xyz;

import com.esri.core.geometry.Envelope2D;
import com.esri.core.geometry.Polygon;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class TileCalculator {

    public static class Tile {

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
        //        @Override
//        public boolean equals(Object obj) {
//        }

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

            double x = getX(this.x);
            double x1 = getX(this.x + 1);

            return w / (x1 - x);
        }

        /**
         * Todo: temporary tile id. boxed on purpose.
         * @return
         */
        public Integer[] getId() {
            return new Integer[]{x, y, z};
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

    public static byte[] encodeTile(Tile tile) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);
        buffer.putInt(tile.x);
        buffer.putInt(tile.y);
        buffer.putInt(tile.z);
        return buffer.array();
    }

    public static Tile decodeTile(byte[] encodedTile) {
        ByteBuffer buffer = ByteBuffer.wrap(encodedTile);
        return new Tile(buffer.getInt(), buffer.getInt(), buffer.getInt());
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
