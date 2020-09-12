package kamel;

public class Main {
	
	public static void main(String[] agrs) {
		State startState = new State();
		startState.getFlourDistribution()[0] = 90;
		
		TreeBuilder treeBuilder = new TreeBuilder();
		treeBuilder.builtTree(startState);
		System.out.println(treeBuilder.states.size());
	}

}





