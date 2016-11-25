package xyz;

import org.junit.Test;

/**
 * Created by willtemperley@gmail.com on 17-Nov-16.
 */
public class Tilecounts {

    @Test
    public void t1() {


        for (int z = 0; z < 20; z++) {
            int xC = 2 << z;
            System.out.println(z + " = " + xC);
        }



//        System.out.println("pixC = " + pixC);

    }
}
