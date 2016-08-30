package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.sun.corba.se.impl.orbutil.graph.Node;


import flowGraph.*;
import flowGraph.*;
public class GraphGUI extends JPanel {
	
	
	
	FlowGraph flowGraph;

	
	@Override
	protected void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		setBackground(Color.white);
		if(flowGraph == null|| flowGraph.nodesNumber() == 0)
			return;
		
		int width = getWidth();
		int heigt = getHeight();
		
		
		
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		int NodeNum = flowGraph.nodesNumber();
		int IncrementX = width/(NodeNum+1);
		
		int x = IncrementX-15;
		int y = heigt/2-15;
		
		g.setStroke(new BasicStroke(2));
		ArrayList<flowGraph.Node> n = flowGraph.getNodes();
		
		for(int i = 0;i < n.size(); i++) {
			n.get(i).getGUI().set(x, y);
			x+= IncrementX;
		}
		
		ArrayList<Edge> e = flowGraph.getEdges();
		for(int i = 0 ;i < e.size() ;i++)
			drawEdge(g, e.get(i),Color.black);
		

		for(int i = 0;i < n.size(); i++) {
			n.get(i).getGUI().paint(g,Color.red);
			x+= IncrementX;
		}
		
		highLightGraph(g);
	}
	
	
	private void drawEdge(Graphics2D g1, Edge e,Color edgeColor) {
			final Graphics2D g = (Graphics2D)g1.create();
			
			int diameter,h;
			h = diameter = 0;
			double angle = 0;
			Point p1,p2;
			String  s = e.getWeight()+"";
			
			try {
			    p1 = new Point(e.getFrom().getGUI().centerX, e.getFrom().getGUI().centerY);
			 	p2 = new Point(e.getTo().getGUI().centerX, e.getTo().getGUI().centerY);
			 	diameter = (int) Math.round(p1.distance(p2));
			 	 
			 	if(p1.x == p2.x) {
			 		g.setColor(edgeColor);
			 		g.drawArc(p1.x-22, p1.y-45, 45, 45, 0, 360);
			 		g.fill(new Polygon(new int[] {p1.x+8,p1.x-4,p1.x-4}, new int[] {p1.y-45,p1.y-40,p1.y-50}, 3));
			 		g.setColor(Color.blue);
				    g.drawString(s, p1.x+s.length(), p1.y-47);			 
			 	}
			 	
			 	else if(e.getTo().getID()-e.getFrom().getID() == 1) {
			 		g.setColor(edgeColor);
			 		g.drawLine(p1.x, p1.y, p2.x, p2.y);
			 		diameter/=2;
			 		diameter+=p1.x;
			 		g.fill(new Polygon(new int[] {diameter+10,diameter-2,diameter-2}, new int[] {p1.y,p1.y-8,p1.y+8}, 3));
			 		g.setColor(Color.blue);
			 		g.drawString(s, diameter+s.length(), p1.y-10);
			 	}
			 	
			 	else{
				    angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
			        
			        g.translate(p1.x, p1.y);
			        g.rotate(angle);
			        
			        h = diameter*2/3;
			        h+= Math.abs(e.getFrom().getID()-e.getTo().getID())*3;
			        if(h > getHeight()/3)
			        	h  = (h*2)/3;
			        
			        g.setColor(edgeColor);
			        g.drawArc(0, -h/2, diameter, h, 0, 180);
			        h /= 2;
			        int in = 8;
			        g.fill(new Polygon(new int[] {diameter/2+in,diameter/2,diameter/2}, new int[] {-h,-h+in,-h-in}, 3));
			       
			       
			        if(angle <= 0){
			        	g.setColor(Color.blue);
			        	g.drawString(s, diameter/2+s.length(), -h-5);
			        }
			 	}
			 	
		    } finally {
		         g.dispose();
		    }
			
			final Graphics2D g2 = (Graphics2D)g1.create();
			try{
			   if(angle > 0) {
				   
		        	 g2.translate(p2.x, p2.y);
		        	 g2.setColor(Color.blue);
				     g2.drawString(s, diameter/2+s.length(), h+15);
		        }
			}
		 	
	        finally {
	        	g2.dispose();
	        }
	        
	}

	
	
	boolean highLight;
    Graph toBeHighlted[];
	
	private void highLightGraph(Graphics2D g) {
		
		if(!highLight || toBeHighlted == null)
			return;
		Color c;
		for(int i = 0; i < toBeHighlted.length; i++){
			ArrayList<Edge> tmp = toBeHighlted[i].getEdges();
			Edge e;
			flowGraph.Node n1,n2;
			for(int j = 0 ;j < tmp.size() ;j++) {
				e = tmp.get(j);
				n1 = flowGraph.getNode(e.getFrom().getID());
				n2 = flowGraph.getNode(e.getTo().getID());
				
				switch(i) {
				case 0:c = Color.green;break;
				case 1:c = Color.MAGENTA;break;
				case 2:c = Color.pink;break;
				case 3:c = Color.cyan;break;
				default: c = Color.blue;break;
				}
				
				drawEdge(g, new Edge(e.getWeight(), n1,n2), c);
				n1.getGUI().paint(g, c);
				n2.getGUI().paint(g, c);
			}
		}
		highLight = false;
	}
	
	
	public void setGraph(FlowGraph g) {
		flowGraph = g;
	}
	
	
	
}
