package xyz;

import com.esri.core.geometry.*;
import org.junit.Test;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by willtemperley@gmail.com on 25-Oct-16.
 */
public class TileRasterizeTest {

    TileCalculator tileCalculator = new TileCalculator();
    SpatialReference spatialRef = SpatialReference.create(4326);

    @Test
    public void testCanary() throws IOException {


        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/canary.shp"));
        ShapeFileReader shapeFileReader = new ShapeFileReader(fileInputStream);
        Envelope2D envelope2D = shapeFileReader.getEnvelope2D();

        List<TileCalculator.Tile> tiles = tileCalculator.tilesForEnvelope(envelope2D, 9);

        double bufferDistance = 0.005;
        for (TileCalculator.Tile tile : tiles) {

            TileRasterizer tileRasterizer = new TileRasterizer(tile);

            FileInputStream fileInputStream2 = new FileInputStream(new File("src/test/resources/canary.shp"));
            ShapeFileReader shapeFileReader2 = new ShapeFileReader(fileInputStream2);

            while (shapeFileReader2.hasNext()) {

                Polyline polyLine = (Polyline) shapeFileReader2.next();

                Polygon outputGeom = (Polygon) OperatorBuffer.local().execute(polyLine, spatialRef, bufferDistance, null);

                tileRasterizer.rasterizePolygon(outputGeom);

            }
            byte[] image = tileRasterizer.getImage();

            TestUtil.writeDebugTile(tile, image);
//            FileOutputStream fileOutputStream

        }

    }

    @Test
    public void testInvalidLine() {


        Polyline inputGeom = createTestLine6dp();
//        Polyline inputGeom = createTestLoop();
        Geometry outputGeom = OperatorBuffer.local().execute(inputGeom, spatialRef, 0.005, null);
        System.out.println("outputGeom = " + outputGeom);

        TestUtil.printGeoJson(inputGeom);
        TestUtil.printGeoJson(outputGeom);

    }

    public Polyline createTestLoop() {

        Polyline inputGeom = new Polyline();
        inputGeom.startPath( -17.76310622692108, 28.60826968649024 );
        inputGeom.lineTo( -17.76243567466736, 28.607181787045608);
        inputGeom.lineTo(-17.762076258659363, 28.605801881066814);
        inputGeom.lineTo(-17.76285946369171, 28.60567001196991);
        inputGeom.lineTo(-17.763272523880005, 28.606979276381438);
        inputGeom.lineTo( -17.76243567466736, 28.607181787045608);
        return inputGeom;
    }


    public Polyline createTestLine5dp() {
        Polyline inputGeom = new Polyline();
        inputGeom.startPath(-17.76328, 28.60824);
        inputGeom.lineTo(-17.76309, 28.60818);
        inputGeom.lineTo(-17.76295, 28.60807);
        inputGeom.lineTo(-17.76282, 28.60785);
        inputGeom.lineTo(-17.76266, 28.60747);
        inputGeom.lineTo(-17.76261, 28.60737);
        inputGeom.lineTo(-17.76255, 28.60714);
        inputGeom.lineTo(-17.76241, 28.60666);
        inputGeom.lineTo(-17.76225, 28.60616);
        inputGeom.lineTo(-17.7622, 28.60592);
        inputGeom.lineTo(-17.7622, 28.60575);
        inputGeom.lineTo(-17.76228, 28.6057);
        inputGeom.lineTo(-17.76275, 28.60573);
        inputGeom.lineTo(-17.76282, 28.60586);
        inputGeom.lineTo(-17.76311, 28.60689);
        inputGeom.lineTo(-17.76311, 28.60698);
        inputGeom.lineTo(-17.76301, 28.60703);
        inputGeom.lineTo(-17.76255, 28.60714);
        return inputGeom;
    }

