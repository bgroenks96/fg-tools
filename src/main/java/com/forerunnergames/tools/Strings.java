// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Static Utility Class for String-related Functionality
 * 
 * @author Aaron Mahan <aaron@forerunnergames.com>
 */
public final class Strings
{
  /*
   * Gets the correct English article ("a" or "an") for the word proceeding it.
   * 
   * @param nextWord The word proceeding the article ("a" or "an"), must not be
   * null, empty, or blank (whitespace only).
   * 
   * @return The correct English article ("a" or "an") for the word proceeding
   * it. "an" if nextWord stars with a vowel, "a" otherwise. Special case:
   * if nextWord is simply "u", it will return "a". Consider the following
   * example: "An umbrella starts with a u." (correct) vs
   * "An umbrella starts with an u." (incorrect)
   */
  public static String aOrAn (String nextWord)
  {
    Arguments.checkIsNotNullOrEmptyOrBlank (nextWord, "word");

    String aOrAn = "a";

    String nextWordLowerCase = nextWord.toLowerCase (Locale.ENGLISH);
    
    if (nextWordLowerCase.startsWith ("a") ||
        nextWordLowerCase.startsWith ("e") ||
        nextWordLowerCase.startsWith ("i") ||
        nextWordLowerCase.startsWith ("o") ||
       (nextWordLowerCase.startsWith ("u") && nextWordLowerCase.length() > 1))
    {
      aOrAn = "an";
    }

    return aOrAn;
  }

  /**
   * Deletes the contents of the specified StringBuilder
   *
   * @param s The StringBuilder to delete the content of, must not be null.
   *
   * @return The empty StringBuilder.
   */
  public static StringBuilder clear (StringBuilder s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.delete (0, s.length());
  }

  /**
   * Deletes the contents of the specified StringBuilder
   *
   * @param s The StringBuilder to delete the content of, must not be null.
   *
   * @return The empty StringBuilder.
   */
  public static com.badlogic.gdx.utils.StringBuilder clear (com.badlogic.gdx.utils.StringBuilder s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.delete (0, s.length());
  }

  /**
   * Replaces any duplicate whitespace in a string with a single space, also
   * removing tabs, newlines, etc in the process.
   * 
   * @param s The string to compress, must not be null.
   * 
   * @return The compressed string. 
   */
  public static String compressWhitespace (String s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.trim().replaceAll ("\\s+", " ");
  }

  /**
   * Deletes the last character from the specified StringBuilder.
   *
   * @param s The StringBuilder from which to delete the last character, must not be null, may be empty.
   *
   * @return The StringBuilder with its last character deleted and any remaining characters shifted left, or the
   * original StringBuilder if it was already empty.
   */
  public static StringBuilder deleteLastChar (StringBuilder s)
  {
    Arguments.checkIsNotNull (s, "s");

    if (s.length() > 0)
    {
      return s.deleteCharAt (s.length() - 1);
    }
    else
    {
      return s;
    }
  }

  /**
   * Deletes the last character from the specified StringBuilder.
   *
   * @param s The StringBuilder from which to delete the last character, must not be null, may be empty.
   *
   * @return The StringBuilder with its last character deleted and any remaining characters shifted left, or the
   * original StringBuilder if it was already empty.
   */
  public static com.badlogic.gdx.utils.StringBuilder deleteLastChar (com.badlogic.gdx.utils.StringBuilder s)
  {
    Arguments.checkIsNotNull (s, "s");

    if (s.length() > 0)
    {
      return s.deleteCharAt (s.length() - 1);
    }
    else
    {
      return s;
    }
  }

