package halogenui.processors.append;

import halogenui.model.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class DeleteEntryAppender extends AbstractEntryAppender {

	@Override
	public void append(BufferedReader in, BufferedWriter out, int size,
			String module, String area, String key) throws IOException {

		String line = in.readLine();
		while (!line.contains("<entry key=\""+key+"\">")) {
			out.write(line + Constants.newline);
			line = in.readLine();
		}
		while (!line.contains("</entry>")){
			line = in.readLine();
		}

		insertContent(in,out,"");
		
	}

}