    public Polyline createTestLine6dp() {
        Polyline inputGeom = new Polyline();
        inputGeom.startPath(-17.763283,28.608236);
        inputGeom.lineTo(-17.763085,28.608177);
        inputGeom.lineTo(-17.762951,28.608074);
        inputGeom.lineTo(-17.762816,28.607846);
        inputGeom.lineTo(-17.762658,28.607473);
        inputGeom.lineTo(-17.762614,28.607368);
        inputGeom.lineTo(-17.762548,28.607135);
        inputGeom.lineTo(-17.762414,28.606655);
        inputGeom.lineTo(-17.762252,28.606163);
        inputGeom.lineTo(-17.762204,28.605922);
        inputGeom.lineTo(-17.762197,28.605747);
        inputGeom.lineTo(-17.762283,28.605695);
        inputGeom.lineTo(-17.762748,28.60573);
        inputGeom.lineTo(-17.76282,28.60586);
        inputGeom.lineTo(-17.763106,28.60689);
        inputGeom.lineTo(-17.763113,28.606979);
        inputGeom.lineTo(-17.763006,28.607034);
        inputGeom.lineTo(-17.762548,28.607135);
        return inputGeom;
    }

    public Polyline createTestLine4dp() {
        Polyline inputGeom = new Polyline();
        inputGeom.startPath(-17.7633,28.6082);
        inputGeom.lineTo(-17.7631,28.6082);
        inputGeom.lineTo(-17.763,28.6081);
        inputGeom.lineTo(-17.7628,28.6078);
        inputGeom.lineTo(-17.7627,28.6075);
        inputGeom.lineTo(-17.7626,28.6074);
        inputGeom.lineTo(-17.7625,28.6071);
        inputGeom.lineTo(-17.7624,28.6067);
        inputGeom.lineTo(-17.7623,28.6062);
        inputGeom.lineTo(-17.7622,28.6059);
        inputGeom.lineTo(-17.7622,28.6057);
        inputGeom.lineTo(-17.7623,28.6057);
        inputGeom.lineTo(-17.7627,28.6057);
        inputGeom.lineTo(-17.7628,28.6059);
        inputGeom.lineTo(-17.7631,28.6069);
        inputGeom.lineTo(-17.7631,28.607);
        inputGeom.lineTo(-17.763,28.607);
        inputGeom.lineTo(-17.7625,28.6071);

        return inputGeom;
    }

    //fails
    public Polyline createTestLineFull() {
        Polyline inputGeom = new Polyline();
        inputGeom.startPath(-17.76328329, 28.608236300265105);
        inputGeom.lineTo(-17.76308500, 28.60817690004393);
        inputGeom.lineTo(-17.76295079, 28.608073700141176);
        inputGeom.lineTo(-17.762816499, 28.607846499916093);
        inputGeom.lineTo(-17.762658200, 28.607473399977494);
        inputGeom.lineTo(-17.762613500, 28.607368000332997);
        inputGeom.lineTo(-17.762548200, 28.607135299854235);
        inputGeom.lineTo(-17.762413800, 28.606655499851513);
        inputGeom.lineTo(-17.7622519997, 28.606163299996354);
        inputGeom.lineTo(-17.7622037996656, 28.605922299674432);
        inputGeom.lineTo(-17.762196999891614, 28.605746799675103);
        inputGeom.lineTo(-17.762283000260197, 28.60569510034867);
        inputGeom.lineTo(-17.762747699746512, 28.605729500316215);
        inputGeom.lineTo(-17.76281999984309, 28.605860300412587);
        inputGeom.lineTo(-17.763105700067968, 28.606889599675355);
        inputGeom.lineTo(-17.76311259966667, 28.606979100205365);
        inputGeom.lineTo(-17.763005899602547, 28.607034100043734);
        inputGeom.lineTo(-17.762548200439028, 28.607135299854235);
        return inputGeom;

    }

    @Test
    public void test() throws IOException {
        MapGeometry madagascar = TestResources.getMadagascar();
        Polygon geometry = (Polygon) madagascar.getGeometry();

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

    private void writeDebugTile(TileCalculator.Tile tile, byte[] bytes) throws IOException {
        File f = new File("e:/tmp/ras/" + tile.toString() + ".png");
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        for (byte aByte : bytes) {
            fileOutputStream.write(aByte);
        }
    }

}
