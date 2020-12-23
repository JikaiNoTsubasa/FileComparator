package fr.triedge.file.ui;


import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.triedge.file.controller.Controller;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 6858746380825047206L;
	
	private Controller controller;

	public MainWindow(Controller controller) {
		setController(controller);
		setTitle("File Comparator");
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.build();
		
		setVisible(true);
	}

	private void build() {
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu comp = new JMenu("Comparator");
		
		JMenuItem itemCompare = new JMenuItem("Start Compare");
		itemCompare.addActionListener(res -> controller.startComparator());
		
		JMenuItem itemMark = new JMenuItem("Mark to Delete");
		itemMark.addActionListener(res -> {
			try {
				controller.generateDeleteList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		JMenuItem itemReport = new JMenuItem("Generate Report");
		itemReport.addActionListener(res -> controller.generateReport());
		
		bar.add(file);
		bar.add(comp);
		comp.add(itemCompare);
		comp.add(itemMark);
		comp.add(itemReport);
		
		setJMenuBar(bar);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
