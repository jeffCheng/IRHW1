package PreProcessData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import Classes.Path;

/**
 * This is for INFSCI 2140 in 2017
 *
 */
public class TrectextCollection implements DocumentCollection {
	//you can add essential private methods or variables
	private BufferedReader reader;
	private FileInputStream instream_collection;
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrectextCollection() throws IOException {
		// This constructor should open the file in Path.DataTextDir
		// and also should make preparation for function nextDocument()
		// you cannot load the whole corpus into memory here!!
		instream_collection = new FileInputStream(Path.DataTextDir);
		reader = new BufferedReader(new InputStreamReader(instream_collection)); 
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NTT: remember to close the file that you opened, when you do not use it any more
		String line= reader.readLine();
		while (line != null){
			Map<String,Object> map= new HashMap<String,Object>();
			String tempDOCNO="";
			StringBuilder sb = new StringBuilder();
			while(line.indexOf("</DOC>")==-1){
				int start = 0;
				if ((start = line.indexOf("<DOCNO>")) != -1) { // find <DOCNO> tag
					int end = line.indexOf('<', start+1);
					String str = line.substring(start+7, end).trim(); // get DOCNO
					tempDOCNO=str;
				}
				if (line.indexOf("<TEXT>") != -1) { // find <TEXT> tag
					line = reader.readLine();
					while (line.indexOf("</TEXT>") == -1) { // Until find </TEXT> tag 
						sb.append(line);// append the content between <TEXT> and </TEXT>
						sb.append(" "); // line add blank 
						line = reader.readLine();
					}
				}
				line = reader.readLine();
			}
			map.put(tempDOCNO, sb.toString().toCharArray()); //change to char[]
			line = reader.readLine();
			return map;
		}
		reader.close();
		instream_collection.close();
		return null;
	}
}
