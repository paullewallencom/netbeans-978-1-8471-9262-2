/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testitineraryiep;

/**
 *
 * @author Frank
 */
import com.sun.jbi.engine.iep.core.runtime.client.pojo.SheperdDriver;

public class Main {

    /** Creates a new instance of Main */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProcessItinerary itinerary = new ProcessItinerary("E:/work/AirAlliance/PartG/Config/test.xml");
        itinerary.doFilter();

        SheperdDriver.main(new String[]{
            "E:/work/AirAlliance/PartG/Config/config.properties",
            "E:/work/AirAlliance/PartG/Config/data.txt",
            "1",
            "-1",
            "1"
        });
    }
}
