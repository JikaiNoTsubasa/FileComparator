package fr.triedge.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import fr.triedge.file.Element;
import fr.triedge.file.conf.Config;
import fr.triedge.file.ui.MainWindow;

public class Controller {

	private static final String CONF_LOCATION				= "conf"+File.separator+"config.properties";
	private Config config;
	private MainWindow window;
	private ElementComparator comparator;
	
	public void init() {
		// Load config
		try {
			loadConfig();
			
			startUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startUI() {
		setWindow(new MainWindow(this));
	}
	
	public void startComparator() {
		setComparator(new ElementComparator());
		//setComparator(new FComparator(getConfig().getParam().getProperty(Config.ROOT_LOCATION)));
		getComparator().processRootFolder(getConfig().getParam().getProperty(Config.ROOT_LOCATION));
	}
	
	public void generateDeleteList() throws IOException {
		File f = new File(getConfig().getParam().getProperty(Config.DELETE_LOCATION));
		FileWriter w = new FileWriter(f);
		/*
		for (Entry<String,ElementDescriptor> e : getComparator().getElements().entrySet()) {
			if (e.getValue().getPath().size() < 2)
				continue;
			ElementDescriptor el = e.getValue();
			for (int i = 1; i < el.getPath().size(); ++i) {
				String path = e.getValue().getPath().get(i);
				w.write(path+"\r\n");
				w.flush();
				System.out.println("Marked to delete: "+path);
			}
		}*/
		for(Element e : getComparator().getDuplicateElements()) {
			w.write(e.getPath()+"\r\n");
			w.flush();
			System.out.println("Marked to delete: "+e.getPath());
		}
		w.close();
	}
	
	public void generateReport() {
		/*
		if (getComparator() != null) {
			try {
				getComparator().processReport(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		try {
			getComparator().generateReport(new File("todelete/report.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFiles() {
		File report = new File(getConfig().getParam().getProperty(Config.DELETE_LOCATION));
		if (!report.exists()) {
			System.out.println("File with list doesn't exist");
			return;
		}
		
		try {
			Scanner scan = new Scanner(report);
			while(scan.hasNext()) {
				String path = scan.nextLine();
				System.out.print("Deleting :"+path);
				File file = new File(path);
				if (file.exists()) {
					boolean deleted = file.delete();
					if (deleted) {
						System.out.println(" [DELETE SUCCESSFUL]");
					}else {
						System.out.println(" [DELETE FAILED]");
					}
				}else {
					System.out.println(" [ALREADY DELETED]");
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() throws FileNotFoundException, IOException {
		setConfig(new Config());
		getConfig().getParam().load(new FileInputStream(new File(CONF_LOCATION)));
	}
	
	public void saveConfig() throws IOException {
		getConfig().getParam().store(new FileOutputStream(new File(CONF_LOCATION)), "");
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public MainWindow getWindow() {
		return window;
	}

	public void setWindow(MainWindow window) {
		this.window = window;
	}

	public ElementComparator getComparator() {
		return comparator;
	}

	public void setComparator(ElementComparator comparator) {
		this.comparator = comparator;
	}
}
