package kamel;


public class State {
	
	public int[] getFlourDistribution() {
		return flourDistribution;
	}

	public void setFlourDistribution(int[] flourDistribution) {
		//this.flourDistribution = flourDistribution;
		for(int i = 0; i < flourDistribution.length; i++) {
			this.flourDistribution[i] = flourDistribution[i];
		}
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
	
	private MyHashMap vorganger = new MyHashMap();
	private MyHashMap nachfolger = new MyHashMap();

	private int[] flourDistribution = new int[31];	//für 30 steps
	
	private int kamelIndex = 0;
	
	private int kamelLoad = 0;
	
	public State() {
		
	}

	public MyHashMap getNachfolger() {
		return nachfolger;
	}

	public void setNachfolger(MyHashMap nachfolger) {
		this.nachfolger = nachfolger;
	}

	public MyHashMap getVorganger() {
		return vorganger;
	}

	public void setVorganger(MyHashMap vorganger) {
		this.vorganger = vorganger;
	}
	
	

}
