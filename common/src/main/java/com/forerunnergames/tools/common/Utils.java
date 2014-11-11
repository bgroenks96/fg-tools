package com.forerunnergames.tools.common;

import com.google.common.collect.Multimap;

public class Utils
{
  /**
   * Finds the key from its associated value in a multiMap.
   * 
   * @param value The value whose key is to be searched for.
   * @param multiMap The multiMap to search in.
   * 
   * @return The key, if found; Integer.MIN_VALUE otherwise.
   */
  public static int findKeyFromValue (int value, Multimap <Integer, Integer> multiMap)
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

      if (multiMap.containsEntry (boxedKey, boxedValue)) foundKey = true;
    }

    assert foundKey;

    return boxedKey.intValue();
  }

  /**
   * Checks whether the double n is equal to a mathematical integer.
   *
   * If n is infinite, or NaN, this method will return false. If n is positive or negative zero, this method will
   * return true.
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
   * Cause the current thread to sleep for the specified number of milliseconds.
   *
   * This method is interrupt-friendly (i.e. allows interruption).
   * 
   * @param milliseconds The number of milliseconds to sleep for, must be greater than zero.
   * 
   * @throws IllegalArgumentException If milliseconds is less than or equal to  zero.
   * 
   * @see {@link Time}
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

  private Utils()
  {
    Classes.instantiationNotAllowed();
  }
}