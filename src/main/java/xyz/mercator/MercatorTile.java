package xyz.mercator;

import xyz.Tile;

/**
 *
 * Created by willtemperley@gmail.com on 02-Mar-17.
 */
public class MercatorTile implements Tile {

    protected int x;
    protected int y;
    protected int z;

    public MercatorTile() { }

    public MercatorTile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", getX(), getY(), getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MercatorTile)) return false;

        MercatorTile that = (MercatorTile) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
