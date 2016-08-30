package flowGraph;

import java.util.ArrayList;

import javax.print.attribute.standard.Sides;

public class Graph {
	
	protected ArrayList<Node> nodes;
	protected ArrayList<Edge> edges;
	
	// gain is used only when you use
	// the graph as a loop or a forward path
	// since a loop or a path is a subgraph of the main graph
	protected double gain;
	
	protected int currentIndex;
	
	public Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		gain = 0;
		currentIndex = 0;
	}

	public Graph(int size) {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		gain = 0;
		currentIndex = 0;
		
		for(int i = 1 ; i <= size; i++)
			nodes.add(new Node(i));
	}

	/**
	 * add a node to nodes
	 * @param n
	 */
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	
	
	
	/**
	 * in order to create graph easily, just 
	 * add all nodes first and then call this function
	 * to generate all edges
	 */
	public void generateEdges() {
		
		edges = new ArrayList<>();
		
		Node tmp;
		Edge e;
		for(int  i = 0; i < nodes.size(); i++) {//loop throw all nodes of the graph
			tmp = nodes.get(i);
			tmp.startLooping();
			
			e = tmp.getNextEdge();
			while(e != null) {// get every each in Node tmp and add it to edges
				edges.add(e);
				e = tmp.getNextEdge();
			}
			
		}//end of for
		
	}//end of generateEdges
	
	
	/**
	 * check if this graph touches graph g
	 * in there words , they have common nodes
	 * @param g graph to check if it touches this graph
	 * @return true if both graph touch each other
	 * 		   else it returns false 
	 */
	public boolean touch(Graph g) {
		
		for(int i = 0; i < nodes.size(); i++) {//loop throw each node in this graph
			if(g.nodes.contains(this.nodes.get(i)))//check if graph g contains the same node
				return true;
		}
		
		return false;
	}
	
	
	public void addEdge(int from , int to , double weight) {
		
		Node f  = nodes.get(nodes.indexOf(new Node(from)));
		Node t  = nodes.get(nodes.indexOf(new Node(to)));
		if(f == null || t == null)
			return;
		Edge e = new Edge(weight, f, t);
		for(int i = 0  ; i < f.getEdges().size(); i++)
			if(f.getEdges().contains(e))
				return;
		f.addEdge(e);
		edges.add(e);
	}
	
	public void removeEdge(int from , int to , int weight) {
		Node f  = nodes.get(nodes.indexOf(new Node(from)));
		Node t  = nodes.get(nodes.indexOf(new Node(to)));
		
		if(f == null || t == null)
			return;
		
		Edge e  = new Edge(weight, f, t);
		for(int i = 0; i < edges.size(); i++) {
			if(edges.get(i).equals(e)){
				edges.remove(i);
				break;
			}
		}
		
		ArrayList<Edge> edg = f.getEdges();
		for(int i = 0; i < edg.size(); i++) {
			if(edg.get(i).equals(e)){
				edg.remove(i);
				break;
			}
		}
		
		edg = t.getEdges();
		for(int i = 0; i < edg.size(); i++) {
			if(edg.get(i).equals(e)){
				edg.remove(i);
				break;
			}
		}
		
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public void calculateGain() {
		gain = 1;
		for(int i = 0 ;i < edges.size(); i++) {
			gain *= edges.get(i).getWeight();
		}
	}
	
	
	public void addNode(int n) {
		if(nodes.contains(new Node(n)))
			return;
		nodes.add(new Node(n));
	}
	
	public void startLooping() {
		currentIndex = 0;
	}
	
	public Node getNextNode() {
		
		if(currentIndex == nodes.size())
			return null;
		return nodes.get(currentIndex++);

	}
	
	
// setters and getters
//////////////////////////////////////////////////////////////////////////////////////////////////
	public int nodesNumber() {
		return nodes.size();
	}
	public int branchesNumber() {
		return edges.size();
	}
	
	public Node getNode(int n) {
		int x = nodes.indexOf(new Node(n));
		if(x == -1)
			return null;
		return nodes.get(x);
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}


	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}


	public ArrayList<Edge> getEdges() {
		return edges;
	}


	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}


	public double getGain() {
		return gain;
	}


	public void setGain(double gain) {
		this.gain = gain;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public String toString() {
		
		if(nodes.size() == 0)
			return "graph is empty";
		
		String r = "gain : "+gain+" | Nodes : [";

		int i;
	
		for(i = 0; i < nodes.size()-1; i++) {
			r += nodes.get(i)+",";
		}
		r += nodes.get(i)+"]";
		
		if(edges.size() == 0 )
			return r;
		
		r += "\nedges :[";
		for(i = 0; i < edges.size()-1; i++) {
			r += edges.get(i).toString()+",";
		}
		r += edges.get(i)+"]";
		return r;
	}

	public String getNodesString() {
		String r = "";
		for(int i = nodes.size()-1; i > -1; i--) {
			r += nodes.get(i).getID()+",";
		}
		
		r += nodes.get(nodes.size()-1).getID();
		
		return r;
	}
	
	public String getPathString() {
		String r = "";
		for(int i = nodes.size()-1; i > 0; i--) {
			r += nodes.get(i).getID()+",";
		}
		
		r += nodes.get(0).getID();
		
		return r;
	}
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		Graph g = (Graph)obj;
		
		if(g.nodes.size() != this.nodes.size() || edges.size() != g.edges.size())
			return false;
		
		for(int i = 0 ;i < nodes.size() ;i++)
			if(!nodes.contains(g.nodes.get(i)))
				return false;
			
		for(int i = 0 ;i < edges.size() ;i++)
			if(!edges.contains(g.edges.get(i)))
				return false;
		
		
		return true;
	}
	
	
}
