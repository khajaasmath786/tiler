package xyz;

/**
 * Created by willtemperley@gmail.com on 14-Mar-17.
 */
public interface TileKey<T extends Tile>  {

    void setTile(T tile);

    void getTile(T tile);

}
