package textgen;

import java.util.AbstractList;
 

/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		
		head = new LLNode<E>(null) ;
		tail = new LLNode<E>(null) ; 
		
		head.data = null ; //sentinel nodes
		tail.data = null ; 
		
		head.next = tail ;
		tail.prev = head ; 
		
		size = 0 ; //init size of the list to zero
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if(element == null)
			throw new NullPointerException("Cannot add 'null' as an element!") ;
		
		LLNode<E> newNode = new LLNode<E>(element) ;
		
		newNode.next = tail ; 
		newNode.prev = tail.prev ; 
		
		tail.prev.next = newNode ; 
	    tail.prev = newNode ; 
	    
	    size++ ;
		
		return true ;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		
		if(index < 0 || index > size-1)
			throw new IndexOutOfBoundsException("This isn't a valid index!") ; 
		
		LLNode<E> curNode = head.next ;
		
		for(int i = 0 ; i < size ; i++) {
			if( i == index && curNode.data != null)
				return curNode.data ;
			
			curNode = curNode.next ;
		}
		
		return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) //add e
	{

		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException("This isn't a valid index!") ; 
		
		if(element == null)
			throw new NullPointerException("Cannot add 'null' as an element!") ;
		
		if(index == size) { //just add the element at the end 
			add(element) ;
			return ;
		}
		
		LLNode<E> curNode = head.next ;
		
		for(int i = 0 ; i < size ; i++) {
			if( i == index) {
				
				LLNode<E> newNode = new LLNode<E>(element) ;
				curNode.prev.next = newNode ; 
				newNode.next = curNode ; 
				newNode.prev = curNode.prev ; 
				
				curNode.prev = newNode ;
				break ; 
				
			}
						
			curNode = curNode.next ;
		}
		
		size++ ; 
		
		
	}


	/** Return the size of the list */
	public int size() 
	{
		
		return size ;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if(index < 0 || index > size-1)
			throw new IndexOutOfBoundsException("This isn't a valid index!") ; 
		
		LLNode<E> curNode = head.next ;
		
		for(int i = 0 ; i < size ; i++) {
			if( i == index) {
				//remove curNode 
				curNode.prev.next = curNode.next ;
			    
				curNode.next.prev = curNode.prev ; 
				
				break ; 
				
			}
						
			curNode = curNode.next ;
		}
		
		size-- ; 
		
		return  curNode.data ; 
	}

	
	
	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		E oldElement = null ; 
		
		if(index < 0 || index > size-1)
			throw new IndexOutOfBoundsException("This isn't a valid index!") ; 
		
		if(element == null)
			throw new NullPointerException("Cannot add 'null' as an element!") ;
		
		LLNode<E> curNode = head.next ;
		
		for(int i = 0 ; i < size ; i++) {
			if( i == index) {
				//update curNode
				oldElement = curNode.data ;
				curNode.data = element ; 
				
				break ; 		
			}
						
			curNode = curNode.next ;
		}
			
		return oldElement ; 
	}   
	}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	/*
	public LLNode(E e, LLNode prevNode) {
		this(e) ;
		prev
		
	}*/

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
