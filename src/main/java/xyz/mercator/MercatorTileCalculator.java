package xyz.mercator;

import com.esri.core.geometry.Envelope2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willtemperley@gmail.com on 19-Jan-17.
 */
public class MercatorTileCalculator extends GlobalMercator {

    public List<MercatorTile> tilesForEnvelope(Envelope2D env, int zoom) {

        /*
        This system follows image coordinates
         */

        double xmin = env.xmin;
        double ymin = env.ymin;
        double xmax = env.xmax;
        double ymax = env.ymax;

        MercatorTile ul = tileForCoordinate(xmin, ymax, zoom);
        MercatorTile lr = tileForCoordinate(xmax, ymin, zoom);

        /*
        Could put tmsTiles in array or provide zipped vectors
         */
        List<MercatorTile> mercatorTiles = new ArrayList<>();

        //iterate ll to ur
        for (int i = ul.getX(); i <= lr.getX(); i++) {
            for (int j = lr.getY(); j <= ul.getY(); j++) {
                mercatorTiles.add(new MercatorTile(i, j, zoom));
            }
        }
        return mercatorTiles;
    }


}
