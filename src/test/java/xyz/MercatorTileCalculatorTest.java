package xyz;

import com.esri.core.geometry.*;
import org.junit.Test;
import xyz.mercator.MercatorTile;
import xyz.mercator.MercatorTileCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class MercatorTileCalculatorTest {


    @Test
    public void testMadagascar() throws FileNotFoundException {

        MapGeometry madagascar = TestResources.getMadagascar();

        Envelope2D env = new Envelope2D();
        madagascar.getGeometry().queryEnvelope2D(env);


        PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("E:/tmp/x.json")));

        printWriter.write("{\n");
        printWriter.write("    \"type\": \"FeatureCollection\",\n");
        printWriter.write("    \"features\": [\n");


        MercatorTileCalculator tileCalculator = new MercatorTileCalculator();
        List<MercatorTile> mercatorTiles = tileCalculator.tilesForEnvelope(env, 13);
        for (int i = 0; i < mercatorTiles.size(); i++) {
            MercatorTile t = mercatorTiles.get(i);

            printWriter.write("        {\n");  //begin feature
            printWriter.write("        \"geometry\":\n");

//            Polygon p = t.getEnvelopeAsPolygon();
            Envelope2D env2d = tileCalculator.getTileEnvelope(t);
            Polygon p = envToPoly(env2d);

            String execute = OperatorExportToGeoJson.local().execute(0, madagascar.getSpatialReference(), p);

            printWriter.write(execute);

            printWriter.write(",\n");
            printWriter.write("\"type\": \"Feature\",\n");

//            printWriter.write("        \"id\": " + i + ",\n");
            printWriter.write("        \"properties\": {\n");
            printWriter.write("            \"zoom\": \"" + t.getZ() + "\"\n");
            printWriter.write("          }\n");
            printWriter.write("        }\n");
            if (i < mercatorTiles.size() - 1) {
                printWriter.write("        ,\n");
            }
        }

        printWriter.write("     ]\n");
        printWriter.write("}\n");

        printWriter.close();
    }

    private Polygon envToPoly(Envelope2D env) {

        Polygon p = new Polygon();
        Point2D lowerLeft = env.getLowerLeft();
        Point2D upperRight = env.getUpperRight();
        double x = lowerLeft.x;
        double y = lowerLeft.y;
        double x1 = upperRight.x;
        double y1 = upperRight.y;

        p.startPath(x, y);
        p.lineTo(x1, y);
        p.lineTo(x1, y1);
        p.lineTo(x, y1);
        p.closePathWithLine();
        return p;
    }

}