  /**
   * Gets a trimmed substring from the specified string. Any extra whitespace
   * will be deleted from the beginning and end of the substring.
   * 
   * @see String#trim()
   * @see String#substring(int, int)
   * 
   * @param s The string to get the trimmed substring from, must not be null,
   *          must not be empty.
   * 
   * @param beginIndex The character index to start the substring from,
   *                   must be >= 0 and < endIndex.
   * 
   * @param endIndex The string character index one past the end of the
   *                 substring, must be >= 0, > beginIndex, and <= s.length().
   * 
   * @return The trimmed substring.
   */
  public static String getTrimmedSubstring (String s, int beginIndex,
                                                      int endIndex)
  {
    Arguments.checkIsNotNullOrEmpty (s, "s");

    Arguments.checkLowerInclusiveBound (beginIndex, 0, "beginIndex");
    Arguments.checkLowerInclusiveBound (endIndex,   0, "endIndex");

    Arguments.checkUpperExclusiveBound (beginIndex, endIndex, "beginIndex",
                                                              "endIndex");

    Arguments.checkUpperInclusiveBound (endIndex, s.length(), "endIndex",
                                                              "s.length()");

    return s.substring(beginIndex, endIndex).trim();
  }

  /**
   * Checks whether the string s is comprised of only alphanumeric characters
   * (a to z, A to Z, or 0-9).
   * 
   * @param s The string to check, must not be null.
   * 
   * @return True if the string s is comprised of only alphanumeric characters.
   * False if the string s is empty, contains whitespace or any
   * non-alphanumeric character.
   */
  public static boolean isAlphanumeric (String s)
  {
    Arguments.checkIsNotNull (s, "s"); 

    return s.matches ("[a-zA-Z0-9]+");
  }

  /**
   * Checks whether the character c is a printable character.
   *
   * @param c The character to check.
   * 
   * @return True if the character c is a printable character, false otherwise.
   */
  public static boolean isPrintable (char c)
  {
    Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of (c);

    return ! Character.isISOControl (c) &&
           c != KeyEvent.CHAR_UNDEFINED &&
           unicodeBlock != null &&
           unicodeBlock != Character.UnicodeBlock.SPECIALS;
  }

  /**
   * Checks whether the string s is a printable string.
   *
   * @param s The string to check.
   *
   * @return True if the string s is a printable string, false if it is null, empty, or whitespace-only.
   */
  public static boolean isPrintable (final String s)
  {
    return s != null && ! s.isEmpty() && ! isWhitespace (s);
  }

  /**
   * Checks whether the string s is comprised of only whitespace.
   * <br/><br/>
   * Note: The input string may contain supplementary characters.
   * For rules on which characters are considered whitespace,
   * see @see Character#isWhitespace(char)
   * 
   * @param s The string to check, must not be null.
   * 
   * @return True if the string s is comprised of only whitespace or is empty.
   */
  public static boolean isWhitespace (String s)
  {
    Arguments.checkIsNotNull (s, "s"); 

    boolean isWhitespace = true;

    for (int i = 0; i < s.length(); ++i)
    {
      if (! Character.isWhitespace (s.codePointAt (i)))
      {
        isWhitespace = false;

        break;
      }
    }

    return isWhitespace;
  }

  /**
   * Automatically gets the correct plurally-agreeing form of the given phrase
   * depending on whether its preceding clarifying numerical value is equal 
   * to 1.
   * <br/><br/>
   * Note: 0 and negative values are always pluralized, e.g. 0 apples, or a
   * balance of -3 dollars.
   * 
   * @param variable The preceding clarifying numerical value
   * @param singular The singular form of the phrase
   * @param plural   The plural form of the phrase
   * 
   * @return The plurally-agreeing form of the phrase
   * 
   * @see #pluralizeS(int, java.lang.String)
   */
  public static String pluralize (int variable, String singular, String plural)
  {
    return String.valueOf (variable) + " " +
           (variable == 1 ? singular : plural);
  }

  /**
   * Automatically gets the correct plurally-agreeing simple -s (add s) form of
   * the given phrase depending on whether its preceding clarifying numerical
   * value is equal to 1.
   * <br/><br/>
   * If the phrase needs to be pluralized, a lowercase 's' will be appended to
   * the end of the phrase, otherwise the singular form of the phrase will be
   * returned. This method is only appropriate for phrases whose correct plural
   * form only involves adding an 's'.
   * <br/><br/>
   * Note: 0 and negative values are always pluralized, e.g. 0 apples, or a
   *       balance of -3 dollars
   * 
   * @param variable The preceding clarifying numerical value
   * @param singular The singular form of the phrase
   * 
   * @return The plurally-agreeing form of the phrase
   * 
   * @see #pluralize(int, java.lang.String, java.lang.String)
   */
  public static String pluralizeS (int variable, String singular)
  {
    return String.valueOf (variable) + " " + singular +
           (variable == 1 ? "" : "s");
  }

