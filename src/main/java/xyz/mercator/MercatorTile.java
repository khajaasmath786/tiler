package xyz.mercator;
/**
 * Created by willtemperley@gmail.com on 02-Mar-17.
 */
public class MercatorTile {

    public int x;
    public int y;
    public int z;

    public MercatorTile() {

    }

    public MercatorTile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", x, y, z);
    }
}
