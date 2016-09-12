package se.olapetersson.util;

import java.util.logging.Logger;

/**
 * Created by ola on 2016-09-12.
 */
public class LoggerUtil {
    private final static Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    public static void logCurrentThread(String caller, Thread thread) {
        logger.info("\nCalled from: " +caller +"\nCurrent thread: : " + thread + "\nCurrent group: " + thread.getThreadGroup() + "\nThread name: " + thread.getName());
    }
}
