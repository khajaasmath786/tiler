package xyz;

import com.esri.core.geometry.*;
import org.junit.Test;
import xyz.tms.TmsTile;
import xyz.tms.TmsTileCalculator;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class TileCalculatorTest {

    private TmsTileCalculator tileCalculator = new TmsTileCalculator();

    @Test
    public void testMadagascar() {

        MapGeometry madagascar = TestResources.getMadagascar();

        Envelope2D env = new Envelope2D();
        madagascar.getGeometry().queryEnvelope2D(env);

        double xmin = env.xmin;
        double ymin = env.ymin;

        TmsTile tmsTile = TmsTileCalculator.getTile(xmin, ymin, 6);

        List<TmsTile> tmsTiles = TmsTileCalculator.tilesForEnvelope(env, 6);
        for (TmsTile t : tmsTiles) {

            Polygon p = t.getEnvelopeAsPolygon();
            System.out.println("p = " + p);

            String execute = OperatorExportToGeoJson.local().execute(0, madagascar.getSpatialReference(), p);
            System.out.println("execute = " + execute);

        }
    }

}
