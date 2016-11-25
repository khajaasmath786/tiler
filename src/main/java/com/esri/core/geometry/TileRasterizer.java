package com.esri.core.geometry;

import xyz.TileCalculator;


/**
 * Package-private members can only be accessed from here
 *
 * Created by willtemperley@gmail.com on 20-Oct-16.
 */
public class TileRasterizer {


    final Transformation2D trans = new Transformation2D();

//    private static int width = 256;
//    private static int height = 256;
    private final TileScanCallback scanCallback;// = new IntbackedScanCallback(width, height);

    public TileRasterizer(TileCalculator.Tile tile, TileScanCallback scanCallback) {
        this.scanCallback = scanCallback;
        trans.setIdentity(); //reset
        Envelope2D envelope2D = tile.getEnvelope();

        double xx = -envelope2D.xmin;
        double yy = -envelope2D.ymin;
        trans.shift(xx, yy);
        double scale = tile.getScale();
        trans.scale(scale, scale);
    }


    /*
    Todo: test!!
    This is a slightly different approach: keep filling up the tile and extract the info when you want it
     */
    public void rasterizePolygon(Polygon polygon) {

        //todo: can this be re-used?
        SimpleRasterizer rasterizer = new SimpleRasterizer();
        Envelope2D env = new Envelope2D();
        polygon.queryEnvelope2D(env);

        rasterizer.setup(scanCallback.getWidth(), scanCallback.getHeight(), scanCallback);

        SegmentIterator segIter = polygon.querySegmentIterator();
        Point2D p1 = new Point2D();
        Point2D p2 = new Point2D();
        while (segIter.nextPath()) {
            while (segIter.hasNextSegment()) {
                Segment seg = segIter.nextSegment();
                if (seg.getType() != Geometry.Type.Line)
                    throw GeometryException.GeometryInternalError(); // TODO:
                trans.transform(seg.getStartXY(), p1);
                trans.transform(seg.getEndXY(), p2);
                rasterizer.addEdge(p1.x, p1.y, p2.x, p2.y);
            }
        }

        rasterizer.renderEdges(SimpleRasterizer.EVEN_ODD);

        rasterizer.flush();

    }

    public byte[] getImage() {
        return scanCallback.getImage();
    }

    public byte[] getBitset() {
        return scanCallback.getBitSet();
    }
}
