package trynerror;

import org.apache.log4j.Logger;

public class Log4JTry {

	static Logger logger = Logger.getLogger(Log4JTry.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("OK");
	}

}
