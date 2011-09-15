package halogenui.processors.file;

import halogenui.preferences.PreferenceConstants;
import halogenui.processors.search.SearchPropertyValues;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Formatter;

import org.eclipse.core.runtime.Platform;

//import org.apache.log4j.Logger;

public class FileContentModifier {

	// private static Logger logger = Logger.getLogger("");
	// private static String module = "General";
	// private static String area = "Calendar";
	private static String newline = System.getProperty("line.separator");
	private static String[] matchFormats = {
			"/globalUI/entry[module='%s' and area='%s']/@key",
			"/globalUI/entry[module='%s']/@key" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public static void addNewEntry(String module, String area,
			String entryString) {

		entryString = entryString.replaceAll("\\n", newline);
		String path = Platform.getPreferencesService().getString("halogenUI",
				PreferenceConstants.PATH, "", null);
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File does not exist.");
			return;
		}

		for (int i = 0; i < matchFormats.length; i++) {
			Formatter fmt = new Formatter();
			String matchFormat = matchFormats[i];
			fmt.format(matchFormat, module, area);
			ArrayList<String> searchResult = null;
			System.out.println(fmt.toString());
			try {
				searchResult = SearchPropertyValues.search(fmt.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (searchResult != null && searchResult.size() > 0) {
				NewEntryAppender appender=null;
				if (i == 0) {
					appender = new SameModuleAreaAppender();
					appendEntryString(path, file, searchResult.size(),
							module,area, entryString,appender);
				} else if (i == 1) {
					appender = new SameModuleAppender();
				}
				appendEntryString(path, file, searchResult.size(),
						module,area, entryString,appender);
				return;
			}
		}
		appendAsTheLastEntry(path, file, entryString);
		return;
	}

	private static void appendEntryString(String path, File file,
			int size, String module, String area, String entryString, NewEntryAppender appender) {

		RandomAccessFile rand;
		try {
			rand = new RandomAccessFile(file, "r");

			File file2 = new File("tempUI.xml");
			file2.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("tempUI.xml", true), "UTF-8"));

			appender.append(rand, out, size, module, area, entryString);

			rand.close();
			out.close();

			if (file.delete()) {
				file2.renameTo(new File(path));
				file2.delete();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void appendAsTheLastEntry(String path, File file,
			String entryString) {
		try {
			RandomAccessFile rand = new RandomAccessFile(file, "r");
			File file2 = new File("tempUI.xml");
			file2.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("tempUI.xml", true), "UTF-8"));
			String line = rand.readLine();
			while (!line.contains("</globalUI>")) {
				out.write(line + newline);
				line = rand.readLine();
			}
			System.out.println("reached /globalUI");
			out.write(entryString);
			out.write(line);

			rand.close();
			out.close();

			if (file.delete()) {
				file2.renameTo(new File(path));
				file2.delete();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void appendToTheSameModuleBlock(String path, File file,
			int size, String module, String entryString) {

		// System.out.println("size="+size);
		try {
			RandomAccessFile rand = new RandomAccessFile(file, "r");

			File file2 = new File("tempUI.xml");
			file2.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("tempUI.xml", true), "UTF-8"));

			String moduleLine = "";
			String areaLine = "";
			int counter = 0;

			while (true) {

				while (moduleLine != null && !moduleLine.contains("module")) {
					moduleLine = rand.readLine();
					out.write(moduleLine + newline);
				}
				if (moduleLine == null) {
					break;
				} else if (moduleLine.contains(">" + module + "<")) {
					areaLine = rand.readLine();
					out.write(areaLine + newline);
					String closeEntryline = rand.readLine();
					out.write(closeEntryline + newline);

					counter++;
					if (counter == size) {
						insertContent(rand, out, entryString);
						break;
					}
				}

				moduleLine = "";
			}
			rand.close();
			out.close();

			if (file.delete()) {
				file2.renameTo(new File(path));
				file2.delete();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void appendToTheSameModuleAreaBlock(String path, File file,
			int size, String module, String area, String entryString) {

		try {
			RandomAccessFile rand = new RandomAccessFile(file, "r");

			File file2 = new File("tempUI.xml");
			file2.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("tempUI.xml", true), "UTF-8"));

			String moduleLine = "";
			String areaLine = "";
			int counter = 0;

			while (true) {

				while (moduleLine != null && !moduleLine.contains("module")) {
					moduleLine = rand.readLine();
					out.write(moduleLine + newline);
				}
				if (moduleLine == null) {
					break;
				} else if (moduleLine.contains(">" + module + "<")) {
					areaLine = rand.readLine();
					out.write(areaLine + newline);
					String closeEntryline = rand.readLine();
					out.write(closeEntryline + newline);
					if (areaLine.contains(">" + area + "<")) {
						counter++;
						if (counter == size) {
							insertContent(rand, out, entryString);
							break;
						}
					}
				}

				moduleLine = "";
			}
			rand.close();
			out.close();

			if (file.delete()) {
				file2.renameTo(new File(path));
				file2.delete();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void insertContent(RandomAccessFile rand, BufferedWriter out,
			String entryString) throws IOException {

		// long insertPos = rand.getFilePointer();
		ArrayList<String> restLines = new ArrayList<String>();
		String line = rand.readLine();
		while (line != null) {

			restLines.add(line);
			line = rand.readLine();
		}

		// rand.seek(insertPos);
		// rand.writeBytes(insertText);
		out.write(entryString);
		// rand.writeBytes(insertText);
		for (String lineToWrite : restLines) {
			// rand.writeBytes(lineToWrite);
			// rand.writeBytes(newline);
			out.write(lineToWrite + newline);
		}
	}

}
