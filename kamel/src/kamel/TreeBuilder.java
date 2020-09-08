package kamel;

import java.util.ArrayList;


public class TreeBuilder {
	
	ArrayList<State> states = new ArrayList<State>();
	
	
	
	
	public TreeBuilder() {
		
	}
	
	
	public void builtTree(State start) {
		
		
		
	}
	
	
	private State stepForeward(State start, int steps) {
		if(steps<=start.getKamelLoad()) {
			if(start.getKamelIndex()+steps<30) {
				State end = new State();
				end.setKamelIndex(start.getKamelIndex()+steps);
				end.setKamelLoad(start.getKamelLoad()-steps);
				end.setFlourDistribution(start.getFlourDistribution());
				end.getVorganger().put(start, "stepFore"+steps);
				start.getNachfolger().put(end,"stepFore"+steps);
				return end;
			}else {
				FailureState end = new FailureState();
				end.setMassage("Du läufst zu weit");
				return end;
			}
		}else {
			FailureState end = new FailureState();
			end.setMassage("Du hast nicht genug kahles");
			return end;
		}
	}
	
	private State stepBackwards(State start, int steps) {
		
			if(start.getKamelIndex()-steps>=0) {
				State end = new State();
				end.setKamelIndex(start.getKamelIndex()-steps);
				end.setKamelLoad(start.getKamelLoad());
				end.setFlourDistribution(start.getFlourDistribution());
				end.getVorganger().put(start, "stepBack"+steps);
				start.getNachfolger().put(end,"stepBack"+steps);
				return end;
			}else {
				FailureState end = new FailureState();
				end.setMassage("Du läufst zu weit nach hinten");
				return end;
			}
		
	}

	private State pickUp(State start, int count) {
		if(start.getKamelLoad()+count <= 30) {
			if(start.getFlourDistribution()[start.getKamelIndex()]>=count) {
				State end = new State();
				end.setKamelLoad(start.getKamelLoad()+count);
				end.getFlourDistribution()[start.getKamelIndex()] = end.getFlourDistribution()[start.getKamelIndex()] -count;
				end.setKamelIndex(start.getKamelIndex());
				end.getVorganger().put(start, "pickUp"+count);
				start.getNachfolger().put(end,"pickUp"+count);
				return end;
			}else {
				FailureState end = new FailureState();
				end.setMassage("so viel liegt hier nicht");
				return end;
			}
		}else {
			FailureState end = new FailureState();
			end.setMassage("Du hast schon genug");
			return end;
		}
	
	}
	
	private State putDown(State start, int count) {
		if(start.getKamelLoad()-count >= 0) {
			State end = new State();
			end.setKamelLoad(start.getKamelLoad()-count);
			end.getFlourDistribution()[start.getKamelIndex()] = end.getFlourDistribution()[start.getKamelIndex()] +count;
			end.setKamelIndex(start.getKamelIndex());
			end.getVorganger().put(start, "putDown"+count);
			start.getNachfolger().put(end,"putDown"+count);
			return end;
		}else {
			FailureState end = new FailureState();
			end.setMassage("so viel trägst du gar nicht");
			return end;
		}
	
	}

	
	
	
	


}