  /**
   * Removes any whitespace characters ( \t\n\x0B\f\r) from the
   * specified string.
   * 
   * @param s The string to remove any whitespace from.
   * 
   * @return The whitespace-stripped string.
   * 
   * @see java.util.regex.Pattern
   */
  public static String stripWhitespace (String s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.replaceAll ("\\s","");
  }

  /**
   * Converts a single word to the specified case.
   * 
   * @param word The word to convert, must not be null;
   * @param letterCase The case to convert the word to, use LetterCase.NONE to leave
   *                   the word as-is, must not be null.
   * 
   * @return  The word, converted to the specified case.
   */
  public static String toCase (String word, LetterCase letterCase)
  {
    Arguments.checkIsNotNull (word,       "word");
    Arguments.checkIsNotNull (letterCase, "letterCase");

    String caseCorrectedWord = word;

    switch (letterCase)
    {
      case PROPER:
      {
        caseCorrectedWord = toProperCase (word);

        break;
      }
      case LOWER:
      {
        caseCorrectedWord = word.toLowerCase();

        break;
      }
      case UPPER:
      {
        caseCorrectedWord = word.toUpperCase();

        break;
      }
      case NONE:
      {
        break;
      }
    }

    return caseCorrectedWord;
  }

  /**
   * Gets the English ordinal abbreviation for the specified integer
   * (e.g., 1st, 2nd, 3rd, etc.).
   *
   * @param n The integer to get the ordinal abbreviation for.
   *
   * @return A string containing the English ordinal abbreviation for the
   *         specified integer.
   */
  public static String toMixedOrdinal (int n)
  {
    String ordinal = Strings.toOrdinal (n);

    return n + ordinal.substring (ordinal.length() - 2);
  }

  /**
   * Gets the English ordinal words for the specified integer.
   *
   * @param n The integer to get the ordinal words for.
   *
   * @return A string containing the English ordinal words for the specified
   *         integer.
   */
  public static String toOrdinal (int n)
  {
    StringBuilder ordinal = new StringBuilder();

    ordinal.append ((n < 0)? "negative " : "");

    if (n != 0 && n != Integer.MIN_VALUE)
    {
      int nonOrdinalPart = 0;
      int ordinalPart    = 0;

      int nonnegativeN = Math.abs (n);

      if (nonnegativeN >= 100)
      {
        nonOrdinalPart =
                (int) ((Math.floor ((double) nonnegativeN / 100.0)) * 100.0);

        ordinal.append (Strings.toWords (nonOrdinalPart));

        ordinalPart = nonnegativeN - nonOrdinalPart;

        if (ordinalPart != 0)
        {
          ordinal.append (" and ");
        }
      }
      else
      {
        ordinalPart = nonnegativeN;
      }

      if (ordinalPart >= 0 && ordinalPart < 20)
      {
        switch (ordinalPart)
        {
          case  0: ordinal.append ("th");          break;
          case  1: ordinal.append ("first");       break;
          case  2: ordinal.append ("second");      break;
          case  3: ordinal.append ("third");       break;
          case  5: ordinal.append ("fifth");       break;
          case  8: ordinal.append ("eighth");      break;
          case  9: ordinal.append ("ninth");       break;
          case 12: ordinal.append ("twelfth");     break;

          default: ordinal.append(
                           Strings.toWords (ordinalPart)).append ("th");
                   break;
        }
      }
      else
      {
        switch (ordinalPart)
        {
          case 20: ordinal.append ("twentieth");   break;
          case 30: ordinal.append ("thirtieth");   break;
          case 40: ordinal.append ("fortieth");    break;
          case 50: ordinal.append ("fiftieth");    break;
          case 60: ordinal.append ("sixtieth");    break;
          case 70: ordinal.append ("seventieth");  break;
          case 80: ordinal.append ("eightieth");   break;
          case 90: ordinal.append ("ninetieth");   break;

          default: nonOrdinalPart =
                     (int) ((Math.floor ((double) ordinalPart / 10.0)) * 10.0);

                   ordinal.append(
                           Strings.toWords (nonOrdinalPart)).append ("-");

                   ordinalPart -= nonOrdinalPart;

                   ordinal.append (Strings.toOrdinal (ordinalPart));

                   break;
        }
      }
    }
    else if (n == Integer.MIN_VALUE)
    {
      ordinal.append ("two billion one hundred forty-seven million four " +
                      "hundred eighty-three thousand six hundred and " +
                      "forty-eighth");
    }
    else
    {
      ordinal.append ("zeroth");
    }

    return ordinal.toString();
  }
  
