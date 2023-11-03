/****************************
 * Julia Trinh
 * 40245980
 * Assignment 3
 * Due June 12th 2023
 ****************************/

package ca.concordia.a3;

public class Node{

	// Attributes
	Character c;
	int freq;
	Node left = null;
	Node right = null;
	
	// Constructor for a leaf
	public Node (Character c, int freq) {
		this.c = c;
		this.freq = freq;
	}
	
	// Constructor for a non-leaf
	public Node (Character c, int freq, Node left, Node right) {
		this.c = c;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
	
	// Check if the node is a leaf
	public boolean isLeaf() {
		return (left == null && right == null);
	}
	
	// Find the depth of a node
	public int depth() {
		int depth = 0;
		Node node = this;
		while(node.left != null && node.right != null) {
			node = node.right;
			depth++;
		}
		return depth;
	}
}
