package xyz;

import com.esri.core.geometry.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static xyz.TestUtil.writeDebugTile;

/**
 * Created by willtemperley@gmail.com on 25-Oct-16.
 */
public class RasterizeTest {

    @Test
    public void test2() throws IOException {

        MapGeometry madagascar = TestResources.getMadagascar();
        Polygon geometry = (Polygon) madagascar.getGeometry();

        TileCalculator.Tile tile = new TileCalculator.Tile(79, 23, 6);

        PolygonRasterizer polygonRasterizer = new PolygonRasterizer();

        byte[] bytes = polygonRasterizer.rasterizeTile(geometry, tile);

        writeDebugTile(tile, bytes);
    }

    @Test
    public void test() throws IOException {
        MapGeometry madagascar = TestResources.getMadagascar();
        Polygon geometry = (Polygon) madagascar.getGeometry();

        TileCalculator tileCalculator = new TileCalculator();
        Envelope2D env = new Envelope2D();
        geometry.queryEnvelope2D(env);
        List<TileCalculator.Tile> tiles = tileCalculator.tilesForEnvelope(env, 6);

        OperatorIntersects intersectOp = OperatorIntersects.local();

        for (TileCalculator.Tile tile : tiles) {

            Envelope2D envelope2D = tile.getEnvelope();
//            geometry.queryEnvelope2D(envelope2D);

            boolean intersects = intersectOp.execute(tile.getEnvelopeAsPolygon(), geometry, madagascar.getSpatialReference(), null);

            if (intersects) {
//                System.out.println("intersects = " + intersects);

//                Transformation2D trans = new Transformation2D();
////                trans.scale(tile.getPixelSize(), tile.getPixelSize());
//                double xx = -envelope2D.xmin;
//                double yy = -envelope2D.ymin;
//                trans.shift(xx, yy);
//                double scale = 1 / tile.getScale();
//                System.out.println("scale = " + scale);
//                trans.scale(scale, scale);

                PolygonRasterizer polygonRasterizer = new PolygonRasterizer();

                byte[] bytes = polygonRasterizer.rasterizeTile(geometry, tile);

                if (bytes != null) {

//                    System.out.println("tile.toString() = " + tile.toString());
                    writeDebugTile(tile, bytes);
                }


            }
        }
//        rasterizedGeometry.rasterizePoly();

    }


}
