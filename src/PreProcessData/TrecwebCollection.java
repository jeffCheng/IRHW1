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
public class TrecwebCollection implements DocumentCollection {
	//you can add essential private methods or variables
	private BufferedReader reader;
	private FileInputStream instream_collection;
	private static String hmtlTagRegex = "\\<[^>]*>";
	private Map<String, String> hmtlSpecialChars;
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrecwebCollection() throws IOException {
		// This constructor should open the file in Path.DataWebDir
		// and also should make preparation for function nextDocument()
		// you cannot load the whole corpus into memory here!!
		instream_collection = new FileInputStream(Path.DataWebDir);
		reader = new BufferedReader(new InputStreamReader(instream_collection)); 
		//Check whether has HTML special characters.
		hmtlSpecialChars = new HashMap<String,String>();
		hmtlSpecialChars.put("&gt;", "<");
		hmtlSpecialChars.put("&lt;", ">");
		hmtlSpecialChars.put("&amp;", "&");
		hmtlSpecialChars.put("&quot;", "\"");
		hmtlSpecialChars.put("&nbsp;", " ");
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NT: the returned content of the document should be cleaned, all html tags should be removed.
		// NTT: remember to close the file that you opened, when you do not use it any more
		
		String line= reader.readLine();
		while (line != null){
			Map<String,Object> map= new HashMap<String,Object>();
			String tempDOCNO="";
			StringBuilder sb = new StringBuilder();
			while(line.indexOf("</DOC>")==-1){
				//line = reader.readLine();
				int start = 0;
				if ((start = line.indexOf("<DOCNO>")) != -1) { // find <DOCNO> tag
					int end = line.indexOf('<', start+1);
					String str = line.substring(start+7, end).trim(); // get DOCNO
					tempDOCNO=str;
				}
				if (line.indexOf("</DOCHDR>") != -1) { // find </DOCHDR> tag
					line = reader.readLine();
					while (line.indexOf("</DOC>") == -1) { // Until find </DOC> tag 
						sb.append(line);
						sb.append(" "); 
						line = reader.readLine();
					}
					String content = sb.toString();
					//remove html tags 
					content = content.replaceAll(hmtlTagRegex, "");
					//check whether has HTML special characters.
					for(Map.Entry<String, String> entry : hmtlSpecialChars.entrySet()) {
					    if(content.indexOf(entry.getKey())!= -1){
					    	content = content.replaceAll(entry.getKey(), entry.getValue());
					    }
					}
					//replace non-word character and strip the blank
					content = content.replaceAll("[^\\w\\s]", " ").replaceAll("\\s+", " ");
					map.put(tempDOCNO, content.toCharArray());
					return map;
				}
				line = reader.readLine();
				if(line == null){
					break;
				}
			}
			line = reader.readLine();
		}
		reader.close();
		instream_collection.close();
		return null;
	}
}
