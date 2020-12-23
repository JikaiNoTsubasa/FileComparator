package fr.triedge.file;

public class Element {

	private String name, path;
	private long size;
	
	
	public Element(String name, String path, long size) {
		super();
		this.name = name;
		this.path = path;
		this.size = size;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
