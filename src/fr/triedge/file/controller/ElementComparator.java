package fr.triedge.file.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import fr.triedge.file.Element;

public class ElementComparator {

	private HashMap<String, Element> checkedElements = new HashMap<>();
	private ArrayList<Element> duplicateElements = new ArrayList<>();
	private int passCounter = 0;
	
	public void processRootFolder(String root) {
		processRootFolder(new File(root));
	}
	
	public void processRootFolder(File root) {
		passCounter = 0;
		processElement(root);
	}
	
	private void processElement(File file) {
		if (file == null || !file.exists())
			return;
		
		System.out.print("["+ ++passCounter +"] Processing: "+file.getAbsolutePath());
		
		if (file.isDirectory()) {
			System.out.println(" [FOLDER]");
			File[] dirs = file.listFiles();
			for (File f : dirs)
				this.processElement(f);
		}else if (file.isFile()) {
			String name = file.getName();
			String path = file.getAbsolutePath();
			long size = file.length();
			Element element = new Element(name, path, size);
			if (getCheckedElements().containsKey(name)) {
				Element el = getCheckedElements().get(name);
				if (el.getSize() == element.getSize() && el.getName().equals(element.getName())) {
					getDuplicateElements().add(element);
					System.out.println(" [DUPLICATE]");
				}else {
					System.out.println(" [SAME NAME NOT DUPLICATE]");
				}
			}else {
				getCheckedElements().put(name, element);
				System.out.println(" [ORIGINAL]");
			}
		}
	}
	
	public void generateReport(File output) throws IOException {
		System.out.println("Starting report...");
		if (output == null) {
			output = new File("todelete/report.txt");
		}
		
		FileWriter w = new FileWriter(output);
		Date current = new Date();
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMDD:HHmmss");
		w.write("==== REPORT "+format.format(current)+" ====\r\n");
		HashMap<String, ArrayList<Element>> dup = new HashMap<>();
		
		for (Element e : getDuplicateElements()) {
			if (dup.containsKey(e.getName())) {
				dup.get(e.getName()).add(e);
			}else {
				ArrayList<Element> ar = new ArrayList<Element>();
				ar.add(checkedElements.get(e.getName()));
				ar.add(e);
				dup.put(e.getName(), ar);
			}
		}
		
		for (Entry<String,ArrayList<Element>> e : dup.entrySet()) {
			String name = e.getKey();
			w.write("Duplicate file: "+name+"\r\n");
			for (Element el : e.getValue()) {
				w.write(" -> "+el.getPath()+"\r\n");
				w.flush();
			}
			w.write("\r\n");
		}
		Date current2 = new Date();
		w.write("END "+format.format(current2)+"\r\n");
		w.write("\r\n");
		w.flush();
		w.close();
		System.out.println("Report stored in: "+output.getAbsolutePath());
	}

	public ArrayList<Element> getDuplicateElements() {
		return duplicateElements;
	}

	public void setDuplicateElements(ArrayList<Element> duplicateElements) {
		this.duplicateElements = duplicateElements;
	}

	public int getPassCounter() {
		return passCounter;
	}

	public void setPassCounter(int passCounter) {
		this.passCounter = passCounter;
	}

	public HashMap<String, Element> getCheckedElements() {
		return checkedElements;
	}

	public void setCheckedElements(HashMap<String, Element> checkedElements) {
		this.checkedElements = checkedElements;
	}
}
