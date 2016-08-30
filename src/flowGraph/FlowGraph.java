package flowGraph;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;

import graphAlgorithms.DeltaCalculator;
import graphAlgorithms.ForwardPath;
import graphAlgorithms.IndividualLoops;

public class FlowGraph extends Graph{
	
	
	private Graph[] forwadPaths;
	private Graph[] allLoops;
	private ArrayList<ArrayList<Integer>> nonTouchedLoops;
	private double forwardPathsDelta[];
	private double delta;
	private double overAllTransferFunction;
	public FlowGraph(int size) {
		super(size);
	}
	
	public FlowGraph() {
		
	}
	
	
	
	public Graph[] getForwardPaths() {
		return forwadPaths;
	}
	
	
	public void update() {
		allLoops = IndividualLoops.getLoops(this, 1, nodes.size());
		forwadPaths = ForwardPath.getForwardPaths(this, 1, nodes.size());
		forwardPathsDelta = new double[forwadPaths.length];
		nonTouchedLoops = IndividualLoops.nonTouching();
		
		for(int i = 0 ; i < forwadPaths.length; i++) {
			forwardPathsDelta[i] = DeltaCalculator.getDelta(forwadPaths[i],allLoops, nonTouchedLoops);
		}
		
		updateDelta();
		updateOverAllTransferFunction();
	}
	
	public Graph[] getAllLoops() {
		
		return allLoops;
	}
	

	public ArrayList<ArrayList<Integer>> getNonTouchingLoops() {
		return nonTouchedLoops;
	}
	
	
	public double getDelta() {
		return delta;
	}
	
	
	public double[] getForwardPathDelta() {
		return forwardPathsDelta;
	}
	
	
	public double getOverAllTransferFunction() {
		return overAllTransferFunction;
	}
	
	private void updateDelta() {
		
		delta = 1;
		for(int i = 0 ; i < allLoops.length; i++) {
			delta -= allLoops[i].getGain();
		}
		
		
		int s,g;
		ArrayList<Integer> loop;
		
		for(int i = 0; i < nonTouchedLoops.size(); i++) {
			loop = nonTouchedLoops.get(i);
			s = loop.size();
			g = 1;
			for(int j = 0; j < s; j++) {
				g *= allLoops[loop.get(j)].getGain();
			}
			delta += (Math.pow(-1, s))*g;
			
			
		}//end of for

		
	}
	
	
	private void updateOverAllTransferFunction() {
		
		overAllTransferFunction = 0 ;
		for(int i = 0; i < forwadPaths.length; i++) {
			overAllTransferFunction+= forwadPaths[i].getGain()*forwardPathsDelta[i];
		}
		
		overAllTransferFunction /= delta;
	}

	
	
	
	
	
}
