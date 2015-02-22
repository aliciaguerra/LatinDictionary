package data_structures;

/*  DictionaryDriver
    Driver class to test your LatinDictionary application.
    CS310 Fall 2014
    Alan Riggins
*/    

import java.util.Iterator;
import data_structures.*;

public class DictionaryDriver {
    public static void main(String [] args) {
        new DictionaryDriver();
        }
        
    public DictionaryDriver() {
        String [] words = {
        "vobis","castanea","agricola","basilica","consilium","atavus","vulgus",
        "iuglans"};
        LatinDictionary dictionary = new LatinDictionary();
        dictionary.loadDictionary("Latin.txt");
        String definition;
        
        for(int i=0; i < words.length; i++) {
            definition = dictionary.getDefinition(words[i]);
            if(definition == null)
                System.out.println(
                    "Sorry, " + words[i] + " was not found.\n");
            else
                System.out.println(
                    "The definition of " + words[i] + " is:\n" + 
                    definition + ".\n");
            }
            
        // add a word not in the data file and make sure it can be found.    
        dictionary.insertWord("iuglans","A walnut.  Either the nut or the tree");
        definition = dictionary.getDefinition("iuglans");
        if(definition == null)
            System.out.println(
                "Sorry, iuglans" + " was not found.\n");
        else
            System.out.println(
                "The definition of iuglans" + " is:\n" + 
                definition + ".\n"); 
                
        if(!dictionary.deleteWord(words[0]))
            System.out.println("ERROR, delete FAILED!!!");
        if(dictionary.getDefinition(words[0]) != null)
            System.out.println("ERROR, returned deleted definition.");

        System.out.println("Now checking the getRange method\n");
        String [] myWords = dictionary.getRange("ab","ac");
        for(int i=0; i < myWords.length; i++)
            System.out.println(myWords[i] + "=" + dictionary.getDefinition(myWords[i]));

    }
}          
