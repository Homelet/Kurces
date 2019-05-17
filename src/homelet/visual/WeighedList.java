package homelet.visual;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class WeighedList<E> implements Iterable<E>{
	
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
		if(start.next == end){
			// if start.next is end means no item is in the list, thus, append the item to the end of the list
			append(item, weight);
		}else if(weight < start.next.weight){
			// if the weight is smaller to the start weight
			prepend(item, weight);
		}else if(weight > end.prev.weight){
			// if the weight is larger to the end weight
			append(item, weight);
		}else if(weight == start.next.weight){
			// append the item and scroll to the the last one
			Node n = scrollTillSameWeight(start.next);
			add(n, item, weight);
		}else if(weight == end.prev.weight){
			// append the item to the end of the list which guarantee is the last one
			append(item, weight);
		}else{
			// first find the weight and scroll to the end of that then add the node
			Node n = scrollTillSameWeight(searchPoz(start.next, 0, weight, 0, size));
			add(n, item, weight);
		}
	}
	
	public void remove(E item){
		for(Node node = start.next; node != end; node = node.next){
			if(node.item == item){
				remove(node);
				return;
			}
		}
	}
	
	public void remove(double weight){
		for(Node node = start.next; node != end; node = node.next){
			if(node.weight == weight){
				remove(node);
				return;
			}
		}
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
	
	private Node searchPoz(Node reference, int pos, double weight, int left, int right){
		// if left is right means the pos should be smaller than left and larger than right hence the reference node is
		// the true result
		if(left == right)
			return reference;
		// divide the section in half, and scroll the reference to the point
		int half = (right + left) / 2;
		reference = scroll(reference, half - pos);
		// if the reference is right then returns it, else search the left side and right side
		if(reference.weight == weight)
			return reference;
		else if(reference.weight > weight)
			return searchPoz(reference, half, weight, left, half - 1);
		else
			return searchPoz(reference, half, weight, half + 1, right);
	}
	
	private Node scroll(Node n, int step){
		if(step > 0){
			Node node;
			for(node = n; node.next != end && step != 0; step--){
				node = node.next;
			}
			return node;
		}else if(step < 0){
			Node node;
			for(node = n; node.prev != start && step != 0; step++){
				node = node.prev;
			}
			return node;
		}
		return n;
	}
	
	private Node scrollTillSameWeight(Node n){
		double expectedWeight = n.weight;
		Node   node;
		for(node = n.next; node.next != end && n.weight == expectedWeight; ){
			node = node.next;
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
	
	class Itr implements Iterator<E>{
		
		final int expectedModeCount;
		Node current;
		
		Itr(){
			this.expectedModeCount = modCount;
			this.current = start.next;
		}
		
		@Override
		public boolean hasNext(){
			checkForConcurrentModification();
			return current.next != end;
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
