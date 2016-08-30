package graphAlgorithms;

import java.util.ArrayList;

import flowGraph.*;



public class DeltaCalculator {
	
	
	
	private static Graph graph;
	private static Graph[] loops;
	private static ArrayList<ArrayList<Integer>> allLoops;
	
	public static double getDelta(Graph path,Graph[] l,ArrayList<ArrayList<Integer>> notTouchedLoops) {
		
		graph = path;
		loops = l;
		allLoops = notTouchedLoops;
		doSomeThing();
		
		return delta;
	}
	
		
	private static double delta;
	
	
	
	private static void doSomeThing() {
		
		delta = 1;
		boolean t[] = new boolean[loops.length];
		for(int i = 0 ; i < loops.length; i++) {
			if(!graph.touch(loops[i])) {
				t[i] = true;
				delta -= loops[i].getGain();
			}
		}
		
		
		int s,g;
		ArrayList<Integer> loop;
		boolean x;
		for(int i = 0; i < allLoops.size(); i++) {
			loop = allLoops.get(i);
			s = loop.size();
			x = false;
			g = 1;
			for(int j = 0; j < s; j++) {
				if(!t[loop.get(j)]) {
					x = true;
					break;
				}
				g *= loops[loop.get(j)].getGain();
			}
			if(x)
				continue;
			delta += (Math.pow(-1, s))*g;
			
			
		}//end of for
		
		
	}
	
	
	
	
	
	
}//end of DeltaCalculator
