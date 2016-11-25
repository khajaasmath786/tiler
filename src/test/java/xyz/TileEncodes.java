package xyz;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by willtemperley@gmail.com on 21-Nov-16.
 */
public class TileEncodes {

    @Test
    public void testSerDe() {

        TileCalculator.Tile tile = new TileCalculator.Tile(2,9,3);
        System.out.println("tile = " + tile);

        byte[] encoded = tile.encode();

        TileCalculator.Tile decoded = new TileCalculator.Tile(encoded);

        System.out.println("decoded = " + decoded);
        assertEquals(tile, decoded);
        assertEquals(tile.getEnvelope(), decoded.getEnvelope());

    }

}
