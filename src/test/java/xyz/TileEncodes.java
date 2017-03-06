package xyz;

import org.junit.Test;
import xyz.tms.TmsTile;

import static org.junit.Assert.assertEquals;

/**
 * Created by willtemperley@gmail.com on 21-Nov-16.
 */
public class TileEncodes {

    @Test
    public void testSerDe() {

        TmsTile tmsTile = new TmsTile(2,9,3);
        System.out.println("tmsTile = " + tmsTile);

        byte[] encoded = tmsTile.encode();

        TmsTile decoded = new TmsTile(encoded);

        System.out.println("decoded = " + decoded);
        assertEquals(tmsTile, decoded);
        assertEquals(tmsTile.getEnvelope(), decoded.getEnvelope());

    }

}