  /**
   * Converts a single word to proper case (first letter is capitalized, all subsequent characters are lowercase).
   *
   * @param word The single word to convert to proper case, must not be null.
   *
   * @return The word converted to proper case.
   */
  public static String toProperCase (String word)
  {
    Arguments.checkIsNotNull (word, "word");
    String firstLetter = word.toLowerCase().substring (0, 1);
    return word.toLowerCase().replaceFirst (firstLetter, firstLetter.toUpperCase());
  }

  /**
   * Converts a collection to a string representation.
   *
   * @param <T> The type of collection to convert to a string.
   * @param collection The collection to convert to a string, must not be null, must not have any null elements.
   * 
   * @return A string representation of the collection and its elements.
   */
  public static <T> String toString (Collection <T> collection)
  {
    Arguments.checkIsNotNull (collection, "collection");
    Arguments.checkHasNoNullElements (collection, "collection");

    int elementCounter = 0;

    StringBuilder stringBuilder = new StringBuilder();

    for (T element: collection)
    {
      ++elementCounter;

      stringBuilder.append (String.format ("%1$-13s %2$4s: %3$s\n",
              element.getClass().getSimpleName(), elementCounter, element));
    }

    return stringBuilder.toString();
  }

  /**
   * Converts an array to a string representation.
   *
   * @param <T> The type of array to convert to a string.
   * @param array The array to convert to a string, must not be null, must not have any null elements.
   *
   * @return A string representation of the array and its elements.
   */
  public static <T> String toString (T[] array)
  {
    Arguments.checkIsNotNull (array, "array");
    Arguments.checkHasNoNullElements (array, "array");

    return toString (Arrays.asList (array));
  }

  /**
   * Converts a throwable's stack trace to a string.
   * 
   * @param throwable The throwable who's stack trace will be converted to a
   *                  string, must not be null.
   * 
   * @return A string representation of the throwable's stack trace.
   */
  public static String toString (Throwable throwable)
  {
    Arguments.checkIsNotNull (throwable, "throwable");

    StringWriter stringWriter = new StringWriter();
    PrintWriter  printWriter  = new PrintWriter (stringWriter);

    throwable.printStackTrace (printWriter);

    return stringWriter.toString();
  }

  /**
   * Converts an iterable to a string representation.
   *
   * @param <T> The type of iterable to convert to a string.
   * @param iterable The iterable to convert to a string, must not be null.
   * 
   * @return A string representation of the iterable and its elements.
   */
  public static <T> String toString (Iterable <T> iterable)
  {
    Arguments.checkIsNotNull (iterable, "iterable");

    int elementCounter = 0;

    StringBuilder stringBuilder = new StringBuilder();

    for (T element: iterable)
    {
      ++elementCounter;

      stringBuilder.append (String.format ("%1$-13s %2$4s: %3$s\n",
              element.getClass().getSimpleName(), elementCounter, element));
    }

    return stringBuilder.toString();
  }

