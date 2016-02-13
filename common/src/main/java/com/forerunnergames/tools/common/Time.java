/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
