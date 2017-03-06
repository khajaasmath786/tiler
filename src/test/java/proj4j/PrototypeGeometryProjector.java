package proj4j;

import com.esri.core.geometry.*;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.ProjCoordinate;

/***
 * A very dumb
 *
 * Created by willtemperley@gmail.com on 24-Nov-16.
 */
public class PrototypeGeometryProjector {

    private final CoordinateTransform transform;

    private ProjCoordinate p1 = new ProjCoordinate();
    private ProjCoordinate p2 = new ProjCoordinate();

    PrototypeGeometryProjector(CoordinateTransform transform) {
       this.transform = transform;
    }

    private void makeTransform(Point2D point2D) {
        p1.x = point2D.x;
        p1.y = point2D.y;
        transform.transform(p1, p2);
        point2D.x = p2.x;
        point2D.y = p2.y;
    }

//    CoordinateTransform trans = ctFactory.createTransform(WGS84, crs);
    public Polyline transform(Polyline geom) {

        Polyline polyline = new Polyline();
        Point2D[] coordinates2D = geom.getCoordinates2D();

        for (int i = 0; i < coordinates2D.length; i++) {
            Point2D point2D = coordinates2D[i];
            makeTransform(point2D);
            if (i == 0) {
                polyline.startPath(point2D.x, point2D.y);
            } else {
                polyline.lineTo(point2D.x, point2D.y);
            }
        }

        return polyline;
    }

    public Polygon transform(Polygon geom) {

        Polygon polygon = new Polygon();
        Point2D[] coordinates2D = geom.getCoordinates2D();

        for (int i = 0; i < coordinates2D.length; i++) {
            Point2D point2D = coordinates2D[i];
            makeTransform(point2D);
            if (i == 0) {
                polygon.startPath(point2D.x, point2D.y);
            } else {
                polygon.lineTo(point2D.x, point2D.y);
            }
        }

        polygon.closeAllPaths();
        return polygon;

    }

}