  /**
   * Converts a map to a string representation.
   *
   * @param <T> The key type of the specified map.
   * @param <U> The value type of the specified map.
   * 
   * @param map The map to convert to a string, must not be null.
   * 
   * @return A string representation of the map and its elements.
   */
  public static <T, U> String toString (Map <T, U> map)
  {
    Arguments.checkIsNotNull (map, "map");

    StringBuilder stringBuilder = new StringBuilder();

    int entryCounter = 0;

    for (Map.Entry <T, U> entry : map.entrySet())
    {
      ++entryCounter;

      T entryKey   = entry.getKey();
      U entryValue = entry.getValue();

      stringBuilder.append (String.format ("Entry         %1$4s:[" +
              "%2$10s, %3$10s]\n", entryCounter, entryKey, entryValue));
    }

    return stringBuilder.toString();
  }

  /**
   * Converts a multimap to a string representation.
   *
   * @param <T> The key type of the specified multimap.
   * @param <U> The value type of the specified multimap.
   * 
   * @param multimap The multimap to convert to a string, must not be null.
   * 
   * @return A string representation of the multimap and its elements.
   */
  public static <T, U> String toString (Multimap <T, U> multimap)
  {
    Arguments.checkIsNotNull (multimap, "multimap");

    StringBuilder stringBuilder = new StringBuilder();

    int entryCounter = 0;

    for (Map.Entry <T, U> entry : multimap.entries())
    {
      ++entryCounter;

      T entryKey   = entry.getKey();
      U entryValue = entry.getValue();

      stringBuilder.append (String.format ("Entry         %1$4s:[" +
              "%2$10s, %3$10s]\n", entryCounter, entryKey, entryValue));
    }

    return stringBuilder.toString();
  }

  /**
   * Converts a collection of list elements to a string list, separated by
   * separator, in case letterCase.
   * 
   * 
   * @param <T> The type of the list elements.
   * @param listElements The collection of list elements to convert, must not be
   *                     null, must not contain any null elements.
   * @param separator The separator that should be added between list elements,
   *                  must not be null.
   * @param letterCase The desired letter case of the list elements, must not be
   *                   null, choose LetterCase.NONE to leave the list elements as-is.
   * @param hasAnd     Whether or not to insert the word 'and ' between the last
   *                   two elements in the list, one space after the last
   *                   separator.
   * @return A string list of listElements, separated by separator, in case
   *         letterCase, with an optional 'and ' occurring between the last two
   *         elements of the list.
   */
  public static <T> String toStringList (Collection <T> listElements,
          String separator, LetterCase letterCase, boolean hasAnd)
  {
    Arguments.checkIsNotNull         (listElements, "listElements");
    Arguments.checkHasNoNullElements (listElements, "listElements");
    Arguments.checkIsNotNull         (separator,    "separator");
    Arguments.checkIsNotNull         (letterCase,   "letterCase");

    // Handle the first three special cases
    if (listElements.isEmpty())
    {
      return "";
    }
    else if (listElements.size() == 1)
    {
      return Strings.toCase (
              Iterables.getOnlyElement (listElements).toString(), letterCase);
    }
    else if (listElements.size() == 2)
    {
      Iterator <T> iterator = listElements.iterator();

      // Here, if the separator is a comma, for example, it's either:
      // "item1 and item2" or "item1,item2" (if no "and" is desired)
      // because "item1, and item2" doesn't make sense grammatically, which is
      // what would happen if we didn't treat this as a special case
      return Strings.toCase (
              iterator.next().toString() + (hasAnd ? " and " : separator) +
                      iterator.next().toString(), letterCase);
    }

    StringBuilder s = new StringBuilder();

    for (T element : listElements)
    {
      String elementString = Strings.toCase (element.toString(),
              letterCase);
      s.append(elementString).append (separator);
    }

    try
    {
      // Delete the extra comma at the end of the last element in the list.
      s.delete (s.length() - separator.length(), s.length());

      if (hasAnd && s.lastIndexOf (separator) >= 0)
      {
        // Insert the word 'and' between the last two elements in the list,
        // after the last comma.
        s.insert (s.lastIndexOf (separator) + 1, "and ");
      }
    }
    catch (StringIndexOutOfBoundsException ignoredException)
    {
    }

    return s.toString();
  }

