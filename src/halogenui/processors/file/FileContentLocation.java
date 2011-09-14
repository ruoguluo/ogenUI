package halogenui.processors.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

//import org.apache.log4j.Logger;

public class FileContentLocation {

//	private static Logger logger = Logger.getLogger("");
	private static String module = "General";
	private static String area = "Calendar";
	private static String newline = System.getProperty("line.separator");
	private static String insertText =
		"\t<entry key=\"AAA\">"+newline+
	"\t\t<default lang=\"language\">AAACalendar</default>"+newline+
	"\t\t<keylabel>AACalendar title</keylabel>"+newline+
	"\t\t<module>AAAGeneral</module>"+newline+
	"\t\t<area>AAACalendar</area>"+newline+
	"\t</entry>"+newline;


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
			
			File file2 = new File("copy1.xml");
		    // Create file if it does not exist
		    boolean success = file.createNewFile();
		    
		    BufferedWriter out = new BufferedWriter(new FileWriter("copy1.xml", false));


			String moduleLine = "";
			String areaLine = "";
			while (true){
				while (moduleLine!=null&&!moduleLine.contains("module")){
					moduleLine = rand.readLine();
					out.write(moduleLine+newline);
				}
				if (moduleLine!=null){
					areaLine = rand.readLine();
					out.write(areaLine+newline);
				} else{
					break;
				}
//				logger.info(moduleLine);
//				logger.info(areaLine);
				if (moduleLine.matches("\\s*<module>"+module+"</module>.*")&&
						areaLine.matches("\\s*<area>"+area+"</area>.*")){
					insertContent(rand,out);
					break;
				}
				moduleLine = rand.readLine();
				out.write(moduleLine+newline);
			}
			rand.close();
		    out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertContent(RandomAccessFile rand,BufferedWriter out) throws IOException{
		String line = rand.readLine();
		out.write(line+newline);
		//long insertPos = rand.getFilePointer();
		ArrayList<String> restLines = new ArrayList<String>();
		line = rand.readLine();
		while (line!=null){
			
			restLines.add(line);
			line = rand.readLine();
		}

		//rand.seek(insertPos);
		//rand.writeBytes(insertText);
		out.write(insertText);
		//rand.writeBytes(insertText);
		for (String lineToWrite:restLines){
			//rand.writeBytes(lineToWrite);
			//rand.writeBytes(newline);
			out.write(lineToWrite+newline);
		}
	}

}
