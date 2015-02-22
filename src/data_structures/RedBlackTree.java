/*
*Alicia Guerra
*Professor Steve Price
*CS 310: Data Structures
*masc1529
*December 12, 2014
*/
package data_structures;
/*We import this to use an iterator over a collection. The iterator takes the place
of Enumeration in the Java Collections framework. Iterators differ from enumeration
in two ways: (1) iterators allow the caller to remove*/
import java.util.Iterator;
/*A map entry (key-value pair.
The Map.entrySet method returns a collection-view of the map, whose elements are
of this class. The only way to obtain a reference to a map entry is from the
iterator of this collection-view. These Map.Entry objects are valid only for the
duration of the iteration; more formally, the behavior of a map entry is undefined
if the backing map has been modified after the entry was returned by the iterator,
except though the setValue operation on the map entry.*/
import java.util.Map.Entry;
/*A Red-Black tree based map implementation. The map is sorted according to the
natural ordering of its keys, or by a Comparator provided at map creation time,
depending on which constructor is used.
This implementation provides guaranteed log(n) time cost for the containsKey,
get, put, and remove operations.
Note that the ordering maintained by a tree map, like any sorted map, and whether 
or not an explicit comparator is provided, must be consistent with equals if this
this sorted map is to correctly implement the Map interface. This is because the
Map interface is defined in terms of the equals operation*/
import java.util.TreeMap;
    public class RedBlackTree<K,V> implements DictionaryADT<K,V>{   
    TreeMap<K, V> storage;
/*We construct the red-black tree.*/
    public RedBlackTree()
    {storage= new TreeMap<K, V>();}
/*This returns true if the dictionary has an object identified by the key in it,
otherwise false.*/    
    @Override
    public boolean contains(K key){
    return storage.containsKey(key);}
/*This adds the given key/value pair to the dictionary. This returns false if
the dictionary is full, or if the key is a duplicate. It returns true if addition
suceeded.*/
    @Override
    public boolean add(K key, V value){
/*Insertion begins by adding the node as any binary search tree insertion does 
and coloring it red. Whereas in the binary tree, we always add a leaf, in the 
red-black tree, leaves contain no information, so instead we add a red interior 
node, with two black leaves, in place of an existing black leaf. What happens 
next depends on the color of other nearby nodes. The term uncle node will be 
used to refer to the sibling of a node’s parent, as in human family trees. 
We’ll note that:(1) The property of leaf nodes being black always holds. (2) The 
property that both children of every red node are black is threatened only by 
adding a red node, repainting a black node red, or a rotation.(3) The property 
that all paths from any given node to its leaf nodes contain the same number of 
black nodes is threatened only by adding a black node, repainting a red node 
black (or vice versa), or a rotation.In the first case, the current node is at 
the root of the tree. In that case, it is repainted to satisfy the property that 
the root of the tree is black. Since this adds one black node to every path at 
once, the property that all paths from any given node to its leaf nodes contain 
the same number of black nodes is not violated. In the second case, the current 
node’s parent is black, so the property that both children of every red node are 
black is not violated. In this case, the tree is still valid. The property that 
all paths from a given node to its leaf nodes contain the same number of black 
nodes is not threatened, because the current node has two black leaf children, 
but because the node is red, the paths through each of its children have the same 
number of black nodes as the path through the leaf it replaced, which was black, 
and so its property remains satisfied. In the third case, if both the parent and 
the uncle are red, then both of them can be repainted black and the grandparent 
G becomes red. Now the current node has a black parent. Since any path through 
the parent or uncle must pass through the grandparent, he number of black nodes 
on these paths has not changed. However, the grandparent might violate the 
properties that the root is supposed to be black and that both children of every 
red node are black. So, to fix this, the entire procedure is recursively performed 
on the grandparent from case 1. In the fourth case, the parent is red, but the 
uncle is black. Also, the current node is the right child of the parent and the 
parent is the left child of its parent. In this case, a left rotation on the parent 
that switches the roles of the current node and the parent can be performed; 
then, the former parent node is dealt with by relabeling the current and parent 
node because the property that both children of every red node being black is 
violated. The rotation causes some paths to pass through the node where they did 
not before. It also causes some paths not to pass through the node P where they 
did before. Both of these nodes are red so the property that all paths from any 
given nodes to its leaves contain the same number of black nodes is not being 
violated. After this case has been completed, the property of both children of 
every node being black is violated, but this can be resolved by going on to the 
fifth case. In the fifth case, the parent is red, but the uncle is black, so the
current node is the left child of the parent, and the parent is the left child 
of its parent. A right rotation on the parent is performed, and now the former 
parent is now the parent of both the current node and its former grandparent. 
The grandparent is black, so the colors of the parent and the grandparent are 
switched, and the resulting tree satisfies the property that the children of 
every red node are black. The property that all paths from the given node to its 
leaf nodes contain the same number of black nodes is satisfied, since all paths 
that went through any of these three nodes went through the grandparent before, 
and now they all go through the parent node. In each case, this is the only black 
node of the three.*/
    storage.put(key, value);
    return true;}
/*This deletes the key/value pair identified by the key parameter. This returns 
true if the key/value pair was found and removed, otherwise false.*/
    @Override
    public boolean delete(K key){
/*In a regular binary tree when deleting a node with two non-leaf children, we 
find either the maximum element in its left subtree (which is the in-order 
predecessor) or the minimum element in its right subtree (which is the in-order 
successor) and move its value into the node being deleted.  We then delete the 
node we copied the value from, which must have fewer than two non-leaf children. 
Because merely copying a value and does not violate any red-black properties, 
this reduces to the problem of deleting a node with at most one non-leaf child. 
Once we have solved the problem, the solution applies equally to the case where 
the node we originally wanted to delete has at most one non-leaf child as to the 
case just considered where it has two non-leaf children.*/
    V status=storage.remove(key);
    if(status!=null)
    return true;
    else
    return false;}
/*This returns the value associated with the parameter key. It returns nulll if
the key is not found or the dictionary is empty.*/
    @Override
    public V getValue(K key){
    return storage.get(key);}
/*This returns the key associated with the parameter value. It returns null if
the value is not found in the dictionary.*/
    @Override
    public K getKey(V value){
/*The non-modifying binary search tree operations like search take O(logn) time 
on red-black trees.  Searching a red-black tree for a specific key can be a 
recursive or iterative process. Even though many students found a recursive 
search easier, I felt more comfortable searching the tree iteratively. A red-black 
tree might be slower to search and insert into, but it has the very nice feature 
of the influx traversal, which essentially means you can iterate through the 
nodes of the tree in sorted order. To search the red-black tree, we use the same
algorithm binary search trees use and then go back to account for the rules of 
colors (red/black).Searching in a binary search tree begins by examining the root 
node. If the tree is null, the key we are searching for does not exist in the tree. 
Otherwise, if the key equals that of the root, the search is successful and 
returns the node. If the key is less than that of the root, we search the left 
subtree. Similarly, if the key is greater than that of the root, we search the 
right subtree. The process is repeated until the key is found or the remaining 
tree is null. If the searched key is not found before a null subtree is reached, 
then the item must not be present in the tree. Because in the worst case this 
algorithm must search from the root of the tree to the leaf farthest from the 
root, the search operation takes time proportional to the tree’s height. On 
average, binary search trees have O(logn) height. However, in the worst case, 
binary search trees can have O(n) height, when the unbalanced tree resembles a 
linked list, but in a red-black tree it’s O(logn).*/
    for (Entry<K, V> entry : storage.entrySet()){
    if (value.equals(entry.getValue())){
    return entry.getKey();}}
    return null;}
/*This returns the number of key/value pairs currently stored in the dictionary.*/
    @Override
    public int size(){
    return storage.size();}
/*This returns true if the dictionary is at max capacity.*/
    @Override
    public boolean isFull(){
    return false;}
/*This returns true if the dictionary is empty.*/
    @Override
    public boolean isEmpty(){
    return (this.size()<1);}
/*This returns the dictionary object to an empty state.*/
    @Override
    public void clear(){
    storage.clear();}
/*This returns an iterator of the keys in the dictionary, in ascending sorted
order. The iterator must be fail-fast.*/
    @Override
    public Iterator<K> keys(){
    return storage.keySet().iterator();}
/*This returns an iterator of the values in the dictionary. The order of the 
values must match the order of the keys. The iterator must be fail-fast.*/
    @Override
    public Iterator<V> values(){
/*A red-black tree is a particular implementation of a self-balancing binary 
search tree, and today it seems the most popular choice of implementation. 
Red-black tree was found to have the smallest amount of performance variations. 
Balancing the tree is needed to guarantee good performance, as otherwise the tree 
could degenerate into a list, for example if you insert keys which are already 
sorted. The advantage of search trees over hash tables is that you can traverse 
the tree efficiently in sort order. the tree height is always O(log n), where n 
is the number of node in the tree. The effect of this is that searching for a 
node in a balanced tree takes O(log n) time. Similarly, adding and removing also 
take O(log n). This is in contrast to unbalanced trees, where the worst-case 
complexity for searching/adding/removing is O(n) (which means that in the worst 
case they're not any better than linked lists). You should use balanced trees 
whenever you need to bound the worst-case performance of operations on the tree.*/
    return storage.values().iterator();}}
