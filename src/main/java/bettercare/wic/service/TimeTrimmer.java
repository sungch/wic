package bettercare.wic.service;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 * This work should have been done before json voucher object is saved
 */
public class TimeTrimmer {

  public Timestamp adjustStartingTime(Timestamp voucherTime) {
    Date date = voucherTime;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR, 0);
    return new Timestamp(calendar.getTimeInMillis());
  }

  public Timestamp adjustExpiringTime(Timestamp voucherTime) {
    Date date = voucherTime;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 999);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.HOUR, 11);
    return new Timestamp(calendar.getTimeInMillis());
  }

}
