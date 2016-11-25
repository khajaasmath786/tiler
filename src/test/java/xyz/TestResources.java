package xyz;

import com.esri.core.geometry.*;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by willtemperley@gmail.com on 21-Oct-16.
 */
public class TestResources {

    static OperatorImportFromGeoJson fromGeoJson = OperatorImportFromGeoJson.local();
    static OperatorImportFromWkt fromWkt = OperatorImportFromWkt.local();

    private static MapGeometry fromGeoJson(String json) throws JSONException {

        return fromGeoJson.execute(0, Geometry.Type.Polygon, json, null);
    }

    private static File getFile(String fn) {
        ClassLoader classLoader = TestResources.class.getClassLoader();
        return new File(classLoader.getResource(fn).getPath());
    }

    public static List<Polygon> getTiles(Geometry.Type geomType) {

        List<String> lines = getLinesFromFile("madagascar.z6.wkt");
        List<Polygon> polygons = new ArrayList<>(lines.size());
        for (String line : lines) {
            //todo: if you ask for polygons, you should get polygons
            Geometry g = fromWkt.execute(0, geomType, line, null);
            polygons.add((Polygon) g);

        }

        return polygons;
    }

    private static List<String> getLinesFromFile(String fn) {
        try {
            return Files.readAllLines(getFile(fn).toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e); //just work, or explode
        }
    }

    //    public static void main(String[] args) throws JSONException, IOException {
    public static MapGeometry getMadagascar() {

        List<String> lines = getLinesFromFile("madagascar.geo.json");
        try {
            return fromGeoJson(lines.get(0));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public static MapGeometry getUkWiggle() {

        List<String> lines = getLinesFromFile("ukwiggle.json");
        try {
            String json = lines.get(0);
            return fromGeoJson.execute(0, Geometry.Type.Polyline, json, null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
