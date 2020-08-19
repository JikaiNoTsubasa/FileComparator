package fr.triedge.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class FileComparator {
	
	private File root, output;
	private HashMap<String, ElementDescriptor> elements = new HashMap<>();
	
	public FileComparator(File root, File output) {
		super();
		setRoot(root);
		setOutput(output);
	}
	
	public void process() {
		this.processElement(getRoot());
		try {
			this.processReport();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processElement(File file) {
		if (file == null || !file.exists())
			return;
		
		System.out.println("Processing: "+file.getAbsolutePath());
		
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
	
	private void processReport() throws IOException {
		System.out.println("Starting report...");
		if (getOutput() == null) {
			setOutput(new File("report.txt"));
		}
		
		FileWriter w = new FileWriter(getOutput());
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
		System.out.println("Report stored in: "+getOutput().getAbsolutePath());
	}

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("r", "root", true, "Root path to start the comparison");
		options.addOption("o", "out", false, "Output file for the report");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
		if (cmd.hasOption("root")) {
			File root = new File(cmd.getOptionValue("root"));
			File out = null;
			if (cmd.hasOption("out")) {
				out = new File(cmd.getOptionValue("out"));
			}
			FileComparator c = new FileComparator(root,out);
			c.process();
		}else {
			System.err.println("Missing argument -root");
			System.exit(1);
		}
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public HashMap<String, ElementDescriptor> getElements() {
		return elements;
	}

	public void setElements(HashMap<String, ElementDescriptor> elements) {
		this.elements = elements;
	}

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

}
