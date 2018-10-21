package bettercare.wic.service.supports;


import java.util.Calendar;
import java.util.Date;


/**
 * This work should have been done before json voucher object is saved
 */
public class TimeTrimmer {

  public long adjustStartingTime(long voucherTime) {
    Date date = new Date(voucherTime);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR, 0);
    return calendar.getTimeInMillis();
  }

  public long adjustExpiringTime(long voucherTime) {
    Date date = new Date(voucherTime);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 999);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.HOUR, 11);
    return calendar.getTimeInMillis();
  }

}
