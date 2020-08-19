package fr.triedge.file;

import java.util.ArrayList;

public class ElementDescriptor {

	private String name;
	private ArrayList<String> path = new ArrayList<>();
	
	public ElementDescriptor(String name) {
		super();
		this.name = name;
	}
	public ArrayList<String> getPath() {
		return path;
	}
	public void setPath(ArrayList<String> path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
