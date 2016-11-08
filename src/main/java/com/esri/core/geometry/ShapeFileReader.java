package com.esri.core.geometry;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Reads a shapefile
 * Created by willtemperley@gmail.com on 07-Nov-16.
 */
public class ShapeFileReader extends GeometryCursor {

    private final MixedEndianDataInputStream inputStream;
    private final Envelope2D envelope2D;

    private OperatorImportFromESRIShape importFromESRIShape = OperatorImportFromESRIShape.local();
    private final int fileLengthBytes;
    private int position = 0; //keeps track of where inputstream is
    private int recordNumber; //the record number according to shapefile

    @Override
    public Geometry next() {
        try {

            recordNumber = inputStream.readInt();//1 based
            int recLength = inputStream.readInt();
            position += 8;

            int recordSizeBytes = (recLength * 2);
            byte[] bytes = new byte[recordSizeBytes];
            int read = inputStream.read(bytes);

            Geometry polyline = importFromESRIShape.execute(0, Geometry.Type.Polyline, ByteBuffer.wrap(bytes));
            position += recordSizeBytes;

            return polyline;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getGeometryID() {
        return recordNumber;
    }

    public Envelope2D getEnvelope2D() {
        return envelope2D;
    }

    /**
     * Augments DataInputStream with methods to read little-endian primitives, as the ESRI
     * shapefile format is mixed-endian.
     */
    private static class MixedEndianDataInputStream extends DataInputStream {
        private byte readBuffer[] = new byte[8];

        MixedEndianDataInputStream(InputStream inputStream) {
            super(inputStream);
        }

        /**
         * copied from {@link DataInputStream#readInt()} just reversing the variable names
         */
        final int readLittleEndianInt() throws IOException {
            int ch4 = in.read();
            int ch3 = in.read();
            int ch2 = in.read();
            int ch1 = in.read();
            if ((ch1 | ch2 | ch3 | ch4) < 0)
                throw new EOFException();
            return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
        }

        /**
         * copied from {@link DataInputStream#readLong()} ()} just reversing the array indices
         */
        long readLittleEndianLong() throws IOException {
            readFully(readBuffer, 0, 8);
            return (((long)readBuffer[7] << 56) +
                    ((long)(readBuffer[6] & 255) << 48) +
                    ((long)(readBuffer[5] & 255) << 40) +
                    ((long)(readBuffer[4] & 255) << 32) +
                    ((long)(readBuffer[3] & 255) << 24) +
                    ((readBuffer[2] & 255) << 16) +
                    ((readBuffer[1] & 255) <<  8) +
                    ((readBuffer[0] & 255)));
        }

        private double readLittleEndianDouble() throws IOException {
            return Double.longBitsToDouble(readLittleEndianLong());
        }

    }

    public ShapeFileReader(InputStream in) throws IOException {

        this.inputStream = new MixedEndianDataInputStream(in);

        /*
        Byte 0 File Code 9994 Integer Big
         */
        int fileCode = inputStream.readInt();
        if (fileCode != 9994) {
            throw new IOException("file code " + fileCode + " is not supported.");
        }

        /*
        Byte 4 Unused 0 Integer Big
        Byte 8 Unused 0 Integer Big
        Byte 12 Unused 0 Integer Big
        Byte 16 Unused 0 Integer Big
        Byte 20 Unused 0 Integer Big
         */
        inputStream.skipBytes(20);

        fileLengthBytes = inputStream.readInt() * 2;
        System.out.println("fileLength = " + fileLengthBytes);

        int v = this.inputStream.readLittleEndianInt();

        if (v != 1000) {
            throw new IOException("version " + v + " is not supported.");
        }

        int shapeType = this.inputStream.readLittleEndianInt();
        System.out.println("shapeType = " + shapeType);

        /*
        Byte 24 File Length File Length Integer Big
        Byte 28 Version 1000 Integer Little
        Byte 32 Shape Type Shape Type Integer Little
        Byte 36 Bounding Box Xmin Double Little
        Byte 44 Bounding Box Ymin Double Little
        Byte 52 Bounding Box Xmax Double Little
        Byte 60 Bounding Box Ymax Double Little
        Byte 68* Bounding Box Zmin Double Little
        Byte 76* Bounding Box Zmax Double Little
        Byte 84* Bounding Box Mmin Double Little
        Byte 92* Bounding Box Mmax Double Little
        */
        double xmin = this.inputStream.readLittleEndianDouble();
        double ymin = this.inputStream.readLittleEndianDouble();
        double xmax = this.inputStream.readLittleEndianDouble();
        double ymax = this.inputStream.readLittleEndianDouble();
        double zmin = this.inputStream.readLittleEndianDouble();
        double zmax = this.inputStream.readLittleEndianDouble();
        double mmin = this.inputStream.readLittleEndianDouble();
        double mmax = this.inputStream.readLittleEndianDouble();

        envelope2D = new Envelope2D(xmin, ymin, xmax, ymax);
//        envelope3D = new Envelope3D(xmin, ymin, zmin, xmax, ymax, zmax);

        position = 2 * 50; //header is always 50 words long

    }

    public boolean hasNext() {
        return position < fileLengthBytes;
    }

}
