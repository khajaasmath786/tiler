package xyz;

import com.esri.core.geometry.*;
//import com.esri.core.geometry.examples.ShapefileGeometryCursor;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by willtemperley@gmail.com on 25-Oct-16.
 */
public class TileRasterizeTest {

    private SpatialReference spatialRef = SpatialReference.create(4326);

    /**
     * It looks pretty dumb in the test, but purpose is to show multiple binary images being composited
     * @throws IOException
     */
//    @Test
//    public void testBitsetCompositor() throws IOException {

//        ShapefileGeometryCursor shapeFileReader = getShapefileGeometryCursor();
//        Envelope2D envelope2D = shapeFileReader.getEnvelope2D();
//        List<Geometry> lines = getGeometries(shapeFileReader);
//        List<TileCalculator.Tile> tiles = TileCalculator.tilesForEnvelope(envelope2D, 9);
//
//        double bufferDistance = 0.005;
//
//
//        for (TileCalculator.Tile tile : tiles) {
//            BitsetCompositor bitsetCompositor = new BitsetCompositor(256*256); //THING BEING TESTED
//
//            for (Geometry line : lines) {
//                TileRasterizer tileRasterizer = new TileRasterizer(tile, new BinaryScanCallback(256, 256));
//                Polygon outputGeom = (Polygon) OperatorBuffer.local().execute(line, spatialRef, bufferDistance, null);
//                tileRasterizer.rasterizePolygon(outputGeom);
//                byte[] bitset = tileRasterizer.getBitset();
//                bitsetCompositor.or(bitset);
//            }
//
//            BitSet bitset = bitsetCompositor.getBitset();
//
//            byte[] image = BinaryImage.getImage(bitset, 256, 256);
//            TestUtil.writeDebugTile(tile, image);
//        }
//    }
//
//
//    @Test
//    public void testCanary() throws IOException {
//
//        ShapefileGeometryCursor shapeFileReader = getShapefileGeometryCursor();
//        Envelope2D envelope2D = shapeFileReader.getEnvelope2D();
//        List<Geometry> lines = getGeometries(shapeFileReader);
//        List<TileCalculator.Tile> tiles = TileCalculator.tilesForEnvelope(envelope2D, 9);
//
//        double bufferDistance = 0.005;
//        for (TileCalculator.Tile tile : tiles) {
//
////            TileRasterizer tileRasterizer = new TileRasterizer(tile, new IntbackedScanCallback(256, 256));
//            TileRasterizer tileRasterizer = new TileRasterizer(tile, new BinaryScanCallback(256, 256));
//
//            for (Geometry line : lines) {
//                Polygon outputGeom = (Polygon) OperatorBuffer.local().execute(line, spatialRef, bufferDistance, null);
//                tileRasterizer.rasterizePolygon(outputGeom);
//            }
//
//            byte[] image = tileRasterizer.getImage();
//            TestUtil.writeDebugTile(tile, image);
//        }
//    }
//
//    private List<Geometry> getGeometries(ShapefileGeometryCursor shapeFileReader) {
//        Geometry geom;
//        List<Geometry> lines = new ArrayList<>();
//        while ((geom = shapeFileReader.next()) != null) {
//            lines.add(geom);
//        }
//        return lines;
//    }
//
//
//    private ShapefileGeometryCursor getShapefileGeometryCursor() throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/canary.shp"));
//        return new ShapefileGeometryCursor(fileInputStream);
//    }
//
//    @Test
//    public void test() throws IOException {
//        MapGeometry madagascar = TestResources.getMadagascar();
//        Polygon geometry = (Polygon) madagascar.getGeometry();
//
//        Envelope2D env = new Envelope2D();
//        geometry.queryEnvelope2D(env);
//        List<TileCalculator.Tile> tiles = TileCalculator.tilesForEnvelope(env, 6);
//
//        OperatorIntersects intersectOp = OperatorIntersects.local();
//
//        for (TileCalculator.Tile tile : tiles) {
//
//            Envelope2D envelope2D = tile.getEnvelope();
////            geometry.queryEnvelope2D(envelope2D);
//
//            boolean intersects = intersectOp.execute(tile.getEnvelopeAsPolygon(), geometry, madagascar.getSpatialReference(), null);
//
//            if (intersects) {
//
//                PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
//
//                byte[] bytes = polygonRasterizer.rasterizeTile(geometry, tile);
//
//                if (bytes != null) {
//                    TestUtil.writeDebugTile(tile, bytes);
//                }
//
//
//            }
//        }
//
//    }


}
