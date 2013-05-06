// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

/**
 * Static Utility Class for Time-related Functionality
 * 
 * @author Aaron Mahan
 */
public class Time
{
  // Begin public interface

  /**
   * Calculate the number of milliseconds based on the specified number of
   * seconds.
   * <br/><br/>
   * Note: Increases readability (e.g. Thread.sleep (Time.Seconds (3)), rather
   *       than Thread.sleep (3000)).
   * 
   * @param seconds The number of seconds to convert to milliseconds.
   * 
   * @return The number of milliseconds, calculated from the specified number of
   *         seconds.
   */
  public static long Seconds (int seconds)
  {
    return seconds * 1000;
  }

  /**
   * Calculate the number of milliseconds based on the specified number of
   * seconds.
   * <br/><br/>
   * Note: Increases readability (e.g. Thread.sleep (Time.Seconds (3.25)),
   *       rather than Thread.sleep (3250))
   * 
   * @param seconds The number of seconds to convert to milliseconds.
   * 
   * @return The number of milliseconds, calculated from the specified number of
   *         seconds.
   */
  public static long Seconds (double seconds)
  {
    return (long) (seconds * 1000.0);
  }

  // End public interface

  // Begin private interface

  private Time()
  {
    ClassUtils.instantiationNotAllowed();
  }

  // End private interface
}
