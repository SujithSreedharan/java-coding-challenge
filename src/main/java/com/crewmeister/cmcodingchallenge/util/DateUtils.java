package com.crewmeister.cmcodingchallenge.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
  /**
   * Validates the passed in date to be yyyy-MM-dd
   * @param inDate - passed in date
   * @return true if the passed in string is valid
   */
  public static boolean isValidDate(final String inDate) {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    try {
      dateFormat.parse(inDate.trim());
    } catch (ParseException pe) {
      return false;
    }
    return true;
  }
}
