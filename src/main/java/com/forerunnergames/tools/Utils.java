// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools;

import com.google.common.collect.Multimap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 
 * @author Aaron Mahan
 */
public class Utils
{
  // Begin public interface

  /**
   * Finds the key from its associated value in a multiMap.
   * 
   * @param value The value whose key is to be searched for.
   * @param multiMap The multiMap to search in.
   * 
   * @return The key, if found; Integer.MIN_VALUE otherwise.
   */
  public static int findKeyFromValue (int value,
                                      Multimap <Integer, Integer> multiMap)
  {
    Arguments.checkIsNotNull (value,    "value");
    Arguments.checkIsNotNull (multiMap, "multiMap");

    boolean foundKey = false;

    java.util.Iterator <Integer> keyIterator = multiMap.keySet().iterator();

    Integer boxedKey   = Integer.MIN_VALUE;
    Integer boxedValue = Integer.valueOf (value);

    while (keyIterator.hasNext() && ! foundKey)
    {
      boxedKey = keyIterator.next();

      if (multiMap.containsEntry (boxedKey, boxedValue))
      {
        foundKey = true;
      }
    }

    assert foundKey;

    return boxedKey.intValue();
  }

  /**
   * Checks whether the double n is equal to a mathematical integer.
   * <br/><br/>
   * Note: If n is infinite, or NaN, this method will return false. If n is
   *       positive or negative zero, this method will return true.
   * 
   * @param n The double to check.
   * 
   * @return True if n is equal to a mathematical integer, false otherwise.
   */
  public static boolean isInteger (double n)
  {
    return ! Double.isInfinite (n) && ! Double.isNaN (n) && Math.floor (n) == n;
  }


  /**
   * Prints the specified text to the system console.
   * 
   * @param text The text to print to the system console, must not be null or
   *             empty.
   */
  public static void printToConsole (String text)
  {
    Arguments.checkIsNotNullOrEmpty (text, "Text");

    System.out.print (text);
  }

  /**
   * Resolves the external IP address of the local machine as a string
   * representation in the format "###.###.###.###" (quotes not included). Each
   * octet may be anywhere from 1 to 3 numerical digits in length.
   * 
   * @return A string representation of the external IP address of the local
   *         machine.
   * 
   * @throws IOException If the external IP address of the local machine could
   *                     now be found.
   */
  public String resolveExternalIpAddress() throws IOException
  {
    URL externalIpURL = new URL (Utils.EXTERNAL_IP_API_URL);

    BufferedReader bufferedReader = new BufferedReader (
                    new InputStreamReader (externalIpURL.openStream()));

    return bufferedReader.readLine();
  }

  /**
   * Cause the current thread to sleep for the specified number of milliseconds.
   * <br/><br/>
   * Note: This function is interrupt-friendly (i.e. allows interruption).
   * 
   * @param milliseconds The number of milliseconds to sleep for, must be
   *                     greater than zero.
   * 
   * @throws IllegalArgumentException If milliseconds is less than or equal to 
   *                                  zero.
   * 
   * @see Time
   */
  public static void sleep (long milliseconds)
  {
    Arguments.checkLowerExclusiveBound (milliseconds, 0, "Milliseconds");
    
    try
    {
      Thread.sleep (milliseconds);
    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }

  // End public interface

  // Begin private interface

  private Utils()
  {
    ClassUtils.instantiationNotAllowed();
  }

  private static final String EXTERNAL_IP_API_URL =
          "http://api.externalip.net/ip/";
  // End private interface
}