  /**
   * Gets the English words for the specified integer.
   *
   * @param n The integer to get the words for.
   *
   * @return A string containing the English words for the specified integer.
   */
  public static String toWords (int n)
  {
    StringBuilder words = new StringBuilder(); 

    words.append ((n < 0)? "negative " : "");

    if (n != Integer.MIN_VALUE)
    {
      int remainder = 0;

      int nonnegativeN = Math.abs (n);

      if (nonnegativeN >= 0 && nonnegativeN < 20)
      {
        switch (nonnegativeN)
        {
          case  0: words.append ("zero");      break;
          case  1: words.append ("one");       break;
          case  2: words.append ("two");       break;
          case  3: words.append ("three");     break;
          case  4: words.append ("four");      break;
          case  5: words.append ("five");      break;
          case  6: words.append ("six");       break;
          case  7: words.append ("seven");     break;
          case  8: words.append ("eight");     break;
          case  9: words.append ("nine");      break;
          case 10: words.append ("ten");       break;
          case 11: words.append ("eleven");    break;
          case 12: words.append ("twelve");    break;
          case 13: words.append ("thirteen");  break;
          case 14: words.append ("fourteen");  break;
          case 15: words.append ("fifteen");   break;
          case 16: words.append ("sixteen");   break;
          case 17: words.append ("seventeen"); break;
          case 18: words.append ("eighteen");  break;
          case 19: words.append ("nineteen");  break;
        }
      }
      else if (nonnegativeN >= 20 && nonnegativeN < 100)
      {
        switch ((int) Math.floor (nonnegativeN / 10.0))
        {
          case 2: words.append ("twenty");  break;
          case 3: words.append ("thirty");  break;
          case 4: words.append ("forty");   break;
          case 5: words.append ("fifty");   break;
          case 6: words.append ("sixty");   break;
          case 7: words.append ("seventy"); break;
          case 8: words.append ("eighty");  break;
          case 9: words.append ("ninety");  break;
        }

        remainder = nonnegativeN % 10;

        if (remainder != 0)
        {
          words.append ("-").append (Strings.toWords (remainder));
        }
      }
      else if (nonnegativeN >= 100 && nonnegativeN < 1000)
      {
        words.append (
                Strings.toWords ((int) Math.floor (nonnegativeN / 100.0)));

        words.append (" hundred");

        remainder = nonnegativeN % 100;

        if (remainder != 0)
        {
          words.append (" and ").append (Strings.toWords (remainder));
        }
      }
      else if (nonnegativeN >= 1000 && nonnegativeN < 1000000)
      {
        words.append (
                Strings.toWords ((int) Math.floor (nonnegativeN / 1000.0)));

        words.append (" thousand");

        remainder = nonnegativeN % 1000;

        if (remainder != 0)
        {
          words.append (" ").append (Strings.toWords (remainder));
        }
      }
      else if (nonnegativeN >= 1000000 && nonnegativeN < 1000000000)
      {
        words.append (Strings.toWords ((int) Math.floor (
                (double) nonnegativeN / 1000000.0)));

        words.append (" million");

        remainder = nonnegativeN % 1000000;

        if (remainder != 0)
        {
          words.append (" ").append (Strings.toWords (remainder));
        }
      }
      else
      {
        words.append (Strings.toWords ((int) Math.floor (
                (double) nonnegativeN / 1000000000.0)));
        
        words.append (" billion");

        remainder = nonnegativeN % 1000000000;

        if (remainder != 0)
        {
          words.append (" ").append (Strings.toWords (remainder));
        }
      }
    }
    else
    {
      words.append ("two billion one hundred forty-seven million four " +
                    "hundred eighty-three thousand six hundred and " +
                    "forty-eight");
    }

    return words.toString();
  }
  
  private Strings()
  {
    Classes.instantiationNotAllowed();
  }
}