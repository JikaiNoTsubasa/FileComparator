package fr.triedge.file.conf;

import java.util.Properties;

public class Config {
	
	public static final String ROOT_LOCATION				= "fc.root.location";
	public static final String BACKUP_LOCATION				= "fc.backup.location";
	public static final String DELETE_LOCATION				= "fc.delete.location";

	private Properties param = new Properties();

	public Properties getParam() {
		return param;
	}

	public void setParam(Properties param) {
		this.param = param;
	}
}
