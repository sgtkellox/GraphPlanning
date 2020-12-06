package kamel;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TreeBuilder {

	ArrayList<State> states = new ArrayList<State>();
	int maxFlour = 90;
	ArrayList<State> endStates = new ArrayList<State>();

	public TreeBuilder() {

	}

	public void builtTree(State state, int steps) {

		if (isFinished(state)) {
			addEndState(state);
			return;
		}
		if(state instanceof FailureState) {
			return;
		}
		if (state.getFlourDistribution()[0] == maxFlour) {
			states.add(state);
			State newState = new State();
			newState = pickUp(state);
			newState.setNumberOfSteps(steps);
			states.add(newState);
			builtTree(newState, ++steps);
		} else {
			String action = state.getVorganger().getHashMap().get(state.getVorganger().getNeighbourState());
			if (action == null) {
				return;
			}
			action = action.split(" ")[0];

			for (int i = 1; i < 30; i++) {
				State newState = stepForeward(state, i);
				newState.setNumberOfSteps(steps);
				if (addStateToTree(newState)) {
					builtTree(newState, ++steps);
				}
			}

			for (int i = 1; i < 30; i++) {
				State newState = stepBackwards(state, i);
				newState.setNumberOfSteps(steps);
				if (addStateToTree(newState)) {
					builtTree(newState, ++steps);
				}
			}

			State newState = pickUp(state);
			newState.setNumberOfSteps(steps);
			if (addStateToTree(newState)) {
				builtTree(newState, ++steps);
			}

			newState = putDown(state);
			newState.setNumberOfSteps(steps);
			if (addStateToTree(newState)) {
				builtTree(newState, ++steps);
			}
		}

	}

	public void backTrace() {
		for (State s : endStates) {
			State bestVorgänger = s.getVorganger().chooseBest().getKey();
			System.out.println(s.getVorganger().chooseBest());

			while (!bestVorgänger.getVorganger().getHashMap().isEmpty()) {
				bestVorgänger = bestVorgänger.getVorganger().chooseBest().getKey();
				System.out.println(s.getVorganger().chooseBest());
			}

		}
	}

	private State stepForeward(State start, int steps) {
		if (steps <= start.getKamelLoad()) {
			if (start.getKamelIndex() + steps < 31) {
				State end = new State();
				end.setKamelIndex(start.getKamelIndex() + steps);
				end.setKamelLoad(start.getKamelLoad() - steps);
				end.setFlourDistribution(start.getFlourDistribution());
				end.getVorganger().add(start, "stepFore " + steps);
				start.getNachfolger().add(end, "stepFore " + steps);
				return end;
			} else {
				FailureState end = new FailureState();
				end.setMassage("Du läufst zu weit");
				return end;
			}
		} else {
			FailureState end = new FailureState();
			end.setMassage("Du hast nicht genug kahles");
			return end;
		}
	}

	private State stepBackwards(State start, int steps) {

		if (start.getKamelIndex() - steps >= 0) {
			State end = new State();
			end.setKamelIndex(start.getKamelIndex() - steps);
			end.setKamelLoad(start.getKamelLoad());
			end.setFlourDistribution(start.getFlourDistribution());
			end.getVorganger().add(start, "stepBack " + steps);
			start.getNachfolger().add(end, "stepBack " + steps);
			return end;
		} else {
			FailureState end = new FailureState();
			end.setMassage("Du läufst zu weit nach hinten");
			return end;
		}

	}

	private State pickUp(State start) {
		/*
		 * if(start.getKamelLoad()+count <= 30) {
		 * if(start.getFlourDistribution()[start.getKamelIndex()]>=count) { State end =
		 * new State(); end.setKamelLoad(start.getKamelLoad()+count);
		 * end.getFlourDistribution()[start.getKamelIndex()] =
		 * end.getFlourDistribution()[start.getKamelIndex()] -count;
		 * end.setKamelIndex(start.getKamelIndex()); end.getVorganger().put(start,
		 * "pickUp"+count); start.getNachfolger().put(end,"pickUp"+count); return end;
		 * }else { FailureState end = new FailureState();
		 * end.setMassage("so viel liegt hier nicht"); return end; } }else {
		 * FailureState end = new FailureState(); end.setMassage("Du hast schon genug");
		 * return end; }
		 */

		// soviel wie möglich aufnehmen
		if (start.getFlourDistribution()[start.getKamelIndex()] == 0) {
			FailureState end = new FailureState();
			end.setMassage("hier liegt nichts mehr");
			return end;
		}

		State end = new State();
		int count;

		if (start.getFlourDistribution()[start.getKamelIndex()] < 30 - start.getKamelLoad()) {
			count = start.getFlourDistribution()[start.getKamelIndex()];
			end.setKamelLoad(start.getKamelLoad() + count);
			end.setFlourDistribution(start.getFlourDistribution());
			end.getFlourDistribution()[start.getKamelIndex()] = 0;

		} else {
			count = (30 - start.getKamelLoad());
			end.setKamelLoad(30);
			end.setFlourDistribution(start.getFlourDistribution());
			end.getFlourDistribution()[start.getKamelIndex()] = end.getFlourDistribution()[start.getKamelIndex()]
					- count;
		}
		end.setKamelIndex(start.getKamelIndex());
		end.getVorganger().add(start, "pickUp " + count);
		start.getNachfolger().add(end, "pickUp " + count);
		return end;

	}

	private State putDown(State start) {
		/*
		 * if(start.getKamelLoad()-count >= 0) { State end = new State();
		 * end.setKamelLoad(start.getKamelLoad()-count);
		 * end.getFlourDistribution()[start.getKamelIndex()] =
		 * end.getFlourDistribution()[start.getKamelIndex()] +count;
		 * end.setKamelIndex(start.getKamelIndex()); end.getVorganger().put(start,
		 * "putDown"+count); start.getNachfolger().put(end,"putDown"+count); return end;
		 * }else { FailureState end = new FailureState();
		 * end.setMassage("so viel trägst du gar nicht"); return end; }
		 * 
		 * }
		 */

		// alles ablegen
		if (start.getKamelLoad() <= 0) {
			FailureState end = new FailureState();
			end.setMassage("Du hast nichts mehr dabei");
			return end;
		}
		State end = new State();
		end.setKamelIndex(start.getKamelIndex());
		end.setKamelLoad(0);
		end.setFlourDistribution(start.getFlourDistribution());
		end.getFlourDistribution()[end.getKamelIndex()] = start.getFlourDistribution()[start.getKamelIndex()]
				+ start.getKamelLoad();
		end.getVorganger().add(start, "putDown " + start.getKamelLoad());
		start.getNachfolger().add(end, "putDown " + start.getKamelLoad());
		return end;
	}

	private int loadBehindKamel(State state) {
		for (int i = 0; i < state.getKamelIndex(); i++) {
			if (state.getFlourDistribution()[i] != 0) {
				return i;
			}
		}
		return -1;
	}

	private int loadBeforeKamel(State state) {
		for (int i = state.getKamelIndex() + 1; i <= 30; i++) {
			if (state.getFlourDistribution()[i] != 0) {
				return i;
			}
		}
		return -1;
	}

	private boolean isFinished(State state) {
		if (state.getKamelLoad() != 0|| state instanceof FailureState ) {
			return false;
		}
		for (int i = 0; i <= 30; i++) {
			if (state.getFlourDistribution()[i] != 0) {
				return false;
			}
		}
		return true;
	}

	private Method suggestNextAction(String action, State state) {

		if (action.equals("pickUp")) {
			int idx = loadBeforeKamel(state);
			if (idx != -1) {
				try {
					Method stepForeward = TreeBuilder.class.getMethod("stepForeward", State.class, int.class);
					return stepForeward;

				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private boolean addStateToTree(State newState) {
		for (State s : states) {
			if (s.equals(newState)) {
				if (s.isBetter(newState)) {
					return false;
				} else {
					states.remove(s);
					states.add(newState);
					return true;
				}
			}
		}
		states.add(newState);
		return true;
	}

	private void addEndState(State s) {
		
		if(endStates.isEmpty()) {
			endStates.add(s);
		}

		else if(endStates.get(0).getFlourDistribution()[30] < s.getFlourDistribution()[30]) {
			endStates.clear();
			endStates.add(s);
		}

	}

}

//public void builtTree(State state) {
//
//	if (isFinished(state)) {
//		return;
//	}
//	if (state.getFlourDistribution()[0] == maxFlour) {
//		states.add(state);
//		State newState = new State();
//		newState = pickUp(state);
//		states.add(newState);
//		builtTree(newState);
//	}
//
//	String action = state.getVorganger().getHashMap().get(state.getVorganger().getNeighbourState());
//	if (action == null) {
//		return;
//	}
//	action = action.split(" ")[0];
//
//	if (action.equals("pickUp")) {
//		/*
//		 * if(state.getKamelLoad() <= 30 - state.getKamelIndex()) { return; }
//		 */
//		int idx = loadBeforeKamel(state);
//		if (idx != -1) {
//			State newState = new State();
//			newState = stepForeward(state, idx - state.getKamelIndex());
//			states.add(newState);
//			builtTree(newState);
//		} else {
//			int flour = state.getFlourDistribution()[state.getKamelIndex()] + state.getKamelLoad();
//			for (int i = 1; i < 31 - state.getKamelIndex(); i++) {
//				if (flour <= 30 && state.getKamelIndex() + i == 30) {
//					State newState = new State();
//					newState = stepForeward(state, i);
//					states.add(newState);
//					builtTree(newState);
//				}
//				if (flour > 30 && flour <= 60) {
//					if ((2 * (state.getKamelLoad() - i)) % 30 == 0) {
//						State newState = new State();
//						newState = stepForeward(state, i);
//						states.add(newState);
//						builtTree(newState);
//					}
//				}
//				if (flour > 60) {
//					if ((3 * (state.getKamelLoad() - i)) % 30 == 0) {
//						State newState = new State();
//						newState = stepForeward(state, i);
//						states.add(newState);
//						builtTree(newState);
//					}
//				}
//			}
//		}
//	}
//
//	if (action.equals("stepFore")) {
//
//		State newState = new State();
//		newState = putDown(state);
//		states.add(newState);
//		builtTree(newState);
//	}
//
//	if (action.equals("putDown")) {
//		if (loadBehindKamel(state) != -1) {
//			int idx = loadBehindKamel(state);
//			State newState = new State();
//			newState = stepBackwards(state, state.getKamelIndex() - idx);
//			states.add(newState);
//			builtTree(newState);
//		} else {
//			State newState = new State();
//			newState = pickUp(state);
//			states.add(newState);
//			builtTree(newState);
//		}
//	}
//	if (action.equals("stepBack")) {
//		State newState = new State();
//		newState = pickUp(state);
//		states.add(newState);
//		builtTree(newState);
//	}
//}
