/****************************
 * Julia Trinh
 * 40245980
 * Assignment 3
 * Due June 12th 2023
 ****************************/

// This class is a priority queue that prioritizes the lowest frequencies and characters with higher ASCII value

package ca.concordia.a3;

public class PrioQueue {

	// Create my own linked list
	private class ListNode{
		private Node node;
		private ListNode next;
		
		public ListNode(Node node, ListNode next) {
			this.node = node;
			this.next = next;
		}
	}
	
	private ListNode head;
	
	// Default constructor
	public PrioQueue() {
		head = null;
	}
	
	// Add a node depending on its priority
	public void add(Node node) {
		
		// Check if the list is empty
		if (head == null) head = new ListNode(node, null);
		
		// Check if the current head has a bigger frequency, or has the same freq but has a smaller ASCII code (both are leaves), 
		// or if the node's depth is smaller than the current head's depth (same freq as well), or if same freq, same depth but 
		// the head's leaf's characters have a smaller ASCII code than the node
		else if (head.node.freq > node.freq || 
				(head.node.freq == node.freq && (head.node.isLeaf() && node.isLeaf()) && (int)head.node.c<(int)node.c) ||
				(head.node.freq == node.freq && node.depth()<head.node.depth()) ||
				head.node.freq == node.freq && node.depth() == head.node.depth() && !node.isLeaf() &&
				(int)findRightLeafChar(head.node)<(int)findRightLeafChar(node))
			head = new ListNode(node, head);
		
		// Else if the node cannot be inserted at the head, check where it can be inserted
		else {
			ListNode nodeBefore = findPlacement(node);
			nodeBefore.next = new ListNode(node, nodeBefore.next);
		}
	}
	
	// Find where to insert the node
	public ListNode findPlacement (Node node) {
		ListNode previous = head;
		ListNode current = head.next;
		
		while (current != null) {
			
			// Check if the node is between a node with the same freq and a node with a different freq and are both leaves but the 
			// node has a smaller ASCII code, or the node before has a smaller depth, or they have the same depth but the node has a 
			// smaller ASCII code
			if (current.node.freq != node.freq && previous.node.freq == node.freq && 
					(((previous.node.isLeaf() && node.isLeaf()) && (int)previous.node.c>(int)node.c) ||
					node.depth()>previous.node.depth() || (node.depth() == previous.node.depth() && 
					(int)findRightLeafChar(previous.node)>(int)findRightLeafChar(node))))
				break;
			
			// Check if the node is in between a node with a freq smaller and a node with a freq bigger, or the node is before
			// a node with the same freq but smaller ASCII code (both are leaves), or if the node is before a node with a bigger depth
			// (same freq), the node is before one that has the same freq, same depth, but has a leaf with a smaller ASCII code
			else if (previous.node.freq < node.freq && (current.node.freq > node.freq || current.node == null) || 
					(current.node.freq == node.freq && (current.node.isLeaf() && node.isLeaf()) && (int)current.node.c<(int)node.c) ||
					(current.node.freq == node.freq && node.depth()<current.node.depth()) ||
					current.node.freq == node.freq && node.depth() == current.node.depth() && !node.isLeaf() &&
					(int)findRightLeafChar(current.node)<(int)findRightLeafChar(node)) 
				break;
			else {
				previous = current;
				current = current.next;
			}
		}
		return previous;
	}
	
	// Find the furthest right leaf of a node
	public char findRightLeafChar(Node node) {
		Node rightLeaf = node;
		while(rightLeaf.right != null) {
			rightLeaf = rightLeaf.right;
		}
		return rightLeaf.c;
	}
	
	// Return the size
	public int size() {
		ListNode current = head;
		int count = 0;
		while (current != null) {
			count++;
			current = current.next;
		}
		return count;
	}
	
	// Remove and return the first node
	public Node remove() {
		Node headNode = head.node;
		head = head.next;
		return headNode;
	}
	
	// Shows the head of the list
	public Node peek() {
		return head.node;
	}
	
}
