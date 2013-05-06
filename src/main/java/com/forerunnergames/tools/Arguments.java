// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

import java.util.Arrays;
import java.util.Collection;

/**
 * Static Utility Class for Argument-Checking-related Functionality
 *
 * @author Aaron Mahan
 */
public class Arguments
{
  /**
   * Checks if the specified collection has any null elements.
   * <br/><br/>
   * Note: The check will pass if the collection itself is null.
   *
   * @param collection The collection to check, may be null.
   * @param collectionName The name of the collection to check, may be null.
   *
   * @throws IllegalArgumentException If the collection has any null elements.
   */
  public static void checkHasNoNullElements (Collection <?> collection,
                                             String collectionName)
  {
    if (collection != null && collection.contains (null))
    {
      Arguments.illegalArgument (collectionName, ArgumentStatus.NULL_ELEMENTS);
    }
  }

  /**
   * Checks if the specified Object array has any null elements.
   * <br/><br/>
   * Note: The check will pass if the Object array itself is null.
   *
   * @param array The Object array to check, may be null.
   * @param arrayName The name of the array to check, may be null.
   *
   * @throws IllegalArgumentException If the Object array has any null elements.
   */
  public static void checkHasNoNullElements (Object[] array, String arrayName)
  {
    if (array != null && Arrays.asList(array).contains (null))
    {
      Arguments.illegalArgument (arrayName, ArgumentStatus.NULL_ELEMENTS);
    }
  }

  /**
   * Checks if the specified string collection has any null or empty elements.
   * <br/><br/>
   * Note: The check will pass if the string collection itself is null or empty.
   *
   * @param strings The string collection to check, may be null.
   * @param collectionName The name of the string collection to check, may be
   *                       null.
   *
   * @throws IllegalArgumentException If the string collection has any null or
   *                                  empty elements.
   */
  public static void checkHasNoNullOrEmptyElements (
          Collection <String> strings, String collectionName)
  {
    if (strings != null && ! strings.isEmpty())
    {
      ArgumentStatus argumentStatus = ArgumentStatus.GOOD;

      for (String s : strings)
      {
        if (s == null)
        {
          argumentStatus = ArgumentStatus.NULL_ELEMENTS;

          break;
        }
        else if (s.isEmpty())
        {
          argumentStatus = ArgumentStatus.EMPTY_ELEMENTS;

          break;
        }
      }

      if (argumentStatus != Arguments.ArgumentStatus.GOOD)
      {
        Arguments.illegalArgument (collectionName, argumentStatus);
      }
    }
  }

  /**
   * Checks if the specified String array has any null or empty elements.
   * <br/><br/>
   * Note: The check will pass if the String array itself is null or empty.
   *
   * @param array The String array to check, may be null.
   * @param arrayName The name of the array to check, may be null.
   *
   * @throws IllegalArgumentException If the String array has any null or empty
   *                                  elements.
   */
  public static void checkHasNoNullOrEmptyElements (String[] array,
                                                    String arrayName)
  {
    if (array != null && array.length > 0)
    {
      ArgumentStatus argumentStatus = ArgumentStatus.GOOD;

      for (int i = 0; i < array.length; ++i)
      {
        if (array [i] == null)
        {
          argumentStatus = ArgumentStatus.NULL_ELEMENTS;

          break;
        }
        else if (array[i].isEmpty())
        {
          argumentStatus = ArgumentStatus.EMPTY_ELEMENTS;

          break;
        }
      }

      if (argumentStatus != Arguments.ArgumentStatus.GOOD)
      {
        Arguments.illegalArgument (arrayName, argumentStatus);
      }
    }
  }

