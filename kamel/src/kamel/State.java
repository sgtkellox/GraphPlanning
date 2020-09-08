package kamel;

import java.util.HashMap;

public class State {
	
	public int[] getFlourDistribution() {
		return flourDistribution;
	}

	public void setFlourDistribution(int[] flourDistribution) {
		this.flourDistribution = flourDistribution;
	}

	public int getKamelIndex() {
		return kamelIndex;
	}

	public void setKamelIndex(int kamelIndex) {
		this.kamelIndex = kamelIndex;
	}

	public int getKamelLoad() {
		return kamelLoad;
	}

	public void setKamelLoad(int kamelLoad) {
		this.kamelLoad = kamelLoad;
	}
	
	private HashMap<State, String> vorganger = new HashMap<State, String>();
	private HashMap<State, String> nachfolger = new HashMap<State, String>();

	private int[] flourDistribution = new int[30];
	
	private int kamelIndex = 0;
	
	private int kamelLoad = 0;
	
	public State() {
		
	}

	public HashMap<State, String> getNachfolger() {
		return nachfolger;
	}

	public void setNachfolger(HashMap<State, String> nachfolger) {
		this.nachfolger = nachfolger;
	}

	public HashMap<State, String> getVorganger() {
		return vorganger;
	}

	public void setVorganger(HashMap<State, String> vorganger) {
		this.vorganger = vorganger;
	}
	
	

}
