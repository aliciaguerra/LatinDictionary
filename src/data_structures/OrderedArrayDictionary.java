/*
*Alicia Guerra
*Professor Steve Price
*CS 310: Data Structures
*December 12, 2014
*masc 1529
*/
package data_structures;
/*An iterator over a collection. Iterator takes the place of Enumeration in the 
Java Collections Framework. Iterators differ from enumerations in two ways:
Iterators allow the caller to remove elements from the underlying collection 
during the iteration with well-defined semantics.Method names have been improved.*/
import java.util.Iterator;
/*Thrown by the nextElement method of an Enumeration to indicate there are no more
methods in the enumeration.*/
import java.util.NoSuchElementException;
public class OrderedArrayDictionary<K, V> implements DictionaryADT<K, V> {
class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
/*One of the advantages of an array-based ordered dictionary is that searching 
an entry in an ordered search table by binary search takes O(logn). Realistically, 
a sorted array would be advantageous if you want to get a sorted listing of items, 
or do a ranged search of anything except an exact match. If all you’re doing is 
searching “onesie twosie”, a hash table is conceptually faster. In practice, 
the array and binary search may be faster due to caching though. In a hash table, 
depending upon the hash algorithm and the order of the table, collisions are 
common, so you have to traverse the chain to determine if you have a match if 
you are doing a lookup/comparison. With a sorted array, searches are nominally 
slower, but range searches and ordered output are a lot simpler.*/
    K key;
    V value;
    public DictionaryNode(K key, V value) {
    this.key = key;
    this.value = value;}
    @Override
    public int compareTo(DictionaryNode<K, V> node) {
    return ((Comparable<K>) key).compareTo(node.key);}}
/*We create a class called "IteratingKeys" that will serve as our key iterator.*/
    public class IteratingKeys implements Iterator<K> {
    DictionaryNode<K, V>[] collection = new DictionaryNode[currentSize];
    private int index1;
    public IteratingKeys() {
    for (int i = 0; i < currentSize; i++) {
    collection[i] = new DictionaryNode<K, V>(storage[i].key, storage[i].value);}
    AQuickSorter.quicksort(collection);}
    @Override
/*The java.util.Scanner.hasNext() method Returns true if this scanner has another 
token in its input. This method may block while waiting for input to scan. The 
scanner does not advance past any input.*/
    public boolean hasNext() {
    return index1<currentSize;}
    @Override
    public K next() {
    if (!hasNext()) {
    throw new NoSuchElementException();}
    return collection[index1++].key;}
    @Override
    public void remove() {
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
    T pivot, temp;
    pivot = array[left0];
    do{
    do
    left++;
    while (left <= right0 && array[left].compareTo(pivot) < 0);
    do
    right--;
    while (array[right].compareTo(pivot) > 0);
    if (left < right) {
    temp = array[left];
    array[left] = array[right];
    array[right] = temp;}}
    while (left <= right);
    temp = array[left0];
    array[left0] = array[right];
    array[right] = temp;
    if (left0 < right)
    quicksort(array, left0, right);
    if (left < right0)
    quicksort(array, left, right0);}}
/*We create a public class called "IteratingValues*/
public class IteratingValues implements Iterator<V> {
    DictionaryNode<K, V>[] collection = new DictionaryNode[currentSize];
    private int idx;
    public IteratingValues() {
    for (int i = 0; i < currentSize; i++) {
    collection[i] = new DictionaryNode<K, V>(storage[i].key, storage[i].value);}
    AQuickSorter.quicksort(collection);}
    @Override
/*The java.util.Scanner.hasNext() method Returns true if this scanner has another 
token in its input. This method may block while waiting for input to scan. 
The scanner does not advance past any input.*/
    public boolean hasNext(){
    return idx < currentSize;}
    @Override
    public V next() {
    if (!hasNext()){
    throw new NoSuchElementException();}
    return collection[idx++].value;}
    @Override
    public void remove() {
    throw new UnsupportedOperationException();}}
/*We finish declaring the rest of our variables.*/
    private int currentSize;
    private DictionaryNode<K, V>[] storage;
    private int maxSize;
    public OrderedArrayDictionary(){
    this(100);}
    @SuppressWarnings("unchecked")
    public OrderedArrayDictionary(int maxSize){
    this.maxSize = maxSize;
    currentSize = 0;
    storage = new DictionaryNode[maxSize];}
/*This adds the given key-value pair to the dictionary. It returns false if the
dictionary is full, or if the key is a duplicate. It returns true if the addition
succeeded.*/
    @Override
    public boolean add(K key, V value){
/*To add an entry to a sorted array-based dictionary, we search from the beginning 
to find the correct position for a new entry. After locating the correct position 
for the insertion, we shift the contents of the subsequent array locations toward 
the end of the array in the order indicated. Then we complete the insertion. The 
worst-case efficiencies are when locateIndex uses a binary search; the complexity 
for the addition method in this particular case is O(n).*/
    if (isFull()){
    return false;}
    int where = findPointtoAdd(key, 0, currentSize - 1);
    if (where == -1)
    return false;
    DictionaryNode<K, V>[] newSortedArray = new DictionaryNode[storage.length + 1];
    System.arraycopy(storage, 0, newSortedArray, 0, where);
/*We are placing our former array at the shift of our new array.*/
    System.arraycopy(storage, where, newSortedArray, where + 1, storage.length - where);
    newSortedArray[where] = new DictionaryNode<K, V>(key, value);
    storage = newSortedArray;
    currentSize++;
    return true;}
