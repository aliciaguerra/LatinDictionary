/*
 *Alicia Guerra
 *CS 310: Data Structures
 *Professor Steve Price
 *masc1529
*/

/*Reads the Latin.txt datafile and creates an array of DictionaryEntry.*/
package data_structures;


import java.io.*;
import java.util.*;


public class DictionaryReader {


    static DictionaryEntry [] entries = new DictionaryEntry[8328];
    static int index = 0;
        
    public static DictionaryEntry[] getDictionaryArray(String fileName) {
        
        String line;
        try {
            BufferedReader in = new BufferedReader(
                new FileReader(fileName));
            while((line = in.readLine()) != null) {
                processLine(line);
                }
            }
        catch(FileNotFoundException e) {
            throw new RuntimeException(
            "The file " + fileName + " was not found.");
            }
        catch(IOException e) {
            throw new RuntimeException(
            "Error reading file " + fileName + " from disk.");
            }
        catch(Exception e) {
            e.printStackTrace();
            //System.out.println( "Sorry, a FATAL error has occurred: " +e);
            System.exit(1);
            }
        return entries;            
        }
        
    private static void processLine(String line) {
        if(line.startsWith("#")) return;
        StringTokenizer t = new StringTokenizer(line,"\t");
        if(!t.hasMoreElements()) return;
        String v = (String) t.nextElement();
        String k = (String) t.nextElement();
        entries[index++] = new DictionaryEntry(k,v);
    }
        
    public static void printArray() {
        for(int i=0; i < 8328; i++) {
            if(entries[i] == null) {
                System.out.println("NULL at index " + i);
                return;
                }
            System.out.println(entries[i].getKey() + " ***** " + 
            entries[i].getValue());
            }
        }
        
        
     
}