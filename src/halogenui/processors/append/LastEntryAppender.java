package halogenui.processors.append;

import halogenui.model.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class LastEntryAppender implements NewEntryAppender {

	@Override
	public void append(BufferedReader in, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException {

		String line = in.readLine();
		while (!line.contains("</globalUI>")) {
			out.write(line + Constants.newline);
			line = in.readLine();
		}
		System.out.println("reached /globalUI");
		out.write(entryString);
		out.write(line);

	}

}
