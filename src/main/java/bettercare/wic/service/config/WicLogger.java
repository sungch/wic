package bettercare.wic.service.config;

import org.springframework.stereotype.Service;

@Service
public class WicLogger {

  public void log(String mesg) {
    System.out.println("***********************************");
    System.out.print("*");
    System.out.print(mesg);
    System.out.println("*");
    System.out.println("***********************************");
  }

}
