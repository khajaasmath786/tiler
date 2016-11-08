package xyz;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.ShapeFileReader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by willtemperley@gmail.com on 08-Nov-16.
 */
public class ShapeFileReaderTest {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/canary.shp"));
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        ShapeFileReader shapeFileReader = new ShapeFileReader(dataInputStream);

        while(shapeFileReader.hasNext()) {
            Geometry next = shapeFileReader.next();
            System.out.println("next = " + next.getType());
        }

    }
}
