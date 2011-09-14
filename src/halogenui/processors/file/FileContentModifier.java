package halogenui.processors.file;

import halogenui.preferences.PreferenceConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;

//import org.apache.log4j.Logger;

public class FileContentModifier {

	// private static Logger logger = Logger.getLogger("");
	// private static String module = "General";
	// private static String area = "Calendar";
	private static String newline = System.getProperty("line.separator");

	// private static String insertText = "\t<entry key=\"AA2\">" + newline
	// + "\t\t<default lang=\"language\">AAACalendar</default>" + newline
	// + "\t\t<keylabel>AACalendar title</keylabel>" + newline
	// + "\t\t<module>AAAGeneral</module>" + newline
	// + "\t\t<area>AAACalendar</area>" + newline + "\t</entry>" + newline;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public static void addNewEntry(String module, String area,
			String entryString) {

		String path = Platform.getPreferencesService().getString("halogenUI",
				PreferenceConstants.PATH, "", null);
		boolean moduleFound = false;

		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File does not exist.");
			System.exit(0);
		}

		try {
			RandomAccessFile rand = new RandomAccessFile(file, "r");

			File file2 = new File("tempUI.xml");
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					"tempUI.xml", true),"UTF-8"));

			String moduleLine = rand.readLine();;
			String areaLine = "";
			while (true) {

				while (!moduleLine.contains("</globalUI>")
						&& !moduleLine.contains("module")) {
					out.write(moduleLine + newline);
					moduleLine = rand.readLine();
				}
				if (moduleLine.contains("module")) {
					out.write(moduleLine + newline);
					areaLine = rand.readLine();
					out.write(areaLine + newline);
					String closeEntryline = rand.readLine();
					out.write(closeEntryline + newline);
				} else {
					appendAsTheLastEntry(entryString, moduleLine, out);
					break;
				}
				// logger.info(moduleLine);
				// logger.info(areaLine);
				if (moduleLine.matches("\\s*<module>" + module + "</module>.*")) {
					moduleFound = true;
					if (areaLine.matches("\\s*<area>" + area + "</area>.*")) {
						insertContent(rand, out, entryString);
						break;
					}
				} else {
					if (moduleFound) {

					}
				}
//				moduleLine="";
				moduleLine = rand.readLine();
//				out.write(moduleLine + newline);
			}
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

	private static void appendAsTheLastEntry(String entryString,
			String lastLine, BufferedWriter out) throws IOException {
		out.write(entryString);
		out.write(lastLine + newline);
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
