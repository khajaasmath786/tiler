package xyz;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by willtemperley@gmail.com on 29-Aug-16.
 */
public class KeyDistributionTest {


    /**
     * reversing the row key creates an almost perfect distribution over the range 00 to FF for the first bytes in a monotonically-increasing integer
     */
    @Test
    public void testKeyDistribution() {

        int nSamples = 10000;

        int[] Xs = randomInts(nSamples);
        int[] Ys = randomInts(nSamples);

        TileCalculator.Tile[] tiles = new TileCalculator.Tile[nSamples];
        for (int i = 0; i < nSamples; i++) {
            tiles[i] = new TileCalculator.Tile(Xs[i], Ys[i], 14);
        }

        testKeyDistribution(tiles);

    }


//    http://stackoverflow.com/questions/7940439/java-create-array-with-random-ints-ints-can-only-be-used-once
    public static int[] randomInts(int nSamples) {

        int[] a = new int[nSamples];
        for (int i = 1; i <= nSamples; i++)
        {
            a[i-1] = i;
        }

        Random rg = new Random();
        int tmp;
        for (int i = nSamples -1; i > 0; i--) {
            int r = rg.nextInt(i+1);
            tmp = a[r];
            a[r] = a[i];
            a[i] = tmp;
        }

//        for (int i = 0; i < nSamples; i++)
//            System.out.print(a[i] + " ");

        return a;

    }


    /**
     * The tile key from {@link TileCalculator.Tile#encode()} should distribute evenly across all values of it's first byte
     * @param tiles tiles to test
     */
    public void testKeyDistribution(TileCalculator.Tile[] tiles) {

        long[] freq = new long[256];

        for (TileCalculator.Tile tile : tiles) {
            byte[] bytes = tile.encode();

            int unsigned = unsignedToBytes(bytes[0]);
            freq[unsigned] += 1;
        }

        Arrays.sort(freq); //why no min max in java standard libs?

        long min = freq[0];
        System.out.println("min = " + min);

        long max = freq[freq.length -1];

        System.out.println("max = " + max);
        Assert.assertTrue(max - min <= 1);

    }

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

}
