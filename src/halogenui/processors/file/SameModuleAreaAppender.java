package halogenui.processors.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SameModuleAreaAppender extends AbstractEntryAppender {

	@Override
	public void append(RandomAccessFile rand, BufferedWriter out, int size,
			String module, String area, String entryString) throws IOException {
		
		System.out.println("SameModuleAreaAppender is called");

		String moduleLine = "";
		String areaLine = "";
		int counter = 0;

		while (true) {

			while (moduleLine != null && !moduleLine.contains("module")) {
				moduleLine = rand.readLine();
				out.write(moduleLine + newline);
			}
			if (moduleLine == null) {
				break;
			} else if (moduleLine.contains(">" + module + "<")) {
				areaLine = rand.readLine();
				out.write(areaLine + newline);
				String closeEntryline = rand.readLine();
				out.write(closeEntryline + newline);
				if (areaLine.contains(">" + area + "<")) {
					counter++;
					if (counter == size) {
						insertContent(rand, out, entryString);
						break;
					}
				}
			}

			moduleLine = "";
		}

	}

}
