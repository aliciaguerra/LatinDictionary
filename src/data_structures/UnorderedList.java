/*
 *Alicia Guerra
 *CS 310
 *Professor Steve Price
 *masc 1529
*/

package data_structures;

/*An unordered linked list used by PQ#4 via composition.*/
import java.util.Iterator;
import java.util.NoSuchElementException;

/*We are going to implement our unordered linked list.*/
public class UnorderedList<T> implements Iterable<T> {
    private Node<T> head = null;
    private Node<T> tail;
    private int currentSize;

/*This is our insertion method.*/
    public void insertFirst(T obj) {
/*We will create a new node.*/
        Node<T> newNode = new Node<T>(obj);
        if (isEmpty()) {
            head = tail = newNode;
        }
/*We will insert the node before the first.*/   
        else {
            newNode.next = tail;
            head = newNode;
        }
/*We will increment the count by one.*/
        currentSize++;

    }

/*The @Suppresswarnings annotation type allows Java programmers to disable compilation
warnings for a certain part of the program.*/    
    @SuppressWarnings("unchecked")
    public T find(T object)
    {
        for(T obj : this)
        {
            if (((Comparable<T>) object).compareTo(((T) obj)) == 0)
                return obj;
        }
        return null;
    }

    public void insertLast(T obj) {
        Node<T> newNode = new Node<T>(obj);

        if (isEmpty()) {
            head = tail = newNode;
        }
/*We will insert the new node after the last.*/    
        else {
            tail.next = newNode;
            tail = newNode;
        }
/*We're going to keep increasing the size by 1.*/
        currentSize++;

    }

    public T peekMin() {
        if (isEmpty()) {
            return null;
        }
        return _peekMin().data;
    }

    public T peekFirst() {
        return null;
    }

    public T removeMin() {

        if (isEmpty()) {
            return null;
        }

        Node<T> current = head, currentBest = head;
        Node<T> previous = null, previousBest = null;

        while (current != null) {
            if (((Comparable<T>) current.data).compareTo(currentBest.data) < 0) {
                previousBest = previous;
                currentBest = current;

            }
            previous = current;
            current = current.next;
        }

        if (head == tail) {
            head = tail = null; 
        }
        else if (currentBest == head) {
            head = head.next;
        }
        else if (currentBest == tail) {
            previousBest.next = null;
            tail = previousBest;
        }
        else {
            previousBest.next = currentBest.next;
        }

        currentSize--;

        return currentBest.data;
    }

/*The @Suppresswarnings annotation type allows Java programmers to disable compilation
warnings for a certain part of the program.*/
    @SuppressWarnings("unchecked")
    private Node<T> _peekMin() {
       
        
        Node<T> current = head, currentBest = head;

        while (current != null) {
            if (((Comparable<T>) current.data).compareTo(currentBest.data) < 0) {
                currentBest = current;

            }

            current = current.next;
        }
        return currentBest;
    }

/*The @Suppresswarnings annotation type allows Java programmers to disable compilation
warnings for a certain part of the program.*/
    @SuppressWarnings("unchecked")
    public T remove(T obj) {
/*We will be traversing the list until we find the object.*/
        Node<T> previous = null, current = head;

        while (current != null && ((Comparable<T>) obj).compareTo(current.data) != 0) {
            previous = current;
            current = current.next;
        }

        if (current == null)
            return null;
/*If there's only 1 item in the list, we come to this if statement.*/
        if (head == tail)
        {
            head = tail = null;
        }
        else if (previous == null) {
            head = head.next;
        }
        else if (current == tail) {
            previous.next = null;
            tail = previous;
        }
/*If none of the others apply, that means there are two or more nodes and it's in
the middle.*/
        else 
        {
            previous.next = current.next;

        }

        currentSize--;
        return current.data;

    }
/*Returns PQ to an empty state.*/
    public void clear() {
        head = null;
        currentSize = 0;

    }

    public T removeFirst() {
        if (isEmpty())
            return null;

        T temp = (T) head.data;

        if (head == tail) {
            head = tail = null;
        }
        else
            head = head.next;
/*We are going to decrement our size by 1.*/
        currentSize--;
        return temp;

    }

/*This is where we do our search.*/
    public Iterator<T> iterator() {
        return new ListIterator();

    }
/*Returns true if the PQ is empty, otherwise false.*/
    public boolean isEmpty() {
        return this.getCurrentSize() < 1;
    }

    public int getCurrentSize() {
        return currentSize;
    }

/*We use this for both ordered list and unordered list.*/
    public class ListIterator implements Iterator<T> {

        Node<T> iterPtr;

        public ListIterator() {
            iterPtr = head;
        }

        @Override
        public boolean hasNext() {
            return iterPtr != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T temp = iterPtr.data;
            iterPtr = iterPtr.next;
            return temp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

    }
    
    public class Node<T> {

        public Node(T obj) {
            this.data=obj;
        }
        public Node<T> next;
        public Node<T> previous;
        public T data;

    }
}