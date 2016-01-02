package com.forerunnergames.tools.common;

import com.google.common.base.Optional;
import com.google.common.collect.Multimap;

public final class Utils
{
  /**
   * Finds the key from its associated value in a multiMap.
   *
   * @param value
   *          The value whose key is to be searched for.
   * @param multiMap
   *          The multiMap to search in.
   *
   * @return The key, if found; Integer.MIN_VALUE otherwise.
   */
  public static int findKeyFromValue (final int value, Multimap <Integer, Integer> multiMap)
  {
    Arguments.checkIsNotNull (value, "value");
    Arguments.checkIsNotNull (multiMap, "multiMap");

    boolean foundKey = false;

    java.util.Iterator <Integer> keyIterator = multiMap.keySet ().iterator ();

    Integer boxedKey = Integer.MIN_VALUE;
    Integer boxedValue = Integer.valueOf (value);

    while (keyIterator.hasNext () && !foundKey)
    {
      boxedKey = keyIterator.next ();

      if (multiMap.containsEntry (boxedKey, boxedValue)) foundKey = true;
    }

    assert foundKey;

    return boxedKey;
  }

  /**
   * Checks whether the double n is equal to a mathematical integer.
   * <p/>
   * If n is infinite, or NaN, this method will return false. If n is positive or negative zero, this method will return
   * true.
   *
   * @param n
   *          The double to check.
   *
   * @return True if n is equal to a mathematical integer, false otherwise.
   */
  public static boolean isInteger (final double n)
  {
    return !Double.isInfinite (n) && !Double.isNaN (n) && Math.floor (n) == n;
  }

  /**
   * Cause the current thread to sleep for the specified number of milliseconds.
   * <p/>
   * This method is interrupt-friendly (i.e. allows interruption).
   *
   * @param milliseconds
   *          The number of milliseconds to sleep for, must be greater than zero.
   *
   * @throws IllegalArgumentException
   *           If milliseconds is less than or equal to zero.
   * @see Time
   */
  public static void sleep (final long milliseconds)
  {
    Arguments.checkLowerExclusiveBound (milliseconds, 0, "Milliseconds");

    try
    {
      Thread.sleep (milliseconds);
    }
    catch (InterruptedException e)
    {
      Thread.currentThread ().interrupt ();
    }
  }

  /**
   * Attempts to cast the specified object to the specified type, returning an {@link Optional} to avoid
   * {@link ClassCastException} & {@link NullPointerException} in cases where the cast is not possible.
   *
   * @param object
   *          The object to cast.
   * @param objectType
   *          The class of the type to cast the specified object to.
   * @param <T>
   *          The type to cast the specified object to.
   *
   * @return An {@link Optional} that will contain the casted object if the specified object was successfully able to be
   *         cast to the specified type, i.e., {@link Optional#isPresent()} will return true & {@link Optional#get ()}
   *         will return the cast object, otherwise {@link Optional#isPresent()} will return false &
   *         {@link Optional#get()} will throw an {@link IllegalStateException}.
   */
  public static <T> Optional <T> optionalCast (final Object object, final Class <T> objectType)
  {
    Arguments.checkIsNotNull (objectType, "objectType");

    return objectType.isInstance (object) ? Optional.of (objectType.cast (object)) : Optional.<T> absent ();
  }

  private Utils ()
  {
    Classes.instantiationNotAllowed ();
  }
}
