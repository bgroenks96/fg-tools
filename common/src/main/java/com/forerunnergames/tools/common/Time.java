package com.forerunnergames.tools.common;

/**
 * Utility class for time-related functionality.
 */
public class Time
{
  /**
   * Calculate the number of milliseconds based on the specified number of seconds. Increases readability (e.g.
   * Thread.sleep (Time.Seconds (3)), rather than Thread.sleep (3000)).
   *
   * @param seconds
   *          The number of seconds to convert to milliseconds.
   *
   * @return The number of milliseconds, calculated from the specified number of seconds.
   */
  public static long Seconds (final int seconds)
  {
    return seconds * 1000L;
  }

  /**
   * Calculate the number of milliseconds based on the specified number of seconds. Increases readability (e.g.
   * Thread.sleep (Time.Seconds (3.25)), rather than Thread.sleep (3250))
   *
   * @param seconds
   *          The number of seconds to convert to milliseconds.
   *
   * @return The number of milliseconds, calculated from the specified number of seconds.
   */
  public static long Seconds (final double seconds)
  {
    return (long) (seconds * 1000.0);
  }

  private Time ()
  {
    Classes.instantiationNotAllowed ();
  }
}
