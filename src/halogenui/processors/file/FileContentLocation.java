package halogenui.processors.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class FileContentLocation {

	private static Logger logger = Logger.getLogger("");
	private static String module = "General";
	private static String area = "Calendar";
	private static String insertText =
		"<entry key=\"AAA\">\n"+
	"\t<default lang=\"language\">AAACalendar</default>\n"+
	"\t<keylabel>AACalendar title</keylabel>\n"+
	"\t<module>AAAGeneral</module>\n"+
	"\t<area>AAACalendar</area>\n"+
	"</entry>";


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File file = new File("halogenUI.xml");
		  if(!file.exists())
		  {
		  System.out.println("File does not exist.");
		  System.exit(0);
		  }

		  try {
			RandomAccessFile rand = new RandomAccessFile(file,"rw");

			String moduleLine = "";
			String areaLine = "";
			while (true){
				while (moduleLine!=null&&!moduleLine.contains("module")){
					moduleLine = rand.readLine();
				}
				if (moduleLine!=null){
					areaLine = rand.readLine();
				} else{
					break;
				}
				logger.info(moduleLine);
				logger.info(areaLine);
				if (moduleLine.matches("\\s*<module>"+module+"</module>.*")&&
						areaLine.matches("\\s*<area>"+area+"</area>.*")){
					insertContent(rand);
					break;
				}
				moduleLine = rand.readLine();
			}
			rand.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertContent(RandomAccessFile rand) throws IOException{
		long insertPos = rand.getFilePointer();
		ArrayList<String> restLines = new ArrayList<String>();
		String line = rand.readLine();
		while (line!=null){
			restLines.add(line);
			line = rand.readLine();
		}

		rand.seek(insertPos);
		rand.writeBytes(insertText);
		for (String lineToWrite:restLines){
			rand.writeBytes(lineToWrite);
		}
	}

}
