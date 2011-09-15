package halogenui.processors.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LastEntryAppender implements NewEntryAppender {

	@Override
	public void append(RandomAccessFile rand, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException {

		String line = rand.readLine();
		while (!line.contains("</globalUI>")) {
			out.write(line + newline);
			line = rand.readLine();
		}
		System.out.println("reached /globalUI");
		out.write(entryString);
		out.write(line);

	}

}
