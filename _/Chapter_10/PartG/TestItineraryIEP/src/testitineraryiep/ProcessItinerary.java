/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testitineraryiep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Frank J
 */
public class ProcessItinerary {

    private File file = null;
    private File oFile = null;
    private FileWriter writer = null;

    public ProcessItinerary(String fName) {
        try {
            file = new File(fName);
            oFile = new File("E:/work/AirAlliance/PartG/Config/data.txt");
            writer = new FileWriter(oFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void doFilter() {
        FileReader reader = null;
        try {
            //Initialize data file

            writer.write("SeqID,FirstName,FoodPreference,Source,GuestID,SeatPreference,Destination,LastName");
            writer.write("\r\n");

            reader = new FileReader(file);
            BufferedReader breader = new BufferedReader(reader);

            String line = null;

            while ((line = breader.readLine()) != null) {
                if (line.indexOf("xmlns:ns1") == -1) {
                    if (!line.equals("</ns1:ITINERARY>")) {
                        if (line.trim().length() > 0) {
                            processLine(line);                            
                        }
                    }
                }

            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void processLine(String line) {
        String nline = line.substring(4, line.length() - 1);

        StringTokenizer stok = new StringTokenizer(nline, ">");
        String key = stok.nextToken();
        String dvalue = stok.nextToken();
        String value = new StringTokenizer(dvalue, "<").nextToken();
        //System.out.println("Key:" + key + " Value:" + value);
        if (!key.equals(":TRAVELDATE")) {
            try {
                writer.write(value);
                if (key.equals(":LASTNAME")) {
                    try {
                        writer.write("\r\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    writer.write(",");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }

    public static void main(String ar[]) {
        ProcessItinerary itinerary = new ProcessItinerary("E:/work/AirAlliance/PartG/Config/test.xml");
        itinerary.doFilter();
    }
}