  /**
   * Checks if the specified string collection has any null, empty, or
   * blank (whitespace only) elements.
   * <br/><br/>
   * Note: The check will pass if the string collection itself is null or empty.
   *
   * @param strings The string collection to check, may be null.
   * @param collectionName The name of the string collection to check, may be
   *                       null.
   *
   * @throws IllegalArgumentException If the string collection has any null,
   *                                  empty, or blank (whitespace only)
   *                                  elements.
   */
  public static void checkHasNoNullOrEmptyOrBlankElements (
          Collection <String> strings, String collectionName)
  {
    if (strings != null && ! strings.isEmpty())
    {
      ArgumentStatus argumentStatus = ArgumentStatus.GOOD;

      for (String s : strings)
      {
        if (s == null)
        {
          argumentStatus = ArgumentStatus.NULL_ELEMENTS;

          break;
        }
        else if (s.isEmpty())
        {
          argumentStatus = ArgumentStatus.EMPTY_ELEMENTS;

          break;
        }
        else if (StringUtils.isWhitespace (s))
        {
          argumentStatus = ArgumentStatus.BLANK_ELEMENTS;

          break;
        }
      }

      if (argumentStatus != Arguments.ArgumentStatus.GOOD)
      {
        Arguments.illegalArgument (collectionName, argumentStatus);
      }
    }
  }

  /**
   * Checks if the specified String array has any null, empty, or
   * blank (whitespace only) elements.
   * <br/><br/>
   * Note: The check will pass if the String array itself is null or empty.
   *
   * @param array The String array to check, may be null.
   * @param arrayName The name of the array to check, may be null.
   *
   * @throws IllegalArgumentException If the String array has any null, empty,
   *                                  or blank (whitespace only) elements.
   */
  public static void checkHasNoNullOrEmptyOrBlankElements (String[] array,
                                                           String arrayName)
  {
    if (array != null && array.length > 0)
    {
      ArgumentStatus argumentStatus = ArgumentStatus.GOOD;

      for (int i = 0; i < array.length; ++i)
      {
        if (array [i] == null)
        {
          argumentStatus = ArgumentStatus.NULL_ELEMENTS;

          break;
        }
        else if (array[i].isEmpty())
        {
          argumentStatus = ArgumentStatus.EMPTY_ELEMENTS;

          break;
        }
        else if (StringUtils.isWhitespace (array[i]))
        {
          argumentStatus = ArgumentStatus.BLANK_ELEMENTS;

          break;
        }
      }

      if (argumentStatus != Arguments.ArgumentStatus.GOOD)
      {
        Arguments.illegalArgument (arrayName, argumentStatus);
      }
    }
  }


  /**
   * Checks if the specified integer is greater than or equal to 0.
   *
   * @param i The integer to check.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If i is strictly less than 0.
   */
  public static void checkIsNotNegative (int i, String variableName)
  {
    if (i < 0)
    {
      Arguments.illegalArgument (variableName,
                                 Arguments.ArgumentStatus.NEGATIVE);
    }
  }

  /**
   * Checks if the specified boolean statement evaluates to false.
   * 
   * @param statement The boolean statement to check.
   * @param errorMessage The error message to include in the exception.
   * 
   * @throws IllegalArgumentException If statement evaluates to true;
   */
  public static void checkIsFalse (boolean statement, String errorMessage)
  {
    if (statement == true)
    {
      throw new IllegalArgumentException (errorMessage);
    }
  }

  /**
   * Checks if the specified object is null.
   *
   * @param <T> The type of object to check.
   * @param object The object to check.
   * @param objectName The name of the object to check, may be null.
   *
   * @throws IllegalArgumentException If object is null.
   */
  public static <T> void checkIsNotNull (T object, String objectName)
  {
    if (object == null)
    {
      Arguments.illegalArgument (objectName, Arguments.ArgumentStatus.NULL);
    }
  }

  /**
   * Checks if the specified String is null or empty.
   *
   * @param s The String to check.
   * @param stringName The name of the String to check, may be null.
   *
   * @throws IllegalArgumentException If the String is null or empty.
   */
  public static void checkIsNotNullOrEmpty (String s, String stringName)
  {
    Arguments.ArgumentStatus argumentStatus = Arguments.ArgumentStatus.GOOD;

    if (s == null)
    {
      argumentStatus = Arguments.ArgumentStatus.NULL;
    }
    else if (s.isEmpty())
    {
      argumentStatus = Arguments.ArgumentStatus.EMPTY;
    }

    if (argumentStatus != Arguments.ArgumentStatus.GOOD)
    {
      Arguments.illegalArgument (stringName, argumentStatus);
    }
  }

