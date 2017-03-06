package xyz.tms;

import com.esri.core.geometry.Envelope2D;
import xyz.tms.TmsTile;

import java.util.ArrayList;
import java.util.List;

/**
 * TmsTile which knows how to encode itself with HBase-friendly keys
 *
 * Created by willtemperley@gmail.com on 24-Oct-16.
 */
public class TmsTileCalculator  {


    /**
     * Retrieve the tile which contains this LL
     * @param lon
     * @param lat
     * @param zoom
     * @return
     */
    public static TmsTile getTile(Double lon, Double lat, int zoom) {
        /*
         * Looking for the first tile from left
         */
        int nX = 2 << zoom; //tiles for x at zoom
        int nY = 1 << zoom; //tiles for x at zoom

        double step = 360D / nX; //tile width
        int Xt = (int) Math.floor((lon + 180) / step);
        int Yt = (int) Math.floor((lat +  90) / step);

        return new TmsTile(Xt, Yt, zoom);
    }


    public static List<TmsTile> tilesForEnvelope(Envelope2D env, int zoom) {

        double xmin = env.xmin;
        double ymin = env.ymin;
        double xmax = env.xmax;
        double ymax = env.ymax;

        TmsTile ll = getTile(xmin, ymin, zoom);
        TmsTile ur = getTile(xmax, ymax, zoom);

        /*
        Could put tmsTiles in array or provide zipped vectors
         */
        List<TmsTile> tmsTiles = new ArrayList<>();

        //iterate ll to ur
        for (int i = ll.getX(); i <= ur.getX(); i++) {
            for (int j = ll.getY(); j <= ur.getY(); j++) {
                tmsTiles.add(new TmsTile(i, j, zoom));
            }
        }
        return tmsTiles;
    }
}
