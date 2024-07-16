package myutil;

import java.util.Map;

public class MyMap {

	Map<String, Object> wordMap;

	// Constructor Injection으로 주입받는다
	public MyMap(Map<String, Object> wordMap) {
		super();
		this.wordMap = wordMap;
	}

	public Map<String, Object> getWordMap() {
		return wordMap;
	}

	public void setWordMap(Map<String, Object> wordMap) {
		this.wordMap = wordMap;
	}	
	
}
