package halogenui.processors.append;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface NewEntryAppender {

	public void append(BufferedReader in, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException;
}
