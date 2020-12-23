package fr.triedge.file.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import fr.triedge.file.ElementDescriptor;

public class FComparator {

	private String rootFolder;
	private HashMap<String, ElementDescriptor> elements = new HashMap<>();
	private int counter = 0;
	 
	public FComparator(String rootFolder) {
		setRootFolder(rootFolder);
	}
	
	public void processFolder() {
		counter = 0;
		getElements().clear();
		processElement(new File (getRootFolder()));
	}
	
	private void processElement(File file) {
		if (file == null || !file.exists())
			return;
		
		System.out.println("["+ ++counter +"] Processing: "+file.getAbsolutePath());
		
		if (file.isDirectory()) {
			File[] dirs = file.listFiles();
			for (File f : dirs)
				this.processElement(f);
		}else if (file.isFile()) {
			String name = file.getName();
			String path = file.getAbsolutePath();
			if (getElements().containsKey(name)) {
				ElementDescriptor desc = getElements().get(name);
				desc.getPath().add(path);
			}else {
				ElementDescriptor ed = new ElementDescriptor(name);
				ed.getPath().add(path);
				getElements().put(name, ed);
			}
		}
	}
	
	public void processReport(File output) throws IOException {
		System.out.println("Starting report...");
		if (output == null) {
			output = new File("todelete/report.txt");
		}
		
		FileWriter w = new FileWriter(output);
		Date current = new Date();
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMDD:HHmmss");
		w.write("==== REPORT "+format.format(current)+" ====\r\n");
		
		for (Entry<String,ElementDescriptor> e : getElements().entrySet()) {
			ElementDescriptor ed = e.getValue();
			if (ed.getPath().size() > 1) {
				w.write("Duplicate file: "+ed.getName()+"\r\n");
				for(String path : ed.getPath()) {
					w.write(" -> "+path+"\r\n");
				}
				w.write("\r\n");
				w.flush();
			}
		}
		Date current2 = new Date();
		w.write("END "+format.format(current2)+"\r\n");
		w.write("\r\n");
		w.flush();
		w.close();
		System.out.println("Report stored in: "+output.getAbsolutePath());
	}

	public String getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}

	public HashMap<String, ElementDescriptor> getElements() {
		return elements;
	}

	public void setElements(HashMap<String, ElementDescriptor> elements) {
		this.elements = elements;
	}
}
