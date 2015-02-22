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
/*Thrown by the nextElement method of an Enumeration to indicate that there are 
no more elements in the enumeration.*/
import java.util.NoSuchElementException;
public class BinarySearchTree<K, V> implements DictionaryADT<K, V> {
/*The advantage of using a binary search tree is that all major operations 
(insert, search, and remove) are O(logn) in the average case. A balanced binary 
search tree has the advantage that its asymptotic complexity is actually an upper 
bound, while the “constant” times for hash tables are amortized times. However, 
if the tree is badly balanced, then the cost can be as bad as O(n). Binary search 
trees are memory-efficient. They do not reserve more memory than they need to. 
They also may be traversed to list off all the elements in order. And they can 
be implemented with a persistent interface, where a new tree is returned but the 
old tree continues to exist. Implemented carefully, the old and new trees share 
most of their nodes.*/
    private int currentSize;
    private int modCounter;
    private Node<K, V> root;
    public BinarySearchTree(){
    root = null;
    currentSize = 0;}
    private class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> leftChild;
    private Node<K, V> rightChild;
    public Node(K k, V v){
    key = k;
    value = v;
    leftChild = rightChild = null;}}
    public void print(Node<K, V> n) {
    if (n == null){
    return;}
    print(n.leftChild);
    System.out.println(n);
    print(n.rightChild);}
/*This returns truw if the dictionary has an object identified by the key in it,
otherwise false.*/
    @Override
    public boolean contains(K key){
    if(root==null)
    return false;
    Iterator<K> keys = this.keys();
    while (keys.hasNext()){
    if (keys.next().equals(key)) 
    return true;}
    return false;}
/*We add the given key/value pair to the dictionary. This returns false if the
dictionary is full, or if the key is a duplicate. It returns true if the addition 
succeeded.*/
    @Override
    public boolean add(K key, V value){
/*Insertion in a binary search tree begins as a search would begin; if the key 
is not equal to that of the root, we search the left or right subtrees. Eventually, 
we’ll reach an external node and add the key-value as its right or left child, 
depending on the node’s key. In other words, we will examine the root and 
recursively insert the new node to the left subtree if its key is less than that 
of the root, or the right subtree if the key is greater than or equal to the root.
This operation requires time proportional to the height of the tree in the worst 
case, which is O(logn) time in the average case over all trees, but O(n) time in 
the worst case.*/
    if (root == null)
    root = new Node<K, V>(key, value);
    else
    insert(key, value, root, null, false);
    currentSize++;
    modCounter++;
    return true;}
    private void insert(K k, V v, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
    if (n == null){
    if(wasLeft)
    parent.leftChild = new Node<K, V>(k, v);
    else
    parent.rightChild = new Node<K, V>(k, v);}
    else if (((Comparable<K>) k).compareTo((K) n.key) < 0)
    insert(k, v, n.leftChild, n, true);
    else
    insert(k, v, n.rightChild, n, false);}
/*This deletes the key/value pair identified by the key parameter. It returns
true if the key/value pair was found and removed, otherwise false.*/
    @Override
    public boolean delete(K key){
/*      When it comes to deleting a node in a binary search tree, there are three 
possible cases to consider:
(1) Deleting a Leaf: Deleting a leaf node is easy; we just remove it from the tree.
(2) Deleting a Node with One Child: We remove the node and replace it with its
    child.
(3) Deleting a Node with Two Children: We’ll call the node to be deleted “N”. We’ll 
    chose its successor or predecessor node (“R”). We copy the value of R to N, 
    then recursively call delete on R until reaching one of the first two cases.
    Broadly speaking, nodes with children are harder to delete. As with binary 
    trees, a node’s in-order successor is its right subtree’s left-most child, 
    and a node’s in-order predecessor is the left subtree’s right-most child. 
    In either case, the node will have zero or one children. Although this 
    operation does not always traverse the tree down to a leaf, there is always 
    a possibility; in the worst case, it requires height proportional. It does 
    not require more even when the node has two children, since it still follows 
    a single path and does not visit any nodes twice.*/
    if (!this.contains(key)){
    return false;}
    Node<K, V> node = find(key, root, 0);
    Node<K,V> parent = remove(node, root);
    root=parent;
    currentSize--;
    return true;}