  /**
   * Checks if the specified collection is null or empty.
   *
   * @param <T> The type of collection to check.
   * @param collection The collection to check.
   * @param collectionName The name of the collection to check, may be null.
   *
   * @throws IllegalArgumentException If the collection is null or empty.
   */
  public static <T> void checkIsNotNullOrEmpty (Collection <T> collection,
                                                String collectionName)
  {
    Arguments.ArgumentStatus argumentStatus = Arguments.ArgumentStatus.GOOD;

    if (collection == null)
    {
      argumentStatus = Arguments.ArgumentStatus.NULL;
    }
    else if (collection.isEmpty())
    {
      argumentStatus = Arguments.ArgumentStatus.EMPTY;
    }

    if (argumentStatus != Arguments.ArgumentStatus.GOOD)
    {
      Arguments.illegalArgument (collectionName, argumentStatus);
    }
  }

  /**
   * Checks if the specified array is null or empty.
   *
   * @param array The array to check.
   * @param arrayName The name of the array to check, may be null.
   *
   * @throws IllegalArgumentException If array is null or empty.
   */
  public static void checkIsNotNullOrEmpty (Object[] array, String arrayName)
  {
    Arguments.ArgumentStatus argumentStatus = Arguments.ArgumentStatus.GOOD;

    if (array == null)
    {
      argumentStatus = Arguments.ArgumentStatus.NULL;
    }
    else if (array.length == 0)
    {
      argumentStatus = Arguments.ArgumentStatus.EMPTY;
    }

    if (argumentStatus != Arguments.ArgumentStatus.GOOD)
    {
      Arguments.illegalArgument (arrayName, argumentStatus);
    }
  }


  /**
   * Checks if the specified String is null, empty, or blank (whitespace only).
   *
   * @param s The String to check.
   * @param stringName The name of the String to check, may be null.
   *
   * @throws IllegalArgumentException If the String is null or empty, or
   *                                  blank (whitespace only).
   */
  public static void checkIsNotNullOrEmptyOrBlank (String s, String stringName)
  {
    Arguments.ArgumentStatus argumentStatus = Arguments.ArgumentStatus.GOOD;

    if (s == null)
    {
      argumentStatus = Arguments.ArgumentStatus.NULL;
    }
    else if (s.isEmpty())
    {
      argumentStatus = Arguments.ArgumentStatus.EMPTY;
    }
    else if (StringUtils.isWhitespace (s))
    {
      argumentStatus = Arguments.ArgumentStatus.BLANK;
    }

    if (argumentStatus != Arguments.ArgumentStatus.GOOD)
    {
      Arguments.illegalArgument (stringName, argumentStatus);
    }
  }

  /**
   * Checks if the specified boolean statement evaluates to true.
   * 
   * @param statement The boolean statement to check.
   * @param errorMessage The error message to include in the exception.
   * 
   * @throws IllegalArgumentException If statement evaluates to false;
   */
  public static void checkIsTrue (boolean statement, String errorMessage)
  {
    if (statement == false)
    {
      throw new IllegalArgumentException (errorMessage);
    }
  }

