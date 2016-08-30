package gui;

import java.awt.Color;
import java.awt.Graphics2D;

import flowGraph.Node;

public class NodeGui {

	private static final int DIAMATER = 30;
	int id;
	int centerX,centerY;
	
	
	public NodeGui(int id) {
		this.id = id;
	}
	
	void set(int centerX,int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	public void paint(Graphics2D g,Color color) {
		g.setColor(Color.yellow);
		g.fillOval(centerX-DIAMATER/2, centerY-DIAMATER/2, DIAMATER, DIAMATER);
		
		
		g.setColor(color);
		g.drawOval(centerX-DIAMATER/2, centerY-DIAMATER/2, DIAMATER, DIAMATER);
		
		g.setColor(Color.black);
		g.drawString(id+"", centerX-5, centerY+5);
	}
	
	
	public void paint(Graphics2D g, int centerX,int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		g.setColor(Color.yellow);
		g.fillOval(centerX-DIAMATER/2, centerY-DIAMATER/2, DIAMATER, DIAMATER);
		
		g.setColor(Color.red);
		g.drawOval(centerX-DIAMATER/2, centerY-DIAMATER/2, DIAMATER, DIAMATER);
		
		g.setColor(Color.black);
		g.drawString(id+"", centerX-5, centerY+5);
	}
	
	
}
