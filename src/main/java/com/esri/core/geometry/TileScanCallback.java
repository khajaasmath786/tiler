package com.esri.core.geometry;

/**
 * Created by willtemperley@gmail.com on 15-Nov-16.
 */
public interface TileScanCallback extends SimpleRasterizer.ScanCallback {

    /**
     * Fixme this might be an image or a backing array
     * @return
     */
    public byte[] getImage();

    int getWidth();

    int getHeight();

    byte[] getBitSet();
}
