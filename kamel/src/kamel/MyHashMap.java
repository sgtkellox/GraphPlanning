package kamel;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class MyHashMap {

	private State neighbourState;

	private HashMap<State, String> hashmap = new HashMap<State, String>();

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

	public Map.Entry<State, String> chooseBest() {
		State best = new State();
		best.setNumberOfSteps(50);
		for (State s : hashmap.keySet()) {
			if (s.isBetter(best)) {
				best = s;
			}
		}

		return  new AbstractMap.SimpleImmutableEntry<>(best, hashmap.get(best));

	}

}
