package halogenui.processors.append;

import halogenui.preferences.PreferenceConstants;
import halogenui.processors.search.SearchPropertyValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Formatter;

import org.eclipse.core.runtime.Platform;

//import org.apache.log4j.Logger;

public class FileContentModifier {

	private static final String path = Platform.getPreferencesService()
			.getString("halogenUI", PreferenceConstants.PATH, "", null);
	private static final String[] matchFormats = {
			"/globalUI/entry[module='%s' and area='%s']/@key",
			"/globalUI/entry[module='%s']/@key" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public static void deleteEntry(String key) {

		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File does not exist.");
			return;
		}

		NewEntryAppender appender = new DeleteEntryAppender();
		appendEntryString(path, file, 0, null, null, key, appender);

	}

	public static void addNewEntry(String module, String area,
			String entryString) {

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
				NewEntryAppender appender = null;
				if (i == 0) {
					appender = new SameModuleAreaAppender();
				} else if (i == 1) {
					appender = new SameModuleAppender();
				}
				appendEntryString(path, file, searchResult.size(), module,
						area, entryString, appender);
				return;
			}
		}
		appendEntryString(path, file, 0, "", "", entryString,
				new LastEntryAppender());
		// appendAsTheLastEntry(path, file, entryString);
		return;
	}

	private static void appendEntryString(String path, File file, int size,
			String module, String area, String entryString,
			NewEntryAppender appender) {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "UTF-8"));

			File file2 = new File("tempUI.xml");
			file2.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("tempUI.xml", true), "UTF-8"));

			appender.append(in, out, size, module, area, entryString);

			in.close();
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
}