/*This is our method to delete from the subtree.*/
    private Node<K,V> remove( Node<K,V> node_to_delete, Node<K,V> start_node )
    {if( start_node == null )
    return start_node; 
/*This interface imposes a total ordering on the objects of each class that 
implements it. This ordering is referred to as the class's natural ordering, 
and the class's compareTo method is referred to as its natural comparison method.
Lists (and arrays) of objects that implement this interface can be sorted 
automatically by Collections.sort (and Arrays.sort). Objects that implement this 
interface can be used as keys in a sorted map or as elements in a sorted set, 
without the need to specify a comparator. The natural ordering for a class C is 
said to be consistent with equals if and only if e1.compareTo(e2) == 0 has the 
same boolean value as e1.equals(e2) for every e1 and e2 of class C. Note that 
null is not an instance of any class, and e.compareTo(null) should throw a 
NullPointerException even though e.equals(null) returns false. It is strongly 
recommended (though not required) that natural orderings be consistent with 
equals. This is so because sorted sets (and sorted maps) without explicit 
comparators behave "strangely" when they are used with elements (or keys) 
whose natural ordering is inconsistent with equals. In particular, such a 
sorted set (or sorted map) violates the general contract for set (or map), 
which is defined in terms of the equals method. For example, if one adds 
two keys a and b such that (!a.equals(b) && a.compareTo(b) == 0) to a sorted 
set that does not use an explicit comparator, the second add operation 
returns false (and the size of the sorted set does not increase) because 
a and b are equivalent from the sorted set's perspective.*/
    if(((Comparable<K>)node_to_delete.key).compareTo( start_node.key ) < 0 )
    start_node.leftChild = remove( node_to_delete, start_node.leftChild );
    else if(((Comparable<K>)node_to_delete.key).compareTo( start_node.key ) > 0 )
    start_node.rightChild = remove( node_to_delete, start_node.rightChild );
    else if( start_node.leftChild != null && start_node.rightChild != null ) 
    {start_node.key = findMin( start_node.rightChild ).key;
    start_node.rightChild = remove( start_node, start_node.rightChild );}
    else
    start_node = ( start_node.leftChild != null ) ? start_node.leftChild : start_node.rightChild;
    return start_node;}
    private Node<K,V> findMin( Node<K,V> t )
    {if( t == null )
    return null;
    else if( t.leftChild == null )
    return t;
    return findMin( t.leftChild );}
/*This returns the value associated with the parameter jey. It returns null if the 
key is not found or if the dictionary is empty.*/
    @Override
    public V getValue(K key){
    return find(key, root);}
    private V find(K key, Node<K, V> n){
    if (n == null)
    return null;
/*This interface imposes a total ordering on the objects of each class that 
implements it. This ordering is referred to as the class's natural ordering, 
and the class's compareTo method is referred to as its natural comparison method.
Lists (and arrays) of objects that implement this interface can be sorted 
automatically by Collections.sort (and Arrays.sort). Objects that implement this 
interface can be used as keys in a sorted map or as elements in a sorted set, 
without the need to specify a comparator.The natural ordering for a class C is 
said to be consistent with equals if and only if e1.compareTo(e2) == 0 has the 
same boolean value as e1.equals(e2) for every e1 and e2 of class C. Note that 
null is not an instance of any class, and e.compareTo(null) should throw a 
NullPointerException even though e.equals(null) returns false. It is strongly 
recommended (though not required) that natural orderings be consistent with 
equals. This is so because sorted sets (and sorted maps) without explicit 
comparators behave "strangely" when they are used with elements (or keys) 
whose natural ordering is inconsistent with equals. In particular, such a 
sorted set (or sorted map) violates the general contract for set (or map), 
which is defined in terms of the equals method. For example, if one adds two 
keys a and b such that (!a.equals(b) && a.compareTo(b) == 0) to a sorted set 
that does not use an explicit comparator, the second add operation returns 
false (and the size of the sorted set does not increase) because a and b are 
equivalent from the sorted set's perspective.*/
    if (((Comparable<K>) key).compareTo(n.key) < 0){
    return find(key, n.leftChild);}
    else if (((Comparable<K>) key).compareTo(n.key) > 0) {
    return find(key, n.rightChild);}
    else
    return (V) n.value;}
    private Node<K, V> find(K key, Node<K, V> n, int dummy) {
    if (n == null)
    return null;
    if (((Comparable<K>) key).compareTo(n.key) < 0) {
    return find(key, n.leftChild, 0);}
    else if (((Comparable<K>) key).compareTo(n.key) > 0) {
    return find(key, n.rightChild, 0);}
    else
    return n;}
