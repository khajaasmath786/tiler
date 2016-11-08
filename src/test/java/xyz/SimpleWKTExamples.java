package xyz;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorImportFromWkt;

/**
 * Created by willtemperley@gmail.com on 19-Oct-16.
 */
public class SimpleWKTExamples {

    public static final String Poly = "POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))";
    public static final String PolyWithHole = "POLYGON((35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30))";

    public static final String MultiPolygon = "MULTIPOLYGON(((30 20, 45 40, 10 40, 30 20)), ((15 5, 180 10, 10 20, 5 10, 15 5)))";

    public static Geometry getGeom(String wkt) {

        OperatorImportFromWkt importFromWkt = OperatorImportFromWkt.local();
        return importFromWkt.execute(0, Geometry.Type.Polygon, wkt, null);
    }

}
