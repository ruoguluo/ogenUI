package halogenui.processors.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public interface NewEntryAppender {
	static String newline = System.getProperty("line.separator");

	public void append(RandomAccessFile rand, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException;
}
