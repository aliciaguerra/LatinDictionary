/*
*Alicia Guerra
*Professor Steve Price
*CS 310: Data Structures
*December 12, 2014
*masc1529
*/
package data_structures;
/*An iterator over a collection. Iterator takes the place of Enumeration in the 
Java Collections Framework. Iterators differ from enumerations in two ways:
Iterators allow the caller to remove elements from the underlying collection 
during the iteration with well-defined semantics.Method names have been improved.*/
import java.util.Iterator;
public class LatinDictionary{
private DictionaryADT<String, String> dictionary;
DictionaryEntry[] entries;
public LatinDictionary() {}
/*This will read the key-value pairs from the datafile and build a dictionary 
structure.*/
public void load(String fileName){
/*To acquire the entires, we'll go to our dictionary reader and get the array.*/
entries = DictionaryReader.getDictionaryArray(fileName);
/*Here, we're creating a hash table for our dictionary entries.*/
dictionary = new HashTable<String, String>(entries.length + 50);
for (DictionaryEntry entry : entries) {
dictionary.add(entry.getKey(), entry.getValue());}}
/*This will insert a new word along with its definition.*/
public boolean insert(String key, String value){
return dictionary.add(key, value);}
/*This will remove the key and value that is identified by the key from the
dictionary.*/
public boolean delete(String key){
return dictionary.delete(key);}
/*This will look up the definition of the word key.*/
public String getDefinition(String latinWord){
return dictionary.getValue(latinWord);}
/*This returns true if the word is already in the dictionary.*/
public boolean containsWord(String latinWord){
return dictionary.contains(latinWord);}
/*We implement this to get the range of our words.*/
public String[] getRange(String start, String finish){
UnorderedList<String> list=new UnorderedList<String>();
/*We're going to start iterating through our words.*/
Iterator<String> keys=this.words();
/*In computer science, the Boolean data type is a data type, having two values 
(usually denoted true and false), intended to represent the truth values of logic 
and Boolean algebra. It is named after George Boole, who first defined an algebraic 
system of logic in the mid 19th century. The Boolean data type is the primary 
result of conditional statements, which allow different actions and change 
control flow depending on whether a programmer-specified boolean condition 
evaluates to true or false. It is a special case of a more general logical 
data type; logic does not always have to be boolean.*/
boolean started=false;
boolean finished=false;
while(keys.hasNext() && finished==false)
{String next=(String) keys.next();
if(next.matches("(?i)"+start+"*"))
started=true;
if(next.matches("(?i)"+finish+".*"))
break;
if(started)
list.insertLast(next);}
/*This will copy to our array.*/       
String [] array = new String [list.getCurrentSize()];
int i=0;
for(String entry: list)
{array[i]=entry;
i++;}
return array;}
/*This returns an iterator of the words in the dictionary in sorted order.*/
public Iterator<String> words(){
return dictionary.keys();}
/*This will return the definition in the dictionary, in exactaly the same order
as the iterator.*/
public Iterator<String> definitions(){
return dictionary.values();}}