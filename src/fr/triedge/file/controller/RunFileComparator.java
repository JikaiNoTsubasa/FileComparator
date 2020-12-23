package fr.triedge.file.controller;

import java.io.IOException;
import java.util.Scanner;

public class RunFileComparator {
	
	public void terminal() {
		Controller c = new Controller();
		System.out.println("#===========================================================#");
		System.out.println("# Silent Mode                                               #");
		System.out.println("#===========================================================#");
		System.out.println("# Commands:                                                 #");
		System.out.println("# - scan : Scans the root folder for duplicates             #");
		System.out.println("# - list : After scan, write the list of items to delete    #");
		System.out.println("# - report : After scan, generates report                   #");
		System.out.println("# - delete : After list, deletes duplicates entries         #");
		System.out.println("#===========================================================#");
		System.out.println("");
		try {
			c.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(System.in);
		String line = "";
		while(!line.equalsIgnoreCase("exit")) {
			System.out.print("> ");
			line = scan.nextLine();
			switch (line) {
			case "scan": 
				methScan(c);
				break;
			case "list":
				methList(c);
				break;
			case "report":
				methReport(c);
				break;
			case "delete":
				methDelete(c);
				break;
			case "exit":
				break;
			default:
				System.out.println("Command not reconized");
			}
		}
		scan.close();
	}

	private void methDelete(Controller c) {
		c.deleteFiles();
	}

	private void methReport(Controller c) {
		c.generateReport();
	}

	private void methList(Controller c) {
		try {
			c.generateDeleteList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void methScan(Controller c) {
		c.startComparator();
	}

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("-silent")) {
			RunFileComparator rfc = new RunFileComparator();
			rfc.terminal();
		}else {
			Controller ctrl = new Controller();
			ctrl.init();
			
		}
	}

}
