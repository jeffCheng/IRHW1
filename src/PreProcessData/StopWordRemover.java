package PreProcessData;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import Classes.Path;

public class StopWordRemover {
	//you can add essential private methods or variables in 2017.
	private HashSet<String> hashset = null; //Using HashSet to storage stopwords
	private BufferedReader reader;
	private FileInputStream instream_collection;
	
	public StopWordRemover( ) throws IOException {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
		instream_collection = new FileInputStream(Path.StopwordDir);
		reader = new BufferedReader(new InputStreamReader(instream_collection)); 
		hashset=new HashSet<String>();
		String line;
        while ((line= reader.readLine()) != null){            	
            	hashset.add(line);//add stopwords
        }
        reader.close();
        instream_collection.close();
	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
		String stopword= new String(word);
		if(hashset.contains(stopword)){
			return true;
		}
			return false;
	}
}
