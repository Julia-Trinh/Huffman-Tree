/****************************
 * Julia Trinh
 * 40245980
 * Assignment 3
 * Due June 12th 2023
 ****************************/

package ca.concordia.a3;

// Dictionary to store the characters and their corresponding codes

public class EncodeDictionary {

	char[] c = new char[35]; // size 35 to have enough space to store the alphabet and special characters
	String[] code = new String[35];
	int curr = 0; // current cell that can be filled
	
	// Add a character and increase its frequency
	public void add(char ch, String code) {
		c[curr] = ch;
		this.code[curr] = code;
		curr++;
	}
	
	// Searches for the character and returns the index
	public int findChar(char ch) {
		for (int i=0; i<curr; i++) {
			if (ch == c[i]) return i;
		}
		return -1;
	}
	
	public String returnCode(char ch) {
		int i = findChar(ch);
		return code[i];
	}
}
