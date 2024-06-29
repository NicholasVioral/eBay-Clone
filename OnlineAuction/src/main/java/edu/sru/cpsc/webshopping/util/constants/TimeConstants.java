package edu.sru.cpsc.webshopping.util.constants;

import java.time.format.DateTimeFormatter;

public final class TimeConstants {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

  private TimeConstants() {}
}
