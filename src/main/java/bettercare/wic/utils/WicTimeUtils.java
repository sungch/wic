package bettercare.wic.utils;

import java.sql.Timestamp;
import java.util.*;

/**
 * This work should have been done before json voucher object is saved
 */
public class WicTimeUtils {

  public Timestamp truncateHours(Timestamp localTs) {
    return getAppliedTime(localTs.getTime(), " 00:00:01");
  }

  public Timestamp addFullHours(Timestamp localTs) {
    return getAppliedTime(localTs.getTime()," 23:59:59");
  }

  private Timestamp getAppliedTime(long milliseconds, String filler) {
    String date = new java.sql.Date(milliseconds).toString();
    return Timestamp.valueOf(date + filler);
  }
/**
 * This is not called any longer.
 * Instead, JDBC connection url contains "&useTimezone=true&serverTimezone=UTC"
 */
  public Timestamp toUtcTime(Timestamp ts) {
    return toUtcTime(ts, TimeZone.getDefault());
  }

  public Timestamp toUtcTime(Timestamp ts, TimeZone from) {
    long time = ts.getTime();
    return new Timestamp(time - from.getOffset(time));
  }

  public Timestamp toLocalTime(Timestamp ts, TimeZone to) {
    long time = ts.getTime();
    return new Timestamp(time + to.getOffset(time));
  }

  public Timestamp toLocalTime(Timestamp ts) {
    return toLocalTime(ts, TimeZone.getDefault());
  }
}
