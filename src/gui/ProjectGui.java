package gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import flowGraph.FlowGraph;
import flowGraph.Graph;
import graphAlgorithms.ForwardPath;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class ProjectGui extends JFrame {

	private final JPanel content;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel edit = new JPanel();
	private final JButton newGraph = new JButton("new graph");
	private GraphGUI flowGraph = new GraphGUI();
	private final JLabel labelTo = new JLabel("to");
	private final JLabel labelFrom = new JLabel("from");
	private final JLabel labelWeight = new JLabel("weight");
	private final JTextField textFieldFrom = new JTextField();
	private final JTextField textFieldTo = new JTextField();
	private final JTextField textFieldWeight = new JTextField();
	private final JTextField textFieldNodesNumber = new JTextField();
	private final JButton buttonAddNode = new JButton("add");
	private final JButton buttonDeleteNode = new JButton("delete");
	private final JLabel labelNodesNum = new JLabel("nodes : 0");
	private final JLabel labelDelta = new JLabel("delta : 0");
	private final JLabel labelBrancesNumber = new JLabel("branches : 0");
	private final JTabbedPane tabbedPaneStatus = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelForwardPath = new JPanel();
	private final JTabbedPane tabbedPaneLoops = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelAllLoops = new JPanel();
	private final JPanel panelNonTouchedLoops = new JPanel();;
	private JList listForawrdPaths;
	private DefaultListModel<String> listModelPath = new DefaultListModel<String>();
	private DefaultListModel<String> listModelAllLoops = new DefaultListModel<String>();
	private JList listAllLoops = new JList();
	
	private JList listNodTouched = new JList();
	private DefaultListModel<String> listModelNonTouchedLoops = new DefaultListModel<String>();
	private final JLabel labelTransfer = new JLabel("transfer function : ");
	private final JLabel labelTranferFunctionResult = new JLabel("0");
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectGui frame = new ProjectGui();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProjectGui() {
		super("signal flow graph solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 633);
		content = new JPanel();
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(content);
		content.setLayout(null);
		tabbedPane.setBounds(12, 0, 208, 592);
		content.add(tabbedPane);
		
		JPanel general = new JPanel();
		tabbedPane.addTab("general", null, general, null);
		general.setLayout(null);
		labelNodesNum.setHorizontalAlignment(SwingConstants.LEFT);
		labelNodesNum.setBounds(12, 12, 179, 15);
		
		general.add(labelNodesNum);
		labelDelta.setBounds(12, 66, 179, 15);
		
		general.add(labelDelta);
		labelBrancesNumber.setBounds(12, 39, 179, 15);
		
		general.add(labelBrancesNumber);
		tabbedPaneStatus.setBounds(12, 136, 179, 417);
		
		general.add(tabbedPaneStatus);
		
		tabbedPaneStatus.addTab("paths", null, panelForwardPath, null);
		panelForwardPath.setLayout(null);
		
		listModelPath = new DefaultListModel<String>();
		listForawrdPaths = new JList(listModelPath);
		listForawrdPaths.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				PathListListener(arg0);
				
			}
		});
		listForawrdPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneForwardPaths = new JScrollPane(listForawrdPaths);
		scrollPaneForwardPaths.setBounds(0, 12, 174, 378);
		panelForwardPath.add(scrollPaneForwardPaths);
		
		
		
		tabbedPaneStatus.addTab("Loops", null, tabbedPaneLoops, null);
		
		tabbedPaneLoops.addTab("All", null, panelAllLoops, null);
		panelAllLoops.setLayout(null);
				
		
		
		listModelAllLoops = new DefaultListModel<String>();
		JScrollPane scrollPaneAllLoops = new JScrollPane();
		scrollPaneAllLoops.setBounds(1, 12, 162, 351);
		panelAllLoops.add(scrollPaneAllLoops);
		listAllLoops = new JList(listModelAllLoops);
		scrollPaneAllLoops.setViewportView(listAllLoops);
		listAllLoops.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				LoopsListListner(arg0);
			}
		});
		
		listAllLoops.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		tabbedPaneLoops.addTab("non-touched", null, panelNonTouchedLoops, null);
		
		listModelNonTouchedLoops = new DefaultListModel<String>();
		listNodTouched = new JList<>(listModelNonTouchedLoops);
		listNodTouched.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				notTouchedLoopsListListner(arg0);
				
			}
		});
		JScrollPane scrollpaneNonTouched = new JScrollPane(listNodTouched);
		panelNonTouchedLoops.setLayout(null);
		scrollpaneNonTouched.setBounds(0, 12, 169, 351);
		
		panelNonTouchedLoops.add(scrollpaneNonTouched);
		listNodTouched.setBounds(0, 0, 1, 1);
		labelTransfer.setBounds(12, 93, 179, 15);
		
		general.add(labelTransfer);
		labelTranferFunctionResult.setBounds(12, 109, 179, 15);
		
		general.add(labelTranferFunctionResult);
		
		textFieldNodesNumber.setBounds(134, 32, 41, 25);
		textFieldNodesNumber.setColumns(10);
		textFieldWeight.setBounds(109, 152, 82, 19);
		textFieldWeight.setColumns(10);
		textFieldTo.setBounds(109, 125, 82, 19);
		textFieldTo.setColumns(10);
		textFieldFrom.setBounds(109, 98, 82, 19);
		textFieldFrom.setColumns(10);
		
		tabbedPane.addTab("Edit", null, edit, null);
		edit.setLayout(null);
		newGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					int x = Integer.parseInt(textFieldNodesNumber.getText());
					flowGraph.setGraph(new FlowGraph(x));
					updateGraph();
				}catch(Exception e){}
				
			}
		});
		newGraph.setBounds(12, 32, 110, 25);
		
		edit.add(newGraph);
		labelTo.setHorizontalAlignment(SwingConstants.LEFT);
		labelTo.setBounds(12, 127, 70, 15);
		
		edit.add(labelTo);
		labelFrom.setHorizontalAlignment(SwingConstants.LEFT);
		labelFrom.setBounds(12, 100, 70, 15);
		
		edit.add(labelFrom);
		labelWeight.setHorizontalAlignment(SwingConstants.LEFT);
		labelWeight.setBounds(12, 154, 70, 15);
		
		edit.add(labelWeight);
		
		edit.add(textFieldFrom);
		
		edit.add(textFieldTo);
		
		edit.add(textFieldWeight);
		
		edit.add(textFieldNodesNumber);
		buttonAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int from,to;
				double weight;
				try {
					from = Integer.parseInt(textFieldFrom.getText());
					to = Integer.parseInt(textFieldTo.getText());
					weight = Double.parseDouble(textFieldWeight.getText());
					flowGraph.flowGraph.addEdge(from, to, weight);
					updateGraph();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		buttonAddNode.setBounds(109, 181, 61, 25);
		
		edit.add(buttonAddNode);
		buttonDeleteNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int from,to,weight;
				try {
					from = Integer.parseInt(textFieldFrom.getText());
					to = Integer.parseInt(textFieldTo.getText());
					weight = Integer.parseInt(textFieldWeight.getText());
					flowGraph.flowGraph.removeEdge(from, to, weight);
					updateGraph();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		buttonDeleteNode.setBounds(12, 181, 82, 25);
		
		edit.add(buttonDeleteNode);
		flowGraph.setBorder(new LineBorder(new Color(0, 0, 0)));
		flowGraph.setBounds(232, 22, 619, 570);
		
		content.add(flowGraph);
		
		JButton buttonCopyRight = new JButton(".");
		buttonCopyRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog( null, "- Ahmed Mohammed Hussein\n- Bahie el den\n"
						+"2016");
			}
		});
		buttonCopyRight.setBounds(216, 3, 23, 16);
		content.add(buttonCopyRight);
	}
	
	
	
	
	private void updateGraph() {
		
		flowGraph.flowGraph.update();
		
		labelNodesNum.setText("nodes : "+flowGraph.flowGraph.nodesNumber());
		labelBrancesNumber.setText("branches : "+flowGraph.flowGraph.branchesNumber());
		labelDelta.setText("delta : "+flowGraph.flowGraph.getDelta());
		labelTranferFunctionResult.setText(""+flowGraph.flowGraph.getOverAllTransferFunction());
		updateForwardPathsList();
		
		
		flowGraph.repaint();
	}
	
	
	
	
	
	private void updateForwardPathsList() {
		
		
		Graph[] tmp = flowGraph.flowGraph.getForwardPaths();
		listModelPath.clear();
		listModelPath.addElement("main graph");
		double t [] = flowGraph.flowGraph.getForwardPathDelta();
		for(int i = 0; i < tmp.length; i++) {
			listModelPath.addElement(tmp[i].getPathString()+" / "+tmp[i].getGain()+" / "+t[i]);
		}
		
	    tmp = flowGraph.flowGraph.getAllLoops();
		listModelAllLoops.clear();
		listModelAllLoops.addElement("main graph");
		for(int i = 0; i < tmp.length; i++) {
			listModelAllLoops.addElement(tmp[i].getNodesString()+" / "+tmp[i].getGain());
		}
		
		ArrayList<ArrayList<Integer>> l = flowGraph.flowGraph.getNonTouchingLoops();
		listModelNonTouchedLoops.clear();
		listModelNonTouchedLoops.addElement("main graph");
		for(int i = 0 ; i < l.size() ; i++){
			
			double x = 1;
			for(int j = 0; j < l.get(i).size(); j ++) {
				x *= flowGraph.flowGraph.getAllLoops()[l.get(i).get(j)].getGain();
			}
			listModelNonTouchedLoops.addElement(l.get(i).toString()+" / "+x);
			
		}
		
	}
	
	
	private void PathListListener(ListSelectionEvent arg0) {
		int x = listForawrdPaths.getSelectedIndex();
		if(x < 1){
			flowGraph.repaint();
			return;
		}
		
		flowGraph.repaint();
		flowGraph.highLight = true;
		Graph[] t = new Graph[1];
		t[0] = flowGraph.flowGraph.getForwardPaths()[x-1];
		flowGraph.toBeHighlted = t;
		flowGraph.repaint();
		
	}
	
	
	private void LoopsListListner(ListSelectionEvent arg0) {
		int x = listAllLoops.getSelectedIndex();
		if(x < 1){
			flowGraph.repaint();
			return;
		}
		
		flowGraph.repaint();
		flowGraph.highLight = true;
		Graph[] t = new Graph[1];
		t[0] = flowGraph.flowGraph.getAllLoops()[x-1];;
		flowGraph.toBeHighlted = t;
		flowGraph.repaint();
	}
	
	
	private void notTouchedLoopsListListner(ListSelectionEvent arg0) {
		
		flowGraph.repaint();
		int x = listNodTouched.getSelectedIndex();
		if(x < 1){
			flowGraph.repaint();
			return;
		}
		
		flowGraph.repaint();
		flowGraph.highLight = true;
		ArrayList<Integer> t = flowGraph.flowGraph.getNonTouchingLoops().get(x-1);
		Graph[] g = new Graph[t.size()];
		for(int i = 0; i < t.size() ;i++) {
			g[i] = flowGraph.flowGraph.getAllLoops()[t.get(i)];
		}
		flowGraph.toBeHighlted = g;
		
		flowGraph.repaint();
	}
}
