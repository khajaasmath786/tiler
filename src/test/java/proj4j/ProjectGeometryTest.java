package proj4j;

import com.esri.core.geometry.*;
import org.junit.Test;
import org.osgeo.proj4j.*;
import xyz.TestResources;

/**
 *
 * Just messing around with the proj4j build
 *
 * Created by willtemperley@gmail.com on 24-Nov-16.
 */
public class ProjectGeometryTest {

    @Test
    public void run() {

        final String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees";

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CRSFactory csFactory = new CRSFactory();
        CoordinateReferenceSystem WGS84 = csFactory.createFromParameters("WGS84", WGS84_PARAM);
        CoordinateReferenceSystem crs = csFactory.createFromName("EPSG:32630");
        CoordinateTransform forward = ctFactory.createTransform(WGS84, crs);
        CoordinateTransform reverse = ctFactory.createTransform(crs, WGS84);

        //fwd and reverse
        PrototypeGeometryProjector polylineProjector = new PrototypeGeometryProjector(forward);
        PrototypeGeometryProjector polygonProjector = new PrototypeGeometryProjector(reverse);

        Polyline polyline = (Polyline) TestResources.getUkWiggle().getGeometry();

        Polyline transformed = polylineProjector.transform(polyline);

        Geometry execute = OperatorBuffer.local().execute(transformed, null, 10000, null);
        Polygon transform = polygonProjector.transform((Polygon) execute);

        export(transform);

    }

    private void export(Geometry geom) {

        String execute2 = OperatorExportToGeoJson.local().execute(geom);
        System.out.println(execute2);
    }
}
