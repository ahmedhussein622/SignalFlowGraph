package graphAlgorithms;

import java.util.ArrayList;

import flowGraph.Edge;
import flowGraph.Graph;
import flowGraph.Node;

public class IndividualLoops {
	

	private static ArrayList<Graph> result;
	private static ArrayList<ArrayList<Integer>> nonTouching;
	private static Graph graph;
	private static Node start;
	private static Node end;
	
	/**
	 * calculate forward paths of Graph graph
	 * @param graph to get its forward paths
	 * @return all forward paths of Graph graph
	 */
	public static Graph[] getLoops(Graph g,Node startNode, Node endNode) {
		
		graph = g;
		result = new ArrayList<>();
	 	
	    start = startNode;
	    end = endNode;
	    
	    g.startLooping();
	    Node n = g.getNextNode();
	    while(n != null) {
	    	
	    	n.setParent(null);
	    	n.setVisited(false);
	    	n = g.getNextNode();
	    }
		
	    
	    BFS(start);
	    removeDauplicate();
	    Graph[] r = new Graph[result.size()];
	    for(int i = 0 ; i < result.size(); i++)
	    	r[i] = result.get(i);
	    
	    return r;
	}
	
	
	public static Graph[] getLoops(Graph g,int startNode, int endNode) {
		return getLoops(g, g.getNode(startNode), g.getNode(endNode));
	}
	
	private static void BFS(Node n) {
		
		
		//visit every node that you can reach from this node
		n.setVisited(true);// to avoid loops in graph
		n.startLooping();
		
		Edge e = n.getNextEdge();
		Node next;
		while(e != null) {
			next = e.getTo();
			if(!next.isVisited()){
				next.setParent(e);
				BFS(next);
			}
			else {//loop has been found
				generatePath(n, next, e);
			}
			
			e = n.getNextEdge();
			
		}
		
		n.setVisited(false);//make it available for other searches
		
		
	}
	
	
	private static void generatePath(Node n,Node m,Edge e) {
		
		Node t = n;
		Graph g = new Graph();
		
		while(!t.equals(m)) {
			g.addNode(new Node(t.getID()));
			g.addEdge(new Edge(t.getParent().getWeight(),
					t.getParent().getFrom(), t.getParent().getTo()));
			t = t.getParent().getFrom();
		}
		
		g.addNode(new Node(t.getID()));
		g.addEdge(new Edge(e.getWeight(),e.getFrom(), e.getTo()));
		g.calculateGain();
		result.add(g);
		
	}
	
	
	private static void removeDauplicate() {
		ArrayList<Graph> tmp = new ArrayList<Graph>();
		
		for(int i = 0 ; i < result.size(); i++) {
			boolean t = true;;
			for(int j = i+1; j < result.size();j++) {
				if(result.get(i).equals(result.get(j))){
					t = false;
					break;
				}
			}
			if(t)
				tmp.add(result.get(i));
		}
		
		result = tmp;
		
	}
	
	
	
	
	public static ArrayList<ArrayList<Integer>> nonTouching() {
		nonTouching = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 2; i <= result.size(); i++){
				if(!Compination(i))
					break;
			}
		
		return nonTouching;
	}
	
	
	private static boolean Compination(int x) {
		
		int before = nonTouching.size();
		for(int i = 0 ;x <= result.size()-i; i++) {
			
			generateCompination(new ArrayList<Integer>(), i, x);
		}
		
		return nonTouching.size() != before;
	}
	
	
	
	private static void generateCompination(ArrayList<Integer> pre, int index, int r) {
			
			
			for(int i = 0; i < pre.size();i++) {
				if(result.get(index).touch(result.get(pre.get(i)))) {
						return;
					}
			}
			
			if(r == 1) {
				ArrayList<Integer> k = new ArrayList<>(pre);
				k.add(index);
				nonTouching.add(k);
				return;
			}
			
			if(index == result.size())
				return;
			
			pre.add(index);
			
			for(int i = index+1; i < result.size(); i++) {
				generateCompination(pre, i, r-1);
			}
			
			pre.remove((Integer)index);
			
		
	}
	
	
	
	
}
