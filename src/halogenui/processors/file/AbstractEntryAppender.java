package halogenui.processors.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public abstract class AbstractEntryAppender implements NewEntryAppender {

	@Override
	abstract public void append(RandomAccessFile rand, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException ;

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
