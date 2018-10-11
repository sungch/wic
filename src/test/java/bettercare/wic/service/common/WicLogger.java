package bettercare.wic.service.common;

public class WicLogger {

  public void log(String mesg) {
    System.out.println("***********************************");
    System.out.print("*");
    System.out.print(mesg);
    System.out.println("*");
    System.out.println("***********************************");
  }

}
