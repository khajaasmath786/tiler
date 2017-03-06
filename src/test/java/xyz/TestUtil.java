package xyz;

import com.esri.core.geometry.*;
import xyz.tms.TmsTile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by willtemperley@gmail.com on 08-Nov-16.
 */
public class TestUtil {

    static SpatialReference spatialRef = SpatialReference.create(4326);

    public static void writeDebugTile(TmsTile tmsTile, byte[] bytes) throws IOException {
        System.out.println("writing tmsTile: " + tmsTile);
        File f = new File("e:/tmp/ras/" + tmsTile.toString() + ".png");
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        for (byte aByte : bytes) {
            fileOutputStream.write(aByte);
        }
    }

    public static void printGeoJson(Geometry geometry) {
        OperatorExportToGeoJson operatorExportToGeoJson = OperatorExportToGeoJson.local();
        String execute = operatorExportToGeoJson.execute(0, spatialRef, geometry);

        System.out.println("{\"type\": \"FeatureCollection\",");
        System.out.println("  \"features\": [");
        System.out.println("    {");
        System.out.println("      \"type\": \"Feature\",");
        System.out.println("        \"geometry\":" + execute + ",");
        System.out.println("        \"properties\": {");
        System.out.println("       \"prop0\": \"value0\"");
        System.out.println("      }");
        System.out.println("    }");
        System.out.println("  ]");
        System.out.println("}");
    }

    public static void printDebugGeometryConstruction(Polyline polyLine) {

        for (int i = 0; i < polyLine.getPointCount(); i++) {
            Point point = polyLine.getPoint(i);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(5);

            String Xs = df.format(point.getX());
            String Ys = df.format(point.getY());

            if (i == 0) {
                System.out.println("inputGeom.startPath(" + Xs + "," + Ys + ");");
            } else {
//                            System.out.println("inputGeom.lineTo(" + point.getX() + "," + point.getY() + ");");
                System.out.println("inputGeom.lineTo(" + Xs + "," + Ys + ");");
            }

        }
    }

}
