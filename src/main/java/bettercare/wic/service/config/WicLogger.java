package bettercare.wic.service.config;

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


  public void logInfo(String mesg, Class clz) {
    if(LOGGER.isInfoEnabled()) {
      LOGGER.info(mesg + clz.getSimpleName());
    }
  }

  public void logDebug(String mesg, Class clz) {
    if(LOGGER.isDebugEnabled()) {
      LOGGER.debug(mesg + clz.getSimpleName());
    }
  }

  public void logWarn(String mesg, Class clz) {
    LOGGER.warn(mesg + clz.getSimpleName());
  }

  public void logError(String mesg, Class clz) {
    LOGGER.error(mesg + clz.getSimpleName());
  }

  public void logError(String mesg, Class clz, Throwable ex) {
    LOGGER.error(mesg + clz.getSimpleName(), ex);
  }

}
