package kamel;

import java.util.HashMap;

public class MyHashMap {
	
	private State neighbourState;
	private HashMap<State, String> hashmap= new HashMap<State, String>();
	
	public MyHashMap() {

	}
	
	public State getNeighbourState() {
		return neighbourState;
	}
	
	public HashMap<State, String> getHashMap() {
		return hashmap;
	}
	
	public void setNeighbourState(State state) {
		neighbourState = state;
	}
	
	public void setHashMap(HashMap<State, String> newHashMap) {
		hashmap = newHashMap;
	}
	
	public void add(State state, String message) {
		neighbourState = state;
		hashmap.put(state, message);
	}
}