  /**
   * Checks if the specified integer is greater than or equal to
   * lowerInclusiveBound.
   *
   * @param i The integer to check.
   * @param lowerInclusiveBound The inclusive lower bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If i is strictly less than
   *                                  lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (int i, int lowerInclusiveBound,
                                               String variableName)
  {
    if (i < lowerInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be greater " +
              "than or equal to " + lowerInclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is greater than or equal to
   * lowerInclusiveBound.
   *
   * @param i The integer to check.
   * @param lowerInclusiveBound The lower inclusive bound.
   * @param variableName The name of the variable to check, may be null.
   * @param lowerInclusiveBoundVariableName The name of the lower inclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If i is strictly less than
   *                                  lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (int i, int lowerInclusiveBound,
          String variableName, String lowerInclusiveBoundVariableName)
  {
    if (i < lowerInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + i + "]" +
              " must be greater than or equal to " +
              lowerInclusiveBoundVariableName + " [value: " +
              lowerInclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is greater than or equal to
   * lowerInclusiveBound.
   *
   * @param l The long to check.
   * @param lowerInclusiveBound The inclusive lower bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If l is strictly less than
   *                                  lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (long l, long lowerInclusiveBound,
                                               String variableName)
  {
    if (l < lowerInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be greater " +
              "than or equal to " + lowerInclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is greater than or equal to
   * lowerInclusiveBound.
   *
   * @param l The long to check.
   * @param lowerInclusiveBound The lower inclusive bound.
   * @param variableName The name of the variable to check, may be null.
   * @param lowerInclusiveBoundVariableName The name of the lower inclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If l is strictly less than
   *                                  lowerInclusiveBound.
   */
  public static void checkLowerInclusiveBound (long l, long lowerInclusiveBound,
          String variableName, String lowerInclusiveBoundVariableName)
  {
    if (l < lowerInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + l + "]" +
              " must be greater than or equal to " +
              lowerInclusiveBoundVariableName + " [value: " +
              lowerInclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is strictly greater than
   * lowerExclusiveBound.
   *
   * @param i The integer to check.
   * @param lowerExclusiveBound The exclusive lower bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If i is less than or equal to
   *                                  lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (int i, int lowerExclusiveBound,
                                               String variableName)
  {
    if (i <= lowerExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be strictly " +
              "greater than " + lowerExclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is strictly greater than
   * lowerExclusiveBound.
   *
   * @param i The integer to check.
   * @param lowerExclusiveBound The exclusive lower bound.
   * @param variableName The name of the variable to check, may be null.
   * @param lowerExclusiveBoundVariableName The name of the lower exclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If i is less than or equal to
   *                                  lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (int i, int lowerExclusiveBound,
          String variableName, String lowerExclusiveBoundVariableName)
  {
    if (i <= lowerExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + i + "]" +
              " must be strictly greater than " +
              lowerExclusiveBoundVariableName + " [value: " +
              lowerExclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is strictly greater than lowerExclusiveBound.
   *
   * @param l The long to check.
   * @param lowerExclusiveBound The exclusive lower bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If l is less than or equal to
   *                                  lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (long l, long lowerExclusiveBound,
                                               String variableName)
  {
    if (l <= lowerExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be strictly " +
              "greater than " + lowerExclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is strictly greater than lowerExclusiveBound.
   *
   * @param l The long to check.
   * @param lowerExclusiveBound The exclusive lower bound.
   * @param variableName The name of the variable to check, may be null.
   * @param lowerExclusiveBoundVariableName The name of the lower exclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If l is less than or equal to
   *                                  lowerExclusiveBound.
   */
  public static void checkLowerExclusiveBound (long l, long lowerExclusiveBound,
          String variableName, String lowerExclusiveBoundVariableName)
  {
    if (l <= lowerExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + l + "]" +
              " must be strictly greater than " +
              lowerExclusiveBoundVariableName + " [value: " +
              lowerExclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is less than or equal to
   * upperInclusiveBound.
   *
   * @param i The integer to check.
   * @param upperInclusiveBound The inclusive upper bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If i is strictly greater than
   *                                  upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (int i, int upperInclusiveBound,
                                               String variableName)
  {
    if (i > upperInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be less than " +
              "or equal to " + upperInclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is less than or equal to
   * upperInclusiveBound.
   *
   * @param i The integer to check.
   * @param upperInclusiveBound The upper inclusive bound.
   * @param variableName The name of the variable to check, may be null.
   * @param upperInclusiveBoundVariableName The name of the upper inclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If i is strictly greater than
   *                                  upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (int i, int upperInclusiveBound,
          String variableName, String upperInclusiveBoundVariableName)
  {
    if (i > upperInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + i + "]" +
              " must be less than or equal to " +
              upperInclusiveBoundVariableName + " [value: " +
              upperInclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is less than or equal to upperInclusiveBound.
   *
   * @param l The long to check.
   * @param upperInclusiveBound The upper inclusive bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If l is strictly greater than
   *                                  upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (long l, long upperInclusiveBound,
                                               String variableName)
  {
    if (l > upperInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be less than " +
              "or equal to " + upperInclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is less than or equal to upperInclusiveBound.
   *
   * @param l The long to check.
   * @param upperInclusiveBound The upper inclusive bound.
   * @param variableName The name of the variable to check, may be null.
   * @param upperInclusiveBoundVariableName The name of the upper inclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If l is strictly greater than
   *                                  upperInclusiveBound.
   */
  public static void checkUpperInclusiveBound (long l, int upperInclusiveBound,
          String variableName, String upperInclusiveBoundVariableName)
  {
    if (l > upperInclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + l + "]" +
              " must be less than or equal to " +
              upperInclusiveBoundVariableName + " [value: " +
              upperInclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is strictly less than upperExclusiveBound.
   *
   * @param i The integer to check.
   * @param upperExclusiveBound The upper exclusive bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If i is greater than or equal to
   *                                  upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (int i, int upperExclusiveBound,
                                               String variableName)
  {
    if (i >= upperExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be strictly " +
              "less than " + upperExclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified integer is strictly less than upperExclusiveBound.
   *
   * @param i The integer to check.
   * @param upperExclusiveBound The upper exclusive bound.
   * @param variableName The name of the variable to check, may be null.
   * @param upperExclusiveBoundVariableName The name of the upper exclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If i is greater than or equal to
   *                                  upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (int i, int upperExclusiveBound,
          String variableName, String upperExclusiveBoundVariableName)
  {
    if (i >= upperExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + i + "]" +
              " must be strictly less than " + upperExclusiveBoundVariableName +
              " [value: " + upperExclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is strictly less than upperExclusiveBound.
   *
   * @param l The long to check.
   * @param upperExclusiveBound The upper exclusive bound.
   * @param variableName The name of the variable to check, may be null.
   *
   * @throws IllegalArgumentException If l is greater than or equal to
   *                                  upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (long l, long upperExclusiveBound,
                                               String variableName)
  {
    if (l >= upperExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " must be strictly " +
              "less than " + upperExclusiveBound + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  /**
   * Checks if the specified long is strictly less than upperExclusiveBound.
   *
   * @param l The long to check.
   * @param upperExclusiveBound The upper exclusive bound.
   * @param variableName The name of the variable to check, may be null.
   * @param upperExclusiveBoundVariableName The name of the upper exclusive
   *                                        bound variable, may be null.
   *
   * @throws IllegalArgumentException If l is greater than or equal to
   *                                  upperExclusiveBound.
   */
  public static void checkUpperExclusiveBound (long l, long upperExclusiveBound,
          String variableName, String upperExclusiveBoundVariableName)
  {
    if (l >= upperExclusiveBound)
    {
      throw new IllegalArgumentException (variableName + " [value: " + l + "]" +
              " must be strictly less than " + upperExclusiveBoundVariableName +
              " [value: " + upperExclusiveBound + "]" + " when invoking " +
              ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1) +
              ".");
    }
  }

  private static void illegalArgument (String argumentName,
                                       Arguments.ArgumentStatus argumentStatus)
          throws IllegalArgumentException
  {
    throw new IllegalArgumentException ("\nLocation: " +
            ClassUtils.getClassName (2) + "." + ClassUtils.getMethodName (2) +
            "\nReason:   argument '" + argumentName + "' " +
            argumentStatus.getName());
  }

  private enum ArgumentStatus
  {
    BLANK          ("is blank"),
    BLANK_ELEMENTS ("has blank elements"),
    EMPTY          ("is empty"),
    EMPTY_ELEMENTS ("has empty elements"),
    GOOD           (""),
    NEGATIVE       ("is negative"),
    NULL           ("is null"),
    NULL_ELEMENTS  ("has null elements");

    private String toString;

    private String getName()
    {
      return this.toString;
    }

    private ArgumentStatus (String toString)
    {
      this.toString = toString;
    }
  }
}