/*This returns the key associated with the parameter vaue. It returns null if 
the value is not found in the dictionary. If more than one key exists that matches
the given value, it returns the first one found.*/
    @Override
    public K getKey(V value){
    Iterator<K> k = keys();
    Iterator<V> v = values();
    while (k.hasNext()){
    K tempK = k.next();
    V tempV = v.next();
    if (((Comparable<V>) tempV).compareTo(value) == 0)
    return tempK;}
    return null;}
/*This returns the number of key/value pairs currently stored in the dictionary.*/
    @Override
    public int size(){
    return currentSize;}
/*This returns true if the dictionary is at max capacity.*/
    @Override
    public boolean isFull(){
    return false;}
/*This returns true if the dictionary is empty.*/
    @Override
    public boolean isEmpty(){
    return this.size() < 1;}
/*This returns the dictionary object to an empty state.*/
    @Override
    public void clear(){
    root = null;
    currentSize = 0;}
    @Override
    public Iterator<K> keys(){
    return new HelpingIterateKeys();}
    @Override
    public Iterator<V> values(){
    return new IteratingValues();}
/*We create a public class called "HelpingIterateKeys" that will help us iterate
through our keys.*/
    public class HelpingIterateKeys implements Iterator<K> {
    K[] array;
    private int index;
    private int endIndex;
    public HelpingIterateKeys() {
    array = (K[]) new Object[currentSize];
    OrderedArrayFiller(root);
    index = 0;
    this.endIndex = array.length;}
/*"OrderedArrayFiller" will help us fill in our array in order.*/
    private void OrderedArrayFiller(Node<K, V> n) {
    if (n == null)
    return;
    OrderedArrayFiller(n.leftChild);
    array[index++] = (K) n.key; 
    OrderedArrayFiller(n.rightChild);}
/*The java.util.Scanner.hasNext() method Returns true if this scanner has another 
token in its input. This method may block while waiting for input to scan. The 
scanner does not advance past any input.*/
    @Override
    public boolean hasNext(){
    return (index < endIndex);}
    @Override
    public K next(){
    if (hasNext() == false){
    throw new NoSuchElementException();}
    return (K) array[index++];}
    @Override
    public void remove(){
    throw new UnsupportedOperationException();}}
/*We create a public class called "IteratingValues" to use the iterator to help
us iterate through our values.*/
    public class IteratingValues implements Iterator<V> {
    V[] array;
    private int index;
    private int endIndex;
    public IteratingValues(){
    array = (V[]) new Object[currentSize];
    OrderedArrayFiller(root);
    index = 0;
    this.endIndex = array.length;}
    private void OrderedArrayFiller(Node<K, V> n) {
    if (n == null)
    return;
    OrderedArrayFiller(n.leftChild);
    array[index++] = (V) n.value;
    OrderedArrayFiller(n.rightChild);}
/*The java.util.Scanner.hasNext() method Returns true if this scanner has another 
token in its input. This method may block while waiting for input to scan. 
The scanner does not advance past any input.*/
    @Override
    public boolean hasNext(){
    return (index < endIndex);}
    @Override
    public V next(){
    if (hasNext() == false){
    throw new NoSuchElementException();}
    return (V) array[index++];}
    @Override
    public void remove(){
    throw new UnsupportedOperationException();}}}