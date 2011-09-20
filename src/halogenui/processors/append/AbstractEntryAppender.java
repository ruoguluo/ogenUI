package halogenui.processors.append;

import halogenui.models.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractEntryAppender implements NewEntryAppender {

	@Override
	abstract public void append(BufferedReader in, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException ;

	public static void insertContent(BufferedReader in, BufferedWriter out,
			String entryString) throws IOException {

		ArrayList<String> restLines = new ArrayList<String>();
		String line = in.readLine();
		while (line != null) {

			restLines.add(line);
			line = in.readLine();
		}
		out.write(entryString);
		for (String lineToWrite : restLines) {
			out.write(lineToWrite + Constants.newline);
		}
	}

}
