package halogenui.processors.append;

import halogenui.model.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SameModuleAreaAppender extends AbstractEntryAppender {

	@Override
	public void append(BufferedReader in, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException {

		System.out.println("SameModuleAreaAppender is called");

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
				if (areaLine.contains(">" + area + "<")) {
					counter++;
					if (counter == size) {
						insertContent(in, out, entryString);
						break;
					}
				}
			}

			moduleLine = "";
		}

	}

}