/*For our getValue method, we’ll use the binary search algorithm. A binary search 
finds the position of a position of a specified input value within an array sorted 
by key value. The algorithm compares the search key value with the key value of the 
middle element of the array. If the keys match, then a matching element has been 
found and its index, or position, is returned. Otherwise, if the search key is less 
than the middle element’s key, then the algorithm repeats its action on the sub-array 
to the left of he middle element or, if the search key is greater, on the sub-array 
to the right. If the remaining array to be searched is empty, then the key cannot 
be found in the array and a special “not found” indication is returned. A binary 
search halves the number of items to check with each iteration, so locating an 
item (or determining its absence) takes logarithmic time. The worst-case efficiencies 
are when locateIndex uses a binary search; the complexity for the delete method 
is this particular case is O(logn).*/      
private int BinarySearchAlgorithm(K keyvariable, int low, int high) {
    if (high < low)
    return -1;
    int middle = (low + high) >> 1; 
    int store = ((Comparable<K>) keyvariable).compareTo(storage[middle].key);
    if (store == 0)
    return middle;
    if (store < 0)
    return BinarySearchAlgorithm(keyvariable, low, middle - 1);
    return BinarySearchAlgorithm(keyvariable, middle + 1, high);}
/*This returns the dictionary object to an empty state.*/
    @Override
    public void clear() {
    currentSize = 0;}
/*This returns true if the dictionary has an array*/ 
    @Override
    public boolean contains(K key) {
    int where = findPointtoAdd(key, 0, currentSize - 1);
    return (where == -1);}
/*This deletes the key-value pair identified by the key parameter. It returns
true if the key/value pair was found and removed, and otherwise returns
false.*/    
    @Override
    public boolean delete(K key) {
    int index = getIndex(key);
    int clip = index - 1;
    if (index == 0)
    clip = 0;
    DictionaryNode<K, V>[] newArray = new DictionaryNode[maxSize];
    System.arraycopy(storage, 0, newArray, 0, clip);
    System.arraycopy(storage, index + 1, newArray, index, (currentSize - clip) - 1);
    storage = newArray;
    currentSize--;
    return true;}
/*We create a function entitled "findPointtoAdd" to assist us in calculating the
point of insertion.*/
    public int findPointtoAdd(K keyvariable, int low, int high) {
    if (high < low){
    return low;}
    int middle = (low + high) >> 1;
    if (((Comparable<K>) keyvariable).compareTo(storage[middle].key) == 0){
    return -1;}
    if (((Comparable<K>) keyvariable).compareTo(storage[middle].key) < 0){
    return findPointtoAdd(keyvariable, low, middle - 1);}
    return findPointtoAdd(keyvariable, middle + 1, high);}
    @SuppressWarnings("unchecked")
    public int getIndex(K key) {
    for (int a= 0;a<currentSize;a++) {
    if (((Comparable<K>) storage[a].key).compareTo(key) == 0) {
    return a;}}
    return -1;}
/*It returns the key associated with the parameter value. It returns null if the
value is not found in the dictionary. If more than one exists that matches the 
given value, returns the first one found.*/
    @Override
    public K getKey(V value) {
    if (isEmpty() || value == null)
    return null;
    for (int i = 0; i < currentSize; i++) {
    DictionaryNode<K, V> val = storage[i];
    if (val.value.equals(value)) {
    return val.key;}}
    return null;}
/*This returns the vslue associated with the parameter key.It returns null if
the key is not found or if the dictionary is empty..*/
    @Override
    public V getValue(K key) {
    if (isEmpty() || key == null)
    return null;
    for (int i = 0; i < currentSize; i++) {
    if (((Comparable<K>) storage[i].key).compareTo(key) == 0) {
    return storage[i].value;}}
    return null;}
/*This returns true if the dictionary is empty.*/
    @Override
    public boolean isEmpty(){
    return (size() < 1);}
/*This returns true if the dictionary is empty.*/
    @Override
    public boolean isFull(){
    return currentSize == maxSize;}
/*It returns an iterator of the keys in the dictionary, in ascending sorted
order. The iterator must be fail-fast.*/
    @Override
    public Iterator<K> keys() {
        return new IteratingKeys();}
/*This returns the number of key/value pairs currently stored in the dictionary.*/
    @Override
    public int size() {
    return currentSize;}
/*This returns an iterator of the values in the dictionary. The order of the 
values must match in the order of the keys. The iterator must be fail-fast.*/
    @Override
    public Iterator<V> values() {
    return new IteratingValues();}}
