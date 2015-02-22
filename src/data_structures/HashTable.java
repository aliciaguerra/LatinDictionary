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
during the iteration with well-defined semantics. Method names have been improved.*/
import java.util.Iterator;
import java.util.NoSuchElementException;
    public class HashTable<K, V> implements DictionaryADT<K, V>{
/*Our DictionaryNode is a "wrapper" class that holds our key and value pairs
that will be added to the list.*/
    class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>>{
    K key;
    V value;
    public DictionaryNode(K key, V value){
    this.key = key;
    this.value = value;}
/*This interface imposes a total ordering on the objects of each class that 
implements it. This ordering is referred to as the class's natural ordering, 
and the class's compareTo method is referred to as its natural comparison method.
Lists (and arrays) of objects that implement this interface can be sorted 
automatically by Collections.sort (and Arrays.sort). Objects that implement 
this interface can be used as keys in a sorted map or as elements in a sorted 
set, without the need to specify a comparator. The natural ordering for a class 
C is said to be consistent with equals if and only if e1.compareTo(e2) == 0 has 
the same boolean value as e1.equals(e2) for every e1 and e2 of class C. Note 
that null is not an instance of any class, and e.compareTo(null) should throw a 
NullPointerException even though e.equals(null) returns false. It is strongly 
recommended (though not required) that natural orderings be consistent with equals. 
This is so because sorted sets (and sorted maps) without explicit comparators 
behave "strangely" when they are used with elements (or keys)whose natural ordering 
is inconsistent with equals. In particular, such a sorted  set (or sorted map) 
violates the general contract for set (or map), which is 
defined in terms of the equals method. For example, if one adds two keys a and b 
such that (!a.equals(b) && a.compareTo(b) == 0) to a sorted set that does not use 
an explicit comparator, the second add operation returns false (and the size of 
the sorted set does not increase) because a and b are equivalent from the sorted 
set's perspective.Virtually all Java core classes that implement Comparable have 
natural orderings that are consistent with equals. One exception is 
java.math.BigDecimal, whose natural ordering equates BigDecimal objects with 
equal values and different precisions (such as 4.0 and 4.00).*/
    @Override
    public int compareTo(DictionaryNode<K, V> node) {
    return ((Comparable<K>) key).compareTo(node.key);}}
/*We create a class called "IteratingtheKeys" that will serve as our key iterator.*/
    public class IteratingtheKeys implements Iterator<K> {
    private DictionaryNode<K, V>[] nodes;
    private int idx;
    public IteratingtheKeys(){
    nodes = new DictionaryNode[currentSize];
    idx = 0;
    int j = 0;
    for (int i = 0; i < tableSize; i++){
    for (DictionaryNode<K, V> n : list[i]){
    nodes[j++] = n;}
/*This will be sorted.*/
    nodes = nodes; }
    AQuickSorter.quicksort(nodes);}
/*The java.util.Scanner.hasNext() method Returns true if this scanner has another 
token in its input. This method may block while waiting for input to scan. The 
scanner does not advance past any input.*/
    @Override
    public boolean hasNext(){
    return idx < currentSize;}
/*The java.util.Scanner.next() method finds and returns the next complete token 
from this scanner. A complete token is preceded and followed by input that matches 
the delimiter pattern. This method may block while waiting for input to scan, 
even if a previous invocation of hasNext() returned true.*/
    @Override
    public K next(){
    if (!hasNext()){
    throw new NoSuchElementException();}
    return nodes[idx++].key;}
/*To remove an element from the hash table, we’ll search for an item with key K. 
If such an item is found, we replace it with a special object and return the 
position of this item – otherwise, we’ll return a null position. The special 
object is introduced to replace deleted elements.*/
    @Override
    public void remove(){
    throw new UnsupportedOperationException();}}
/*Quicksort, or partition-exchange sort, is a sorting algorithm developed by 
Tony Hoare that, on average, makes O(n log n) comparisons to sort n items. 
In the worst case, it makes O(n^2) comparisons, though this behavior is rare. 
Quicksort is often faster in practice than other O(n log n) algorithms.
Additionally, quicksort's sequential and localized memory references work well 
with a cache. Quicksort is a comparison sort and, in efficient implementations, 
is not a stable sort. Quicksort can be implemented with an in-place partitioning 
algorithm, so the entire sort can be done with only O(log n) additional space 
used by the stack during the recursion.*/
    private static class AQuickSorter {
    static <T extends Comparable<? super T>> void quicksort(T[] array){
    quicksort(array, 0, array.length - 1);}
    static <T extends Comparable<? super T>> void quicksort(T[] array, int left0, int right0){
    int left = left0;
    int right = right0 + 1;
    T pivot, temporaryvariable;
    pivot = array[left0];
/*In the worst case, searches, insertions, and removals on a hash table take 
O(n) time. The worst case occurs when all the keys inserted into the dictionary 
collide. The expected running time of all the dictionary ADT operations in a 
hash table is O(1). In practice, hashing is very fast provided the load factor 
is not close to 100%. Hash tables might have a performance issue when they get 
filled up and need to reallocate (in the context of a hard real-time system). 
Hash tables need more memory than they actually use, whereas binary search 
trees only use as much memory as they need.*/
    do{
    do
    left++;
    while (left <= right0 && array[left].compareTo(pivot) < 0);
    do
    right--;
    while (array[right].compareTo(pivot) > 0);
    if (left < right) {
    temporaryvariable = array[left];
    array[left] = array[right];
    array[right] = temporaryvariable;}}
    while (left <= right);
    temporaryvariable = array[left0];
    array[left0] = array[right];
    array[right] = temporaryvariable;
    if (left0 < right)
    quicksort(array, left0, right);
    if (left < right0)
    quicksort(array, left, right0);}}
/*Here, we're creating a class called "IteratingValues" that will serve as our
values iterator.*/
    public class IteratingValues implements Iterator<V> {
    private Iterator<K> k;
    public IteratingValues(){
    k = keys();}
/*The java.util.Scanner.hasNext() method Returns true if this scanner has another 
token in its input. This method may block while waiting for input to scan. 
The scanner does not advance past any input.*/
    @Override
    public boolean hasNext(){
    return k.hasNext();}
/*The java.util.Scanner.next() method finds and returns the next complete token 
from this scanner. A complete token is preceded and followed by input that matches 
the delimiter pattern. This method may block while waiting for input to scan, 
even if a previous invocation of hasNext() returned true.*/
    @Override
    public V next(){
    if (!hasNext()){
    throw new NoSuchElementException();}
    return getValue(k.next());}
    @Override
    public void remove() {
    throw new UnsupportedOperationException();}}
/*We're implementing our UnorderedList.java file.*/
    private int currentSize, maxSize, tableSize;
    private UnorderedList<DictionaryNode<K, V>>[] list;
    DictionaryNode<K, V> AVAILABLE = new DictionaryNode<K, V>(null, null);
    public HashTable(int n){
    currentSize = 0;
    maxSize = n;
/*We set our tableSize to be about 30% larger.*/
    tableSize = (int) (maxSize * 13f);
/*We're using chaining. If we experience a collision in our hashtable, we'll just 
add it to our list. Our alternative is probing heuristic.*/
    list = new UnorderedList[tableSize];
    for (int b=0; b < tableSize; b++) {
    list[b] = new UnorderedList<DictionaryNode<K, V>>();}}
/*This adds the given key/value pair to the dictionary*/
    @Override
    public boolean add(K key, V value){
/*We can use a hash table with separate chaining to implement a dictionary ADT, 
with the difference that the dictionary allows multiple entries with the same key. 
This gives O(1) performance for find(), remove(), and insert(). To insert a record 
into the hash table, we need to figure out which bucket the new record should go 
into. And, of course, we’ll throw an exception if the table is full. We look up 
the hash index for that record, sending it the number of buckets M. The returned 
value should be in the range [0, M-1]. To insert a record into the bucket, we 
push back onto the bucket table. With this implementation, inserting a record is 
O(1). But if records within a bucket are unsorted, so our search and erase 
operations will be slower.*/
    if (this.isFull()){
    return false;}
    if (this.contains(key)) {}
    DictionaryNode<K, V> newNode = new DictionaryNode<K, V>(key, value);
    int index = getIndex(key);
    list[index].insertLast(newNode);
    currentSize++;
    return true;}
/*This will return the dictionary object to an empty state.*/
    @Override
    public void clear(){
    currentSize = 0;
    list = new UnorderedList[tableSize];
    for (int i = 0; i < tableSize; i++){
    list[i] = new UnorderedList<DictionaryNode<K,V>>();}}
/*This returns true if the dictionary has an object identified by a key in it,
otherwise false.*/
    @Override
    public boolean contains(K key){
    UnorderedList<DictionaryNode<K, V>> value = find(key);
    if (value.getCurrentSize() > 0)
    return true;
    else
    return false;}
/*This deletes the key/value pair identified by the key parameter. It returns 
true if the key/value pair was found and removed, otherwise false.*/
    @Override
    public boolean delete(K key){
    if (this.contains(key)) {
    int index = getIndex(key);
    int children_count = list[index].getCurrentSize();
    list[index].clear();
    currentSize = currentSize - children_count;
    return true;}
    return false;}
/*We are implementing our UnorderedList.java file.*/
    public UnorderedList<DictionaryNode<K, V>> find(K key) {
    int index = getIndex(key);
    UnorderedList<DictionaryNode<K, V>> value = this.list[index];
    return value;}
    public int getIndex(K key){
    return (key.hashCode() & 0x7FFFFFFF) % tableSize;}
/*This returns the key associated with the parameter value. It returns */
    @Override
    public K getKey(V value){
    for (int i = 0; i < list.length; i++){
    Iterator<DictionaryNode<K, V>> list_items = list[i].iterator();
    while (list_items.hasNext()){
    DictionaryNode<K, V> temp = list_items.next();
    if (temp != null && temp.value.equals(value))
    return temp.key;}}
    return null;}
/*This returns the value associated with the parameter key. It returns null if
the key is not found or the dicitionary is empty.*/
    @Override
    public V getValue(K key){
    int index = getIndex(key);
    DictionaryNode<K, V> fake_node = new DictionaryNode<K, V>(key, null);
    DictionaryNode<K, V> temp = list[index].find(fake_node);
    if (temp == null)
    return null;
    return temp.value;}
/*This will return true if the dictionary is empty.*/
    @Override
    public boolean isEmpty(){
    return this.size() < 1;}
/*This returns true if the dictionary is at max capacity.*/
    @Override
    public boolean isFull() {
    return (this.size() >= this.maxSize);}
/*This returns an iterator of the keys in the dictionary, in ascending sorted
order. The iterator must be fail-fast.*/
    @Override
    public Iterator<K> keys(){
    return new IteratingtheKeys();}
/*This returns the number of key/value pairs currently stored in the dictionary.*/
    @Override
    public int size(){
    return currentSize;}
/*This returns an iterator of the values in the dictionary. The order of the 
values must match the order of the keys. The iterator must be fail-fast.*/
    @Override
    public Iterator<V> values(){
    return new IteratingValues();}}