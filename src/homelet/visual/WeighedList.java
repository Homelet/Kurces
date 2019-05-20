package homelet.visual;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

public final class WeighedList<E> implements Iterable<E>{
	
	public int size(){
		return size;
	}
	
	private Node start, end;
	private int size, modCount;
	
	/**
	 * a Weighted list is a list which only supports iteration through Iterator, it provides always provide a sorted
	 * list through the weight
	 */
	public WeighedList(){
		this.start = new Node(null, 0, null, null);
		this.end = new Node(null, 0, null, null);
		this.start.next = end;
		this.end.prev = start;
		this.size = 0;
	}
	
	public void prepend(E item){
		prepend(item, 0);
	}
	
	public void append(E item){
		append(item, Double.MAX_VALUE);
	}
	
	private void prepend(E item, double weight){
		Node node = new Node(item, weight, start.next, start);
		this.start.next = node;
		node.next.prev = node;
		this.size++;
		this.modCount++;
	}
	
	private void append(E item, double weight){
		Node node = new Node(item, weight, end, end.prev);
		this.end.prev = node;
		node.prev.next = node;
		this.size++;
		this.modCount++;
	}
	
	public void add(E item, double weight){
		if(start.next == end)
			append(item, weight);
		add(searchPoz(weight), item, weight);
	}
	
	public void remove(E item){
		for(Node node = start.next; node != end; node = node.next){
			if(node.item == item){
				remove(node);
				return;
			}
		}
	}
	
	public E remove(double weight){
		for(Node node = start.next; node != end; node = node.next){
			if(node.weight == weight){
				remove(node);
				return node.item;
			}
		}
		return null;
	}
	
	public Object[] removeAll(double weight){
		LinkedList<E> item = new LinkedList<>();
		for(Node node = start.next; node != end; node = node.next){
			if(node.weight > weight){
				break;
			}else if(node.weight == weight){
				remove(node);
				item.add(node.item);
			}
		}
		return item.toArray();
	}
	
	private void remove(Node node){
		node.prev.next = node.next;
		node.next.prev = node.prev;
		node.prev = node.next = null;
		this.size--;
		this.modCount++;
	}
	
	/*
	 * append the item to the next of operation node
	 */
	private void add(Node referenceNode, E item, double weight){
		Node node = new Node(item, weight, referenceNode.next, referenceNode);
		referenceNode.next = node;
		node.next.prev = node;
		this.size++;
		this.modCount++;
	}
	
	private Node searchPoz(double weight){
		Node node;
		for(node = start.next; node != end; node = node.next){
			if(node.weight > weight)
				break;
		}
		return node.prev;
	}
	
	private class Node{
		
		final E      item;
		final double weight;
		Node next;
		Node prev;
		
		Node(E item, double weight, Node next, Node prev){
			this.item = item;
			this.weight = weight;
			this.next = next;
			this.prev = prev;
		}
	}
	
	@Override
	public Iterator<E> iterator(){
		return new Itr();
	}
	
	private class Itr implements Iterator<E>{
		
		final int expectedModeCount;
		Node current;
		
		Itr(){
			this.expectedModeCount = modCount;
			this.current = start.next;
		}
		
		@Override
		public boolean hasNext(){
			checkForConcurrentModification();
			return current != end;
		}
		
		@Override
		public E next(){
			checkForConcurrentModification();
			E item = current.item;
			current = current.next;
			return item;
		}
		
		void checkForConcurrentModification(){
			if(expectedModeCount != modCount)
				throw new ConcurrentModificationException("The list has been Concurrently Modified!");
		}
	}
}
