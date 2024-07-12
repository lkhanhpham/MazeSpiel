package Spiel1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class HighscoreHandler {
	
	private File doc;
	private Scanner sc;
	private String path;
	private String content;
	
	private String[] columnNames = { "", "", ""};
	private String[][] data = new String[3][3];
	
	public HighscoreHandler() {
		URL path1 = this.getClass().getResource("/resources/Spiel 1/knapsack_highscore.txt");
		try {
			sc = new Scanner(path1.openStream()).useDelimiter(";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readFile() {
		content = "";
		while (sc.hasNextLine()) {
			content += sc.nextLine() + "\n";
		}
		return content;
	}
	
	public void writeFile(String content) {
		Path p = FileSystems.getDefault().getPath("./bin/resources/Spiel 1/knapsack_highscore.txt");
		try {
			Path filePath = Files.writeString(p, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getData() {
		for (int i = 0; i < 3; i++) {
			data[i] = sc.next().split(",");
		}
		sc.close();
	}
	
	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public void setDataSet(String[][] data) {
		this.data = data;
	}
	
	public String[][] getDataSet() {
		return data;
	}

	
	
	
}