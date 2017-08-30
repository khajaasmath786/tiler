package xyz;

import com.esri.core.geometry.*;

/**
 * Created by willtemperley@gmail.com on 21-Mar-17.
 */
public class GeometryUtils {

    /**
     * Create the Polygon equivalent of an envelope
     *
     * @param env the envelope
     * @return the poly
     */
    public static Polygon envToPoly(Envelope2D env) {

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
