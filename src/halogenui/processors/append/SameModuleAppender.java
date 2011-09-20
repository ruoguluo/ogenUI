package halogenui.processors.append;

import halogenui.models.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SameModuleAppender extends AbstractEntryAppender {

	@Override
	public void append(BufferedReader in, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException {

		String moduleLine = "";
		String areaLine = "";
		int counter = 0;

		while (true) {

			while (moduleLine != null && !moduleLine.contains("module")) {
				moduleLine = in.readLine();
				out.write(moduleLine + Constants.newline);
			}
			if (moduleLine == null) {
				break;
			} else if (moduleLine.contains(">" + module + "<")) {
				areaLine = in.readLine();
				out.write(areaLine + Constants.newline);
				String closeEntryline = in.readLine();
				out.write(closeEntryline + Constants.newline);

				counter++;
				if (counter == size) {
					insertContent(in, out, entryString);
					break;
				}
			}

			moduleLine = "";
		}

	}

}
