package xyz;

import com.esri.core.geometry.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class TileCalculatorTest {

    private TileCalculator tileCalculator = new TileCalculator();

    @Test
    public void testMadagascar() {

        MapGeometry madagascar = TestResources.getMadagascar();

        Envelope2D env = new Envelope2D();
        madagascar.getGeometry().queryEnvelope2D(env);

        double xmin = env.xmin;
        double ymin = env.ymin;

        TileCalculator.Tile tile = TileCalculator.getTile(xmin, ymin, 6);

        List<TileCalculator.Tile> tiles = TileCalculator.tilesForEnvelope(env, 6);
        for (TileCalculator.Tile t : tiles) {

            Polygon p = t.getEnvelopeAsPolygon();
            System.out.println("p = " + p);

            String execute = OperatorExportToGeoJson.local().execute(0, madagascar.getSpatialReference(), p);
            System.out.println("execute = " + execute);

        }
    }

}
