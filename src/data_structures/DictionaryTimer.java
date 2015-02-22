package data_structures;

/*  DictionaryTimer.java
    CS310, Fall 2014
    A sample driver for doing timing tests on insert and search operations.
        STRUCTURE_SIZE - Size of the dictionary.
        STEP_SIZE      - Increase the size of the dictionary by this amount
                         for each iteration
        ITERATIONS     - Number of timing tests to perform
        M_NUMBER       - Number of search/insert/delete operations to be timed
                         for each size.
    Try testing with the supplied default values, and make modifications
    as needed depending on the implementation tested and the speed of the
    machine you are using.
    
*/    
    
import data_structures.*;

public class DictionaryTimer {
    public static void main(String [] args) {
        new DictionaryTimer();
        }
        
    public DictionaryTimer() {
        int STRUCTURE_SIZE = 100000;
        int STEP_SIZE = 10000;
        int ITERATIONS = 10;
        int M_NUMBER = 10000;
        int maxSize = STRUCTURE_SIZE+M_NUMBER+STEP_SIZE*ITERATIONS;
        long start, stop;
        DictionaryADT dictionary;
//======================================================================

        Integer [] array = new Integer[maxSize];
        for(int i=0; i < maxSize; i++)
            array[i] = new Integer(i+1);
            
        for(int i=0; i < maxSize; i++) {
            int idx = ( (int) (maxSize * Math.random()));
            Integer tmp = array[idx];
            array[idx] = array[i];
            array[i] = tmp;
            }            
        
//======================================================================

        System.out.println(
        "Timing for iterations of size: " + M_NUMBER);           
        for(int i=0; i < ITERATIONS; i++) { 
        
//======================================================================     
//           dictionary = new OrderedArrayDictionary(STRUCTURE_SIZE+M_NUMBER);  
//            dictionary = new HashTable(STRUCTURE_SIZE+M_NUMBER);            
//            dictionary = new BinarySearchTree();
            dictionary = new RedBlackTree();
// Change the line above to test different implementations.
        System.out.println("Testing the " + dictionary.getClass() + " implementation");
//======================================================================  
        System.gc(); //run the garbage collector 
        
            for(int j=0; j < STRUCTURE_SIZE; j++) {
         
                if(!dictionary.add(array[j],array[j])) {
                    System.out.println(
                    "ERROR, could not add item at index " + j);
                    System.exit(1);
                    }
                }
            //time insertion                    
            start = System.currentTimeMillis();
            for(int j=STRUCTURE_SIZE; j < (M_NUMBER+STRUCTURE_SIZE); j++) {
                if(!dictionary.add(array[j],array[j])) {
                    System.out.println(
                    "ERROR, could not add item at index " + j);
                    System.exit(1);
                    }
                }
            stop = System.currentTimeMillis();
            System.out.println(
            "Time for insertion in structure with " + 
            STRUCTURE_SIZE + " items: " + (stop-start));  
            
            //remove the added items
            start = System.currentTimeMillis();            
            for(int j=STRUCTURE_SIZE; j < (M_NUMBER+STRUCTURE_SIZE); j++) {
                if(!dictionary.delete(array[j])) {
                    System.out.println(
                    "ERROR, could not remove item at index " + j);
                    System.exit(1);
                    }                
                }
            stop = System.currentTimeMillis();
            System.out.println(
            "Time for deletion in structure with " + 
            STRUCTURE_SIZE + " items: " + (stop-start));   
                           
            //time search
            start = System.currentTimeMillis();
            for(int j=0; j < M_NUMBER; j++) {
                Integer tmp = (Integer) dictionary.getValue(array[i]);
                if(tmp == null) {
                    System.out.println(
                    "ERROR, item not found during search!!");
                    System.exit(1);
                    }
                }
            stop = System.currentTimeMillis();
            System.out.println(
            "Time for search in structure with " + 
            STRUCTURE_SIZE + " items: " + (stop-start));
            //now remove all items, just an extra test for your program
            for(int j=0; j < STRUCTURE_SIZE; j++) {
         
                if(!dictionary.delete(array[j])) {
                    System.out.println(
                    "ERROR, could not add remove at index " + j);
                    System.exit(1);
                    }
                }                 
            //system should now be empty
            dictionary.clear();             
            STRUCTURE_SIZE += STEP_SIZE;
            System.out.println();
        }
                
                       
    }
} 
