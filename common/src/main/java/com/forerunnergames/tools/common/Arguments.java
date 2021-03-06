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

import com.google.common.collect.Iterables;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public final class Arguments
{
  private enum ArgumentStatus
  {
    BLANK ("is blank"),
    BLANK_ELEMENTS ("has blank elements"),
    EMPTY ("is empty"),
    EMPTY_ELEMENTS ("has empty elements"),
    NULL ("is null"),
    NULL_ELEMENTS ("has null elements"),
    NULL_KEYS ("has null keys"),
    NULL_VALUES ("has null values"),
    NULL_KEYS_AND_VALUES ("has null keys & null values");

    private final String message;

    private String getMessage ()
    {
      return message;
    }

    ArgumentStatus (final String message)
    {
      this.message = message;
    }
  }

  private enum BoundType
  {
    LOWER_EXCLUSIVE ("strictly greater than"),
    UPPER_EXCLUSIVE ("strictly less than"),
    LOWER_INCLUSIVE ("greater than or equal to"),
    UPPER_INCLUSIVE ("less than or equal to");

    private final String message;

    private String getMessage ()
    {
      return message;
    }

    BoundType (final String message)
    {
      this.message = message;
    }
  }

  /**
   * Checks if the specified Iterable has any null elements. The check will pass if the Iterable itself is null.
   *
   * @param iterable
   *          The Iterable to check, may be null.
   * @param iterableName
   *          The name of the Iterable to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the Iterable has any null elements.
   */
  public static void checkHasNoNullElements (final Iterable <?> iterable, final String iterableName)
  {
    if (iterable != null && Iterables.contains (iterable, null))
    {
      illegalArgument (iterableName, ArgumentStatus.NULL_ELEMENTS);
    }
  }

  /**
   * Checks if the specified Object array has any null elements. The check will pass if the Object array itself is null.
   *
   * @param array
   *          The Object array to check, may be null.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the Object array has any null elements.
   */
  public static void checkHasNoNullElements (final Object[] array, final String arrayName)
  {
    if (array != null && Arrays.asList (array).contains (null))
    {
      illegalArgument (arrayName, ArgumentStatus.NULL_ELEMENTS);
    }
  }

  /**
   * Checks if the specified Map has any null keys. The check will pass if the Map itself is null, or if the map has any
   * null values.
   *
   * @param map
   *          The Map to check, may be null.
   * @param mapName
   *          The name of the Map to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the Map has any null keys.
   */
  public static void checkHasNoNullKeys (final Map <?, ?> map, final String mapName)
  {
    if (hasNullKeys (map)) illegalArgument (mapName, ArgumentStatus.NULL_KEYS);
  }

  /**
   * Checks if the specified Map has any null values. The check will pass if the Map itself is null, or if the map has
   * any null keys.
   *
   * @param map
   *          The Map to check, may be null.
   * @param mapName
   *          The name of the Map to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the Map has any null values.
   */
  public static void checkHasNoNullValues (final Map <?, ?> map, final String mapName)
  {
    if (hasNullValues (map)) illegalArgument (mapName, ArgumentStatus.NULL_VALUES);
  }

  /**
   * Checks if the specified Map has any null keys or null values. The check will pass if the Map itself is null.
   *
   * @param map
   *          The Map to check, may be null.
   * @param mapName
   *          The name of the Map to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the Map has any null keys or null values.
   */
  public static void checkHasNoNullKeysOrValues (final Map <?, ?> map, final String mapName)
  {
    final boolean hasNullKeys = hasNullKeys (map);
    final boolean hasNullValues = hasNullValues (map);

    if (!hasNullKeys && !hasNullValues) return;

    if (hasNullKeys && hasNullValues)
    {
      illegalArgument (mapName, ArgumentStatus.NULL_KEYS_AND_VALUES);
    }
    else if (hasNullKeys)
    {
      illegalArgument (mapName, ArgumentStatus.NULL_KEYS);
    }
    else
    {
      illegalArgument (mapName, ArgumentStatus.NULL_VALUES);
    }
  }

  /**
   * Checks if the specified string collection has any null or empty elements. The check will pass if the string
   * collection itself is null or empty.
   *
   * @param strings
   *          The string collection to check, may be null.
   * @param collectionName
   *          The name of the string collection to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the string collection has any null or empty elements.
   */
  public static void checkHasNoNullOrEmptyElements (final Collection <String> strings, final String collectionName)
  {
    if (strings == null || strings.isEmpty ())
    {
      return;
    }

    for (final String s : strings)
    {
      if (s == null)
      {
        illegalArgument (collectionName, ArgumentStatus.NULL_ELEMENTS);
      }
      else if (s.isEmpty ())
      {
        illegalArgument (collectionName, ArgumentStatus.EMPTY_ELEMENTS);
      }
    }
  }

  /**
   * Checks if the specified String array has any null or empty elements. The check will pass if the String array itself
   * is null or empty.
   *
   * @param array
   *          The String array to check, may be null.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the String array has any null or empty elements.
   */
  public static void checkHasNoNullOrEmptyElements (final String[] array, final String arrayName)
  {
    if (array == null)
    {
      return;
    }

    checkHasNoNullOrEmptyElements (Arrays.asList (array), arrayName);
  }

  /**
   * Checks if the specified string collection has any null, empty, or blank (whitespace only) elements. The check will
   * pass if the string collection itself is null or empty.
   *
   * @param strings
   *          The string collection to check, may be null.
   * @param collectionName
   *          The name of the string collection to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the string collection has any null, empty, or blank (whitespace only) elements.
   */
  public static void checkHasNoNullOrEmptyOrBlankElements (final Collection <String> strings,
                                                           final String collectionName)
  {
    if (strings == null || strings.isEmpty ())
    {
      return;
    }

    for (final String s : strings)
    {
      if (s == null)
      {
        illegalArgument (collectionName, ArgumentStatus.NULL_ELEMENTS);
      }
      else if (s.isEmpty ())
      {
        illegalArgument (collectionName, ArgumentStatus.EMPTY_ELEMENTS);
      }
      else if (Strings.isWhitespace (s))
      {
        illegalArgument (collectionName, ArgumentStatus.BLANK_ELEMENTS);
      }
    }
  }

  /**
   * Checks if the specified String array has any null, empty, or blank (whitespace only) elements. The check will pass
   * if the String array itself is null or empty.
   *
   * @param array
   *          The String array to check, may be null.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the String array has any null, empty, or blank (whitespace only) elements.
   */
  public static void checkHasNoNullOrEmptyOrBlankElements (final String[] array, final String arrayName)
  {
    if (array == null)
    {
      return;
    }

    checkHasNoNullOrEmptyOrBlankElements (Arrays.asList (array), arrayName);
  }

  /**
   * Checks if the specified integer value is strictly less than 0.
   *
   * @param value
   *          The integer value to check.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to 0.
   */
  public static void checkIsNegative (final int value, final String valueName)
  {
    checkUpperExclusiveBound ((long) value, 0, valueName, "");
  }

  /**
   * Checks if the specified long value is strictly less than 0.
   *
   * @param value
   *          The long value to check.
   * @param valueName
   *          The name of the long value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to 0.
   */
  public static void checkIsNegative (final long value, final String valueName)
  {
    checkUpperExclusiveBound (value, 0, valueName, "");
  }

  /**
   * Checks if the specified floating point value is strictly less than 0.
   *
   * @param value
   *          The floating point value to check.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to 0.
   */
  public static void checkIsNegative (final float value, final String valueName)
  {
    checkUpperExclusiveBound ((double) value, 0, valueName, "");
  }

  /**
   * Checks if the specified double value is strictly less than 0.
   *
   * @param value
   *          The double value to check.
   * @param valueName
   *          The name of the double value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to 0.
   */
  public static void checkIsNegative (final double value, final String valueName)
  {
    checkUpperExclusiveBound (value, 0, valueName, "");
  }

  /**
   * Checks if the specified integer value is greater than or equal to 0.
   *
   * @param value
   *          The integer value to check.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than 0.
   */
  public static void checkIsNotNegative (final int value, final String valueName)
  {
    checkLowerInclusiveBound ((long) value, 0, valueName, "");
  }

  /**
   * Checks if the specified long value is greater than or equal to 0.
   *
   * @param value
   *          The long value to check.
   * @param valueName
   *          The name of the long value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than 0.
   */
  public static void checkIsNotNegative (final long value, final String valueName)
  {
    checkLowerInclusiveBound (value, 0, valueName, "");
  }

  /**
   * Checks if the specified floating point value is greater than or equal to 0.
   *
   * @param value
   *          The floating point value to check.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than 0.
   */
  public static void checkIsNotNegative (final float value, final String valueName)
  {
    checkLowerInclusiveBound ((double) value, 0, valueName, "");
  }

  /**
   * Checks if the specified double value is greater than or equal to 0.
   *
   * @param value
   *          The double value to check.
   * @param valueName
   *          The name of the double value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than 0.
   */
  public static void checkIsNotNegative (final double value, final String valueName)
  {
    checkLowerInclusiveBound (value, 0, valueName, "");
  }

  /**
   * Checks if the specified boolean condition evaluates to true.
   *
   * @param condition
   *          The boolean condition to check.
   * @param errorMessage
   *          The error message to include in the exception.
   *
   * @throws IllegalArgumentException
   *           If condition evaluates to false;
   */
  public static void checkIsTrue (final boolean condition, final String errorMessage)
  {
    if (!condition) throw new IllegalArgumentException (errorMessage);
  }

  /**
   * Checks if the specified boolean condition evaluates to false.
   *
   * @param condition
   *          The boolean condition to check.
   * @param errorMessage
   *          The error message to include in the exception.
   *
   * @throws IllegalArgumentException
   *           If condition evaluates to true;
   */
  public static void checkIsFalse (final boolean condition, final String errorMessage)
  {
    if (condition) throw new IllegalArgumentException (errorMessage);
  }

  /**
   * Checks if the specified object is null.
   *
   * @param <T>
   *          The type of object to check.
   * @param object
   *          The object to check.
   * @param objectName
   *          The name of the object to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If object is null.
   */
  public static <T> void checkIsNotNull (final T object, final String objectName)
  {
    if (object == null)
    {
      illegalArgument (objectName, ArgumentStatus.NULL);
    }
  }

  /**
   * Checks if the specified String is null or empty.
   *
   * @param s
   *          The String to check.
   * @param stringName
   *          The name of the String to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the String is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final String s, final String stringName)
  {
    if (s == null)
    {
      illegalArgument (stringName, ArgumentStatus.NULL);
    }
    else if (s.isEmpty ())
    {
      illegalArgument (stringName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified iterable is null or empty.
   *
   * @param <T>
   *          The type of iterable to check.
   * @param iterable
   *          The iterable to check.
   * @param iterableName
   *          The name of the iterable to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the iterable is null or empty.
   */
  public static <T> void checkIsNotNullOrEmpty (final Iterable <T> iterable, final String iterableName)
  {
    if (iterable == null)
    {
      illegalArgument (iterableName, ArgumentStatus.NULL);
    }
    else if (!iterable.iterator ().hasNext ())
    {
      illegalArgument (iterableName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified map is null or empty (has no key-value mappings).
   *
   * @param map
   *          The map to check.
   * @param mapName
   *          The name of the map to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the map is null or empty (has no key-value mappings).
   */
  public static void checkIsNotNullOrEmpty (final Map <?, ?> map, final String mapName)
  {
    if (map == null)
    {
      illegalArgument (mapName, ArgumentStatus.NULL);
    }
    else if (map.isEmpty ())
    {
      illegalArgument (mapName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final Object[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive integer array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final int[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive long array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final long[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive float array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final float[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive double array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final double[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive byte array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final byte[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive short array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final short[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive boolean array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final boolean[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified primitive character array is null or empty.
   *
   * @param array
   *          The array to check.
   * @param arrayName
   *          The name of the array to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (final char[] array, final String arrayName)
  {
    if (array == null)
    {
      illegalArgument (arrayName, ArgumentStatus.NULL);
    }
    else if (array.length == 0)
    {
      illegalArgument (arrayName, ArgumentStatus.EMPTY);
    }
  }

  /**
   * Checks if the specified String is null, empty, or blank (whitespace only).
   *
   * @param s
   *          The String to check.
   * @param stringName
   *          The name of the String to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If the String is null or empty, or blank (whitespace only).
   */
  public static void checkIsNotNullOrEmptyOrBlank (final String s, final String stringName)
  {
    if (s == null)
    {
      illegalArgument (stringName, ArgumentStatus.NULL);
    }
    else if (s.isEmpty ())
    {
      illegalArgument (stringName, ArgumentStatus.EMPTY);
    }
    else if (Strings.isWhitespace (s))
    {
      illegalArgument (stringName, ArgumentStatus.BLANK);
    }
  }

  /**
   * Checks if the specified integer value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final int value, final int lowerInclusiveBound, final String valueName)
  {
    checkLowerInclusiveBound ((long) value, (long) lowerInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified integer value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   * @param lowerInclusiveBoundName
   *          The name of the lower inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final int value,
                                               final int lowerInclusiveBound,
                                               final String valueName,
                                               final String lowerInclusiveBoundName)
  {
    checkLowerInclusiveBound ((long) value, (long) lowerInclusiveBound, valueName, lowerInclusiveBoundName);
  }

  /**
   * Checks if the specified long value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final long value, final long lowerInclusiveBound, final String valueName)
  {
    checkLowerInclusiveBound (value, lowerInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified long value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   * @param lowerInclusiveBoundName
   *          The name of the lower inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final long value,
                                               final long lowerInclusiveBound,
                                               final String valueName,
                                               final String lowerInclusiveBoundName)
  {
    if (value < lowerInclusiveBound)
    {
      boundsViolation (value, lowerInclusiveBound, valueName, lowerInclusiveBoundName, BoundType.LOWER_INCLUSIVE);
    }
  }

  /**
   * Checks if the specified floating point value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final float value,
                                               final float lowerInclusiveBound,
                                               final String valueName)
  {
    checkLowerInclusiveBound ((double) value, (double) lowerInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified floating point value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   * @param lowerInclusiveBoundName
   *          The name of the lower inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final float value,
                                               final float lowerInclusiveBound,
                                               final String valueName,
                                               final String lowerInclusiveBoundName)
  {
    checkLowerInclusiveBound ((double) value, (double) lowerInclusiveBound, valueName, lowerInclusiveBoundName);
  }

  /**
   * Checks if the specified double value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final double value,
                                               final double lowerInclusiveBound,
                                               final String valueName)
  {
    checkLowerInclusiveBound (value, lowerInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified double value is greater than or equal to lowerInclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param lowerInclusiveBound
   *          The lower inclusive bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   * @param lowerInclusiveBoundName
   *          The name of the lower inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly less than lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (final double value,
                                               final double lowerInclusiveBound,
                                               final String valueName,
                                               final String lowerInclusiveBoundName)
  {
    if (value < lowerInclusiveBound)
    {
      boundsViolation (value, lowerInclusiveBound, valueName, lowerInclusiveBoundName, BoundType.LOWER_INCLUSIVE);
    }
  }

  /**
   * Checks if the specified integer value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final int value, final int lowerExclusiveBound, final String valueName)
  {
    checkLowerExclusiveBound ((long) value, (long) lowerExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified integer value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   * @param lowerExclusiveBoundName
   *          The name of the lower exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final int value,
                                               final int lowerExclusiveBound,
                                               final String valueName,
                                               final String lowerExclusiveBoundName)
  {
    checkLowerExclusiveBound ((long) value, (long) lowerExclusiveBound, valueName, lowerExclusiveBoundName);
  }

  /**
   * Checks if the specified long value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final long value, final long lowerExclusiveBound, final String valueName)
  {
    checkLowerExclusiveBound (value, lowerExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified long value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   * @param lowerExclusiveBoundName
   *          The name of the lower exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final long value,
                                               final long lowerExclusiveBound,
                                               final String valueName,
                                               final String lowerExclusiveBoundName)
  {
    if (value <= lowerExclusiveBound)
    {
      boundsViolation (value, lowerExclusiveBound, valueName, lowerExclusiveBoundName, BoundType.LOWER_EXCLUSIVE);
    }
  }

  /**
   * Checks if the specified floating point value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final float value,
                                               final float lowerExclusiveBound,
                                               final String valueName)
  {
    checkLowerExclusiveBound ((double) value, (double) lowerExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified floating point value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   * @param lowerExclusiveBoundName
   *          The name of the lower exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final float value,
                                               final float lowerExclusiveBound,
                                               final String valueName,
                                               final String lowerExclusiveBoundName)
  {
    checkLowerExclusiveBound ((double) value, (double) lowerExclusiveBound, valueName, lowerExclusiveBoundName);
  }

  /**
   * Checks if the specified double value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final double value,
                                               final double lowerExclusiveBound,
                                               final String valueName)
  {
    checkLowerExclusiveBound (value, lowerExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified double value is strictly greater than lowerExclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param lowerExclusiveBound
   *          The exclusive lower bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   * @param lowerExclusiveBoundName
   *          The name of the lower exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is less than or equal to lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (final double value,
                                               final double lowerExclusiveBound,
                                               final String valueName,
                                               final String lowerExclusiveBoundName)
  {
    if (value <= lowerExclusiveBound)
    {
      boundsViolation (value, lowerExclusiveBound, valueName, lowerExclusiveBoundName, BoundType.LOWER_EXCLUSIVE);
    }
  }

  /**
   * Checks if the specified integer value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final int value, final int upperInclusiveBound, final String valueName)
  {
    checkUpperInclusiveBound ((long) value, (long) upperInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified integer value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   * @param upperInclusiveBoundName
   *          The name of the upper inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final int value,
                                               final int upperInclusiveBound,
                                               final String valueName,
                                               final String upperInclusiveBoundName)
  {
    checkUpperInclusiveBound ((long) value, (long) upperInclusiveBound, valueName, upperInclusiveBoundName);
  }

  /**
   * Checks if the specified long value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final long value, final long upperInclusiveBound, final String valueName)
  {
    checkUpperInclusiveBound (value, upperInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified long value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   * @param upperInclusiveBoundName
   *          The name of the upper inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final long value,
                                               final long upperInclusiveBound,
                                               final String valueName,
                                               final String upperInclusiveBoundName)
  {
    if (value > upperInclusiveBound)
    {
      boundsViolation (value, upperInclusiveBound, valueName, upperInclusiveBoundName, BoundType.UPPER_INCLUSIVE);
    }
  }

  /**
   * Checks if the specified floating point value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final float value,
                                               final float upperInclusiveBound,
                                               final String valueName)
  {
    checkUpperInclusiveBound ((double) value, (double) upperInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified floating point value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   * @param upperInclusiveBoundName
   *          The name of the upper inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final float value,
                                               final float upperInclusiveBound,
                                               final String valueName,
                                               final String upperInclusiveBoundName)
  {
    checkUpperInclusiveBound ((double) value, (double) upperInclusiveBound, valueName, upperInclusiveBoundName);
  }

  /**
   * Checks if the specified double value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final double value,
                                               final double upperInclusiveBound,
                                               final String valueName)
  {
    checkUpperInclusiveBound (value, upperInclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified double value is less than or equal to upperInclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param upperInclusiveBound
   *          The upper inclusive bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   * @param upperInclusiveBoundName
   *          The name of the upper inclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is strictly greater than upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (final double value,
                                               final double upperInclusiveBound,
                                               final String valueName,
                                               final String upperInclusiveBoundName)
  {
    if (value > upperInclusiveBound)
    {
      boundsViolation (value, upperInclusiveBound, valueName, upperInclusiveBoundName, BoundType.UPPER_INCLUSIVE);
    }
  }

  /**
   * Checks if the specified integer value is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final int value, final int upperExclusiveBound, final String valueName)
  {
    checkUpperExclusiveBound ((long) value, (long) upperExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified integer value is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The integer value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the integer value to check, must not be null.
   * @param upperExclusiveBoundName
   *          The name of the upper exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final int value,
                                               final int upperExclusiveBound,
                                               final String valueName,
                                               final String upperExclusiveBoundName)
  {
    checkUpperExclusiveBound ((long) value, (long) upperExclusiveBound, valueName, upperExclusiveBoundName);
  }

  /**
   * Checks if the specified long value is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final long value, final long upperExclusiveBound, final String valueName)
  {
    checkUpperExclusiveBound (value, upperExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified long is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The long value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the long value to check, must not be null.
   * @param upperExclusiveBoundName
   *          The name of the upper exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final long value,
                                               final long upperExclusiveBound,
                                               final String valueName,
                                               final String upperExclusiveBoundName)
  {
    if (value >= upperExclusiveBound)
    {
      boundsViolation (value, upperExclusiveBound, valueName, upperExclusiveBoundName, BoundType.UPPER_EXCLUSIVE);
    }
  }

  /**
   * Checks if the specified floating point value is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final float value,
                                               final float upperExclusiveBound,
                                               final String valueName)
  {
    checkUpperExclusiveBound ((double) value, (double) upperExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified floating point value is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The floating point value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the floating point value to check, must not be null.
   * @param upperExclusiveBoundName
   *          The name of the upper exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final float value,
                                               final float upperExclusiveBound,
                                               final String valueName,
                                               final String upperExclusiveBoundName)
  {
    checkUpperExclusiveBound ((double) value, (double) upperExclusiveBound, valueName, upperExclusiveBoundName);
  }

  /**
   * Checks if the specified double value is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final double value,
                                               final double upperExclusiveBound,
                                               final String valueName)
  {
    checkUpperExclusiveBound (value, upperExclusiveBound, valueName, "");
  }

  /**
   * Checks if the specified double is strictly less than upperExclusiveBound.
   *
   * @param value
   *          The double value to check.
   * @param upperExclusiveBound
   *          The upper exclusive bound.
   * @param valueName
   *          The name of the double value to check, must not be null.
   * @param upperExclusiveBoundName
   *          The name of the upper exclusive bound, must not be null.
   *
   * @throws IllegalArgumentException
   *           If value is greater than or equal to upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (final double value,
                                               final double upperExclusiveBound,
                                               final String valueName,
                                               final String upperExclusiveBoundName)
  {
    if (value >= upperExclusiveBound)
    {
      boundsViolation (value, upperExclusiveBound, valueName, upperExclusiveBoundName, BoundType.UPPER_EXCLUSIVE);
    }
  }

  private static boolean hasNullKeys (final Map <?, ?> map)
  {
    return map != null && Iterables.contains (map.keySet (), null);
  }

  private static boolean hasNullValues (final Map <?, ?> map)
  {
    return map != null && Iterables.contains (map.values (), null);
  }

  private static void boundsViolation (final Number value,
                                       final Number bound,
                                       final String valueName,
                                       final String boundName,
                                       final BoundType boundType) throws IllegalArgumentException
  {
    final int stackLevel = getStackLevelOfFirstClassOutsideThisClass ();

    throw new IllegalArgumentException ("Argument \"" + valueName + "\" [value: " + value + "]" + " must be "
            + boundType.getMessage () + " " + (boundName.isEmpty () ? "" : "\"" + boundName + "\" ") + "[value: "
            + bound + "]" + " when invoking [" + Classes.getClassName (stackLevel) + "."
            + Methods.getMethodName (stackLevel) + "].");
  }

  private static int getStackLevelOfFirstClassOutsideThisClass ()
  {
    final int maxStackLevel = getMaxStackLevel ();
    final String thisClassName = Classes.getClassName (0);

    for (int stackLevel = 1; stackLevel <= maxStackLevel; ++stackLevel)
    {
      if (!thisClassName.equals (Classes.getClassName (stackLevel))) return stackLevel - 1;
    }

    return maxStackLevel - 1;
  }

  private static int getMaxStackLevel ()
  {
    return Thread.currentThread ().getStackTrace ().length - 1;
  }

  private static void illegalArgument (final String argumentName, final ArgumentStatus argumentStatus)
          throws IllegalArgumentException
  {
    final int stackLevel = getStackLevelOfFirstClassOutsideThisClass ();

    throw new IllegalArgumentException (
            "\nLocation: " + Classes.getClassName (stackLevel) + "." + Methods.getMethodName (stackLevel)
                    + "\nReason:   argument '" + argumentName + "' " + argumentStatus.getMessage ());
  }

  private Arguments ()
  {
    Classes.instantiationNotAllowed ();
  }
}
