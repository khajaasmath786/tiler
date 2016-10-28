package xyz;

import com.esri.core.geometry.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class TileCalculatorTest {

    TileCalculator tileCalculator = new TileCalculator();
    final int zoom6 = 6;

//    OperatorExportToGeoJson geoJson = OperatorExportToGeoJson.fromGeoJson();
    OperatorFactoryLocal factory = OperatorFactoryLocal.getInstance();
    OperatorExportToGeoJson geoJson = OperatorExportToGeoJson.local();
//    OperatorExportToWkt wkt = (OperatorExportToWkt) factory.getOperator(Operator.Type.ExportToWkt);

    @Test
    public void testMadagascar() {

        MapGeometry madagascar = TestResources.getMadagascar();
        System.out.println("madagascar = " + madagascar);

        Envelope2D env = new Envelope2D();
        madagascar.getGeometry().queryEnvelope2D(env);


        double xmin = env.xmin;
        double ymin = env.ymin;

        TileCalculator.Tile tile = tileCalculator.getTile(xmin, ymin, zoom6);

        System.out.println("tile = " + tile);

        List<TileCalculator.Tile> tiles = tileCalculator.tilesForEnvelope(env, zoom6);
        for (TileCalculator.Tile t : tiles) {

            Polygon p = t.getEnvelopeAsPolygon();
            System.out.println("p = " + p);

            String execute = geoJson.execute(0, madagascar.getSpatialReference(), p);
            System.out.println("execute = " + execute);

//            String envJson = exporter.execute(t.getEnvelopeAsPolygon());
//            factory.
//            System.out.println(envJson);
            OperatorExportToESRIShape.local();


        }

    }


}
