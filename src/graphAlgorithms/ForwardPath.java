package graphAlgorithms;

import java.util.ArrayList;

import flowGraph.Edge;
import flowGraph.Graph;
import flowGraph.Node;

public class ForwardPath {
	
	
	
	private static ArrayList<Graph> result;
	private static Graph graph;
	private static Node start;
	private static Node end;
	
	/**
	 * calculate forward paths of Graph graph
	 * @param graph to get its forward paths
	 * @return all forward paths of Graph graph
	 */
	public static Graph[] getForwardPaths(Graph g,Node startNode, Node endNode) {
		
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
	    Graph[] r = new Graph[result.size()];
	    for(int i = 0 ; i < result.size(); i++)
	    	r[i] = result.get(i);
	    
	    return r;
	}
	
	
	public static Graph[] getForwardPaths(Graph g,int startNode, int endNode) {
		return getForwardPaths(g, g.getNode(startNode), g.getNode(endNode));
	}
	
	private static void BFS(Node n) {
		
		if(n.equals(end)) {//Successful forward path
			generatePath(n);
			return;
		}
		
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
			e = n.getNextEdge();
			
		}
		
		n.setVisited(false);//make it available for other searches
		
	}
	
	
	private static void generatePath(Node n) {
		
		Node t = n;
		Graph g = new Graph();
		
		while(t.getParent() != null) {
			
			g.addNode(new Node(t.getID()));
			g.addEdge(new Edge(t.getParent().getWeight(),
					t.getParent().getFrom(), t.getParent().getTo()));
			
			t = t.getParent().getFrom();
		}
		g.addNode(new Node(t.getID()));
		g.calculateGain();
		result.add(g);
		
	}
	
	
	
	
}
