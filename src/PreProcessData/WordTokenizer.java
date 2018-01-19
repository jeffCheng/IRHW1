package PreProcessData;

import java.util.ArrayList;
import java.util.List;

/**
 * This is for INFSCI 2140 in 2017 fall
 * 
 * TextTokenizer can split a sequence of text into individual word tokens.
 */
public class WordTokenizer {
	//you can add essential private methods or variables
	private List<char[]> wordList;
	private int i =0; // index for wordList
	
	// YOU MUST IMPLEMENT THIS METHOD
	public WordTokenizer( char[] texts ) {
		// this constructor will tokenize the input texts (usually it is a char array for a whole document)
		wordList = new ArrayList<char[]>();
		String tokenText =new String(texts);
		//tokenText=tokenText.replace("'", "");
		tokenText=tokenText.replaceAll("[^a-zA-Z ]", "");//check whether is char
		String[] tokenWord=tokenText.split(" ");//split value
		for(int i = 0 ; i < tokenWord.length; i++){
			wordList.add(tokenWord[i].toCharArray());
		}
	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextWord() {
		// read and return the next word of the document
		// or return null if it is the end of the document
		if(i== wordList.size()){
			return null;
		}else{
			return wordList.get(i++);//from 0
		}			
	}
	
}
