/****************************
 * Julia Trinh
 * 40245980
 * Assignment 3
 * Due June 12th 2023
 ****************************/

package ca.concordia.a3;

// Dictionary that stores the characters and their corresponding frequencies

public class FreqMap {

	char[] c = new char[35]; // size 35 to have enough space to store the alphabet and special characters
	int[] freq = new int[35];
	int curr = 0; // current cell that can be filled
	
	// Searches for the character and returns the index
	public int findChar(char ch) {
		for (int i=0; i<curr; i++) {
			if (ch == c[i]) return i;
		}
		return -1;
	}
	
	// Add a character and increase its frequency
	public void add(char ch) {
		int index = findChar(ch);
		if ( index == -1) {
			c[curr] = ch;
			freq[curr] = 1;
			curr++;
		}
		else {
			freq[index]++;
		}
	}
}
