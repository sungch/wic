package bettercare.wic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WicLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(WicLogger.class);

  public void log(String mesg) {
    System.out.println("***********************************");
    System.out.print("*");
    System.out.print(mesg);
    System.out.println("*");
    System.out.println("***********************************");
  }

  public void info(String mesg, Class problemArea) {
    if(LOGGER.isInfoEnabled()) {
      LOGGER.info(problemArea.getSimpleName() + mesg);
    }
  }

  public void debug(String mesg, Class problemArea) {
    if(LOGGER.isDebugEnabled()) {
      LOGGER.debug(problemArea.getSimpleName() + mesg);
    }
  }

  public void warn(String mesg, Class probklemArea) {
    LOGGER.warn(probklemArea.getSimpleName() + mesg);
  }

  public void error(String mesg, Class problemArea) {
    LOGGER.error(problemArea.getSimpleName() + mesg);
  }

  public void error(String mesg, Class problemArea, Throwable ex) {
    LOGGER.error(problemArea.getSimpleName() + mesg, ex);
  }

}
