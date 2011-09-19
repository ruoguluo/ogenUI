package halogenui.processors.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DeleteEntryAppender extends AbstractEntryAppender {

	@Override
	public void append(RandomAccessFile rand, BufferedWriter out, int size,
			String module, String area, String key) throws IOException {

		String line = rand.readLine();
		while (!line.contains("<entry key=\""+key+"\">")) {
			out.write(line + newline);
			line = rand.readLine();
		}
		while (!line.contains("</entry>")){
			line = rand.readLine();
		}

		insertContent(rand,out,"");
		
	}

}
