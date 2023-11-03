/****************************
 * Julia Trinh
 * 40245980
 * Assignment 3
 * Due June 12th 2023
 ****************************/

/*
 *-----------------------------------------------------------------------------------------------------------
 * Q1) Theory 1:
 * 	What is the purpose of using a priority queue in Huffman coding, and how does it help to generate
 * 	an optimal code?
 * 
 * 	A priority queue helps with the order of the Huffman tree. For example, in Huffman coding, lower frequencies
 * 	have higher priorities, and in the case of this assignment, characters with the same frequencies are prioritized
 * 	by their ASCII code (in reverse order in this case). The character nodes with the highest priority are lower
 * 	in the tree, meaning that they are more further away from the root of the tree than those with lower priority.
 * 	This is because in Huffman coding, we add up the two lowest frequencies and reinsert the node, until only this root
 * 	remains, meaning that the higher frequencies will be added towards the end (closer to the root). By doing so, it 
 * 	helps to generate an optimal code since the queue is already sorted by priority beforehand and characters
 * 	that are used more frequently have shorter codes (since they are closer to the root) and characters that are 
 * 	used less frequently have longer codes.
 *
 *-----------------------------------------------------------------------------------------------------------
 * Q2) Theory 2:
 * 	How does the length of a Huffman code relate to the frequency of the corresponding symbol, and
 * 	why is this useful for data compression?
 * 
 * 	As said in the previous question, the shorter the Huffman code, the more frequent the symbol is in the text.
 * 	This is useful for data compression since characters with shorter codes represent fewer bits, meaning that 
 * 	characters used more frequently take up less bits than characters used less frequently. This makes it so that
 * 	the overall text doesn't use up more data space.
 * 	
 *-----------------------------------------------------------------------------------------------------------
 * Q3) Theory 3: 
 * 	What is the time complexity of building a Huffman code, and how can you optimize it?
 * 
 * 	There are many steps to be done while building a Huffman code. The first step is to read the text and to register
 * 	the frequency of each character. This has a time complexity of O(n) since the program has to go through each character
 * 	one by one. The second step is to build the Huffman tree with the help of a priority queue, which has a time complexity
 * 	of O(nlogn), since the program loops while adding the two first nodes and adding the result back into the queue, until
 * 	only one is left. The last step is to traverse the Huffman tree to get to each of its leaves in order to determine the
 * 	code of each character, which is O(n). Overall, it has a time complexity of O(nlogn). The code can be optimized if
 * 	I used Hashmaps instead of a dictionary type class that I implemented myself.
 * 	
 *-----------------------------------------------------------------------------------------------------------
 */

package ca.concordia.a3;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HCDriver {

	public static void main(String[] args) {
		
		// Get the file name and the action to be done
		String fileName;
		String action;
		if(args.length != 2) {
			System.out.println("Please enter the name of the file and the action you want to do.");
			return;
		}
		else {
			fileName = args[0];
			action = args[1].toLowerCase();
		}
		
		
		// Set up Scanner
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new FileInputStream(fileName));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		
		
		// Read the whole text
		String text = fileScanner.nextLine();
		while(fileScanner.hasNextLine()) {
			text += ("\r\n"+fileScanner.nextLine()); // add a CR and a NL when next line
		}
		text = text.toLowerCase(); // convert everything to lower case
		
		
		// Create the tree
		createHuffmanTree(text);
		
		
		// Action to execute
		Scanner keyboard = new Scanner(System.in);
		if (action.equals("encode")) {
			System.out.print("Please enter a line of text to encode: ");
			String lineText = keyboard.nextLine();
			
			String code = "";
			for (char c: lineText.toCharArray()) {
				code += huffmanCode.returnCode(c);
			}
			System.out.println(code);
		}
		else if (action.equals("decode")) {
			System.out.print("Please enter a sequence of zeros and ones to decode: ");
			String sequence = keyboard.nextLine();
			
			decode(sequence, root, 0);
		}
		else {
			System.out.println("Please write either encode or decode.");
			return;
		}
	}
	
	// Root of the tree
	public static Node root;
	
	// Huffman code dictionary
	public static EncodeDictionary huffmanCode = new EncodeDictionary();
	
	// Create a HuffmanTree
	public static void createHuffmanTree(String text) {
		
		// Create a frequency map and store each character and its frequency in it
		FreqMap fm = new FreqMap();
		for (char c: text.toCharArray()) fm.add(c);
		
		// Create a priority queue and store whatever was in the map in it
		PrioQueue pq = new PrioQueue();
		for (int i=0; fm.freq[i] != 0; i++) {
			pq.add(new Node(fm.c[i], fm.freq[i]));
		}
		
		// Loop until there is only the root left
		// Remove the two first nodes with the highest priority, add their frequencies together, and add it back to the queue
		while (pq.size() != 1) {
			Node left = pq.remove();
			Node right = pq.remove();
			int sum = left.freq + right.freq;
			pq.add(new Node(null, sum, left, right));
		}
		
		// Store the root
		root = pq.peek();
		
		// Encode the characters into the dictionary
		encode("", root, huffmanCode);
		
	}

	// Encode into a dictionary
	public static void encode(String code, Node root, EncodeDictionary huffmanCode) {
		// Base case
		if (root.isLeaf()) huffmanCode.add(root.c, code);
		else {
			encode(code+'0', root.left, huffmanCode);
			encode(code+'1', root.right, huffmanCode);
		}
	}
	
	// Decode
	public static void decode(String text, Node node, int index) {
		// Base case
		if (node.isLeaf()) {
			System.out.print(node.c);
			decode(text, root, index);
		}
		// Check if the index is smaller than the length of the text so that it is not out of bound
		else if (index < text.length()){
			node = (text.charAt(index) == '0') ? node.left : node.right;
			decode(text, node, index+1);
		}
	}
	
}
