import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import flowGraph.FlowGraph;
import flowGraph.Graph;
import gui.GraphGUI;



public class Main {

	
	
	
	public static void main(String[] args) {
		
		FlowGraph g = new FlowGraph();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addNode(6);
		
		g.addEdge(1,6,5);
		g.addEdge(6,1,5);
		g.addEdge(1,2,5);
		g.addEdge(2,3,5);
		g.addEdge(3,2,5);
		
		g.addEdge(3,4,5);
		g.addEdge(4,3,5);
		
		
		Graph[] t = g.getAllLoops();
		System.out.println(t[0]+"\n");
		System.out.println(t[1]+"\n");
		System.out.println(t[2]+"\n");
		
		System.out.println(g.getNonTouchingLoops());
		
		
		
	}
}
