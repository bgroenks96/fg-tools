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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.slf4j.helpers.MessageFormatter;

import org.apache.commons.lang3.text.WordUtils;

public final class Strings
{
  /*
   * Gets the correct English article ("a" or "an") for the word proceeding it.
   *
   * @param nextWord The word proceeding the article ("a" or "an"), must not be null, empty, or blank (whitespace only).
   *
   * @return The correct English article ("a" or "an") for the word proceeding it. "an" if nextWord stars with a vowel,
   * "a" otherwise. Special case: if nextWord is simply "u", it will return "a". Consider the following example:
   * "An umbrella starts with a u." (correct) vs "An umbrella starts with an u." (incorrect)
   */
  public static String aOrAn (final String nextWord)
  {
    Arguments.checkIsNotNullOrEmptyOrBlank (nextWord, "nextWord");

    final String nextWordLowerCase = nextWord.toLowerCase (Locale.ENGLISH);

    return nextWordLowerCase.startsWith ("a") || nextWordLowerCase.startsWith ("e")
            || nextWordLowerCase.startsWith ("i") || nextWordLowerCase.startsWith ("o")
            || (nextWordLowerCase.startsWith ("u") && nextWordLowerCase.length () > 1) ? "an" : "a";
  }

  /**
   * Deletes the contents of the specified StringBuilder
   *
   * @param s
   *          The StringBuilder to delete the content of, must not be null.
   *
   * @return The empty StringBuilder.
   */
  public static StringBuilder clear (final StringBuilder s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.delete (0, s.length ());
  }

  /**
   * Replaces any duplicate whitespace in a string with a single space, also removing tabs, newlines, etc in the
   * process.
   *
   * @param s
   *          The string to compress, must not be null.
   *
   * @return The compressed string.
   */
  public static String compressWhitespace (final String s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.trim ().replaceAll ("\\s+", " ");
  }

  /**
   * Checks whether the specified string is contained in the specified StringBuilder.
   *
   * @param stringBuilder
   *          The StringBuilder, must not be null.
   * @param s
   *          The string, must not be null.
   *
   * @return true if at least one instance of s is contained within stringBuilder, false otherwise
   */
  public static boolean contains (final StringBuilder stringBuilder, final String s)
  {
    Arguments.checkIsNotNull (stringBuilder, "stringbuilder");
    Arguments.checkIsNotNullOrEmptyOrBlank (s, "s");

    return stringBuilder.indexOf (s) > -1;
  }

  /**
   * Deletes the last character from the specified StringBuilder.
   *
   * @param s
   *          The StringBuilder from which to delete the last character, must not be null, may be empty.
   *
   * @return The StringBuilder with its last character deleted and any remaining characters shifted left, or the
   *         original StringBuilder if it was already empty.
   */
  public static StringBuilder deleteLastChar (final StringBuilder s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.length () > 0 ? s.deleteCharAt (s.length () - 1) : s;
  }

  /**
   * Gets a trimmed substring from the specified string. Any extra whitespace will be deleted from the beginning and end
   * of the substring.
   *
   * @param s
   *          The string to get the trimmed substring from, must not be null, must not be empty.
   * @param beginIndex
   *          The character index to start the substring from, must be >= 0 and < endIndex.
   * @param endIndex
   *          The string character index one past the end of the substring, must be >= 0, > beginIndex, and <=
   *          s.length().
   *
   * @return The trimmed substring.
   *
   * @see String#trim()
   * @see String#substring(int, int)
   */
  public static String getTrimmedSubstring (final String s, final int beginIndex, final int endIndex)
  {
    Arguments.checkIsNotNullOrEmpty (s, "s");
    Arguments.checkLowerInclusiveBound (beginIndex, 0, "beginIndex");
    Arguments.checkLowerInclusiveBound (endIndex, 0, "endIndex");
    Arguments.checkUpperExclusiveBound (beginIndex, endIndex, "beginIndex", "endIndex");
    Arguments.checkUpperInclusiveBound (endIndex, s.length (), "endIndex", "s.length()");

    return s.substring (beginIndex, endIndex).trim ();
  }

  /**
   * Checks whether the string s is comprised of only alphanumeric characters (a to z, A to Z, or 0-9).
   *
   * @param s
   *          The string to check, must not be null.
   *
   * @return True if the string s is comprised of only alphanumeric characters. False if the string s is empty, contains
   *         whitespace or any non-alphanumeric character.
   */
  public static boolean isAlphanumeric (final String s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.matches ("[a-zA-Z0-9]+");
  }

  /**
   * Checks whether the character c is a printable character.
   *
   * @param c
   *          The character to check.
   *
   * @return True if the character c is a printable character, false otherwise.
   */
  public static boolean isPrintable (final char c)
  {
    final Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of (c);

    return !Character.isISOControl (c) && c != KeyEvent.CHAR_UNDEFINED && unicodeBlock != null
            && unicodeBlock != Character.UnicodeBlock.SPECIALS;
  }

  /**
   * Checks whether the string s is a printable string.
   *
   * @param s
   *          The string to check.
   *
   * @return True if the string s is a printable string, false if it is null, empty, or whitespace-only.
   */
  public static boolean isPrintable (final String s)
  {
    return s != null && !s.isEmpty () && !isWhitespace (s);
  }

  /**
   * Checks whether the string s is comprised of only whitespace. <br/>
   * <br/>
   * Note: The input string may contain supplementary characters. For rules on which characters are considered
   * whitespace, see @see Character#isWhitespace(char)
   *
   * @param s
   *          The string to check, must not be null.
   *
   * @return True if the string s is comprised of only whitespace or is empty.
   */
  public static boolean isWhitespace (final String s)
  {
    Arguments.checkIsNotNull (s, "s");

    for (int i = 0; i < s.length (); ++i)
    {
      if (!Character.isWhitespace (s.codePointAt (i))) return false;
    }

    return true;
  }

  /**
   * Gets the correct plurally-agreeing form of the given phrase depending on whether its preceding clarifying numerical
   * value is equal to 1. <br/>
   * <br/>
   * Note: 0 and negative values are always pluralized, e.g. 0 apples, or a balance of -1 dollars.
   *
   * @param count
   *          The preceding clarifying numerical value, used to determine if the phrase is singular or plural
   * @param singular
   *          The singular form of the phrase denoting a quantity of exactly one.
   * @param plural
   *          The plural form of the phrase denoting a quantity of greater than one, zero, or a negative quantity.
   *
   * @return The plurally-agreeing form of the phrase, preceded by the count as a number, with a space between the count
   *         & phrase.
   *
   * @see #pluralize(int, String, String, String)
   * @see #pluralizeS(int, String)
   * @see #pluralizeWord(int, String, String)
   */
  public static String pluralize (final int count, final String singular, final String plural)
  {
    Arguments.checkIsNotNull (singular, "singular");
    Arguments.checkIsNotNull (plural, "plural");

    return String.valueOf (count) + " " + (count == 1 ? singular : plural);
  }

/**
   * Gets the correct plurally-agreeing form of the given phrase depending on whether its preceding clarifying numerical
   * value is equal to 1. <br/>
   * <br/>
   * Note: 0 and negative values are always pluralized, e.g. 0 apples, or a balance of -1 dollars. <br/>
   *
   * @param count
   *          The clarifying numerical value, used to determine if the phrase is singular or plural
   * @param singular
   *          The singular form of the phrase denoting a quantity of exactly one.
   * @param plural
   *          The plural form of the phrase denoting a quantity of greater than one, zero, or a negative quantity.
   *
   * @return The plurally-agreeing form of the phrase, excluding the count.
   *
   * @see #pluralizeS(int, String)
   * @see #pluralize(int, String, String)
   * @see #pluralize(int, String, String, String)
   */
  public static String pluralizeWord (final int count, final String singular, final String plural)
  {
    Arguments.checkIsNotNull (singular, "singular");
    Arguments.checkIsNotNull (plural, "plural");

    return count == 1 ? singular : plural;
  }

  /**
   * Gets the correct plurally-agreeing simple -s (add s) form of the given phrase depending on whether its preceding
   * clarifying numerical value is equal to 1. <br/>
   * <br/>
   * If the phrase needs to be pluralized, a lowercase 's' will be appended to the end of the phrase, otherwise the
   * singular form of the phrase will be returned. This method is only appropriate for phrases whose correct plural form
   * only involves adding an 's'. <br/>
   * <br/>
   * Note: 0 and negative values are always pluralized, e.g. 0 apples, or a balance of -1 dollars
   *
   * @param count
   *          The preceding clarifying numerical value, used to determine if the phrase is singular or plural.
   * @param singular
   *          The singular form of the phrase denoting a quantity of exactly one.
   *
   * @return The plurally-agreeing form of the phrase, preceded by the count as a number, with a space between the count
   *         & phrase.
   *
   * @see #pluralize(int, String, String)
   * @see #pluralizeWord(int, String, String)
   * @see #pluralize(int, String, String, String)
   */
  public static String pluralizeS (final int count, final String singular)
  {
    Arguments.checkIsNotNull (singular, "singular");

    return String.valueOf (count) + " " + singular + (count == 1 ? "" : "s");
  }

  /**
   * Gets the correct plurally-agreeing form of the given phrase depending on whether its preceding clarifying numerical
   * value is A) equal to 0, B) equal to 1, or C) greater than 1 or less than 0.<br/>
   * <br/>
   * Note: Negative values are always pluralized, e.g. a balance of -1 dollars.
   *
   * @param count
   *          The preceding clarifying numerical value, used to determine if the phrase is zero, singular or plural.
   * @param none
   *          The form of the phrase denoting a quantity of exactly zero.
   * @param singular
   *          The singular form of the phrase denoting a quantity of exactly one.
   * @param plural
   *          The plural form of the phrase denoting a quantity of greater than one, or a negative quantity.
   *
   * @return The plurally-agreeing form of the phrase, excluding the count.
   *
   * @see #pluralize(int, String, String)
   * @see #pluralizeS(int, String)
   * @see #pluralizeWord(int, String, String)
   */
  public static String pluralize (final int count, final String none, final String singular, final String plural)
  {
    Arguments.checkIsNotNull (none, "none");
    Arguments.checkIsNotNull (singular, "singular");
    Arguments.checkIsNotNull (plural, "plural");

    return count == 0 ? none : count == 1 ? singular : plural;
  }

  /**
   * Splits a word into multiple words using uppercase characters as a delimiter, but the delimiter is not discarded.
   * For example: ThisWordHasUpperCASECharactersInIT will be split into the separate words: { "This", "Word", "Has",
   * "Upper", "C", "A", "S", "E", "Characters", "In", "I", "T" }
   *
   * @param word
   *          The word to split, must not be null, may be empty, may be blank (whitespace only), may contain whitespace,
   *          may contain Unicode characters.
   *
   * @return An array of the split words, each one maintaining their original case, or the original word in a single
   *         element array, if there was nothing to split, or an empty array of length 0 if the original word was empty.
   */
  public static String[] splitByUpperCase (final String word)
  {
    Arguments.checkIsNotNull (word, "word");

    final String[] rawSplitWords = word.split ("(?=\\p{Lu})");
    final ArrayList <String> nonEmptySplitWords = new ArrayList <> ();

    for (final String splitWord : rawSplitWords)
    {
      if (!splitWord.isEmpty ()) nonEmptySplitWords.add (splitWord);
    }

    return nonEmptySplitWords.toArray (new String [nonEmptySplitWords.size ()]);
  }

  /**
   * Removes any whitespace characters ( \t\n\x0B\f\r) from the specified string.
   *
   * @param s
   *          The string to remove any whitespace from.
   *
   * @return The whitespace-stripped string.
   *
   * @see java.util.regex.Pattern
   */
  public static String stripWhitespace (final String s)
  {
    Arguments.checkIsNotNull (s, "s");

    return s.replaceAll ("\\s", "");
  }

  /**
   * Converts a single word to the specified case.
   *
   * @param word
   *          The word to convert, must not be null;
   * @param letterCase
   *          The case to convert the word to, use LetterCase.NONE to leave the word as-is, must not be null.
   *
   * @return The word, converted to the specified case.
   */
  public static String toCase (final String word, final LetterCase letterCase)
  {
    Arguments.checkIsNotNull (word, "word");
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
        caseCorrectedWord = word.toLowerCase ();

        break;
      }
      case UPPER:
      {
        caseCorrectedWord = word.toUpperCase ();

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
   * Gets the English ordinal abbreviation for the specified integer (e.g., 1st, 2nd, 3rd, etc.).
   *
   * @param n
   *          The integer to get the ordinal abbreviation for.
   *
   * @return A string containing the English ordinal abbreviation for the specified integer.
   */
  public static String toMixedOrdinal (final int n)
  {
    final String ordinal = toOrdinal (n);

    return n + ordinal.substring (ordinal.length () - 2);
  }

  /**
   * Gets the English ordinal words for the specified integer.
   *
   * @param n
   *          The integer to get the ordinal words for.
   *
   * @return A string containing the English ordinal words for the specified integer.
   */
  public static String toOrdinal (final int n)
  {
    final StringBuilder ordinal = new StringBuilder ();

    ordinal.append ((n < 0) ? "negative " : "");

    if (n != 0 && n != Integer.MIN_VALUE)
    {
      int nonOrdinalPart;
      int ordinalPart;

      final int nonnegativeN = Math.abs (n);

      if (nonnegativeN >= 100)
      {
        nonOrdinalPart = (int) ((Math.floor (nonnegativeN / 100.0)) * 100.0);

        ordinal.append (toWords (nonOrdinalPart));

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
          case 0:
            ordinal.append ("th");
            break;
          case 1:
            ordinal.append ("first");
            break;
          case 2:
            ordinal.append ("second");
            break;
          case 3:
            ordinal.append ("third");
            break;
          case 5:
            ordinal.append ("fifth");
            break;
          case 8:
            ordinal.append ("eighth");
            break;
          case 9:
            ordinal.append ("ninth");
            break;
          case 12:
            ordinal.append ("twelfth");
            break;
          default:
            ordinal.append (toWords (ordinalPart)).append ("th");
            break;
        }
      }
      else
      {
        switch (ordinalPart)
        {
          case 20:
            ordinal.append ("twentieth");
            break;
          case 30:
            ordinal.append ("thirtieth");
            break;
          case 40:
            ordinal.append ("fortieth");
            break;
          case 50:
            ordinal.append ("fiftieth");
            break;
          case 60:
            ordinal.append ("sixtieth");
            break;
          case 70:
            ordinal.append ("seventieth");
            break;
          case 80:
            ordinal.append ("eightieth");
            break;
          case 90:
            ordinal.append ("ninetieth");
            break;

          default:
            nonOrdinalPart = (int) ((Math.floor (ordinalPart / 10.0)) * 10.0);
            ordinal.append (toWords (nonOrdinalPart)).append ("-");
            ordinalPart -= nonOrdinalPart;
            ordinal.append (toOrdinal (ordinalPart));
            break;
        }
      }
    }
    else if (n == Integer.MIN_VALUE)
    {
      ordinal.append ("two billion one hundred forty-seven million four "
              + "hundred eighty-three thousand six hundred and " + "forty-eighth");
    }
    else
    {
      ordinal.append ("zeroth");
    }

    return ordinal.toString ();
  }

  /**
   * Converts a single word to proper case (first letter is capitalized, all subsequent characters are lowercase).
   *
   * @param word
   *          The single word to convert to proper case, must not be null.
   *
   * @return The word converted to proper case.
   */
  public static String toProperCase (final String word)
  {
    Arguments.checkIsNotNull (word, "word");

    return WordUtils.capitalizeFully (word);
  }

  /**
   * Converts an array to a string representation.
   *
   * @param <T>
   *          The type of array to convert to a string.
   * @param array
   *          The array to convert to a string, must not be null, must not have any null elements.
   *
   * @return A string representation of the array and its elements.
   */
  public static <T> String toString (final T[] array)
  {
    Arguments.checkIsNotNull (array, "array");
    Arguments.checkHasNoNullElements (array, "array");

    return toString (Arrays.asList (array));
  }

  /**
   * Converts a throwable's stack trace to a string.
   *
   * @param throwable
   *          The throwable who's stack trace will be converted to a string, must not be null.
   *
   * @return A string representation of the throwable's stack trace.
   */
  public static String toString (final Throwable throwable)
  {
    Arguments.checkIsNotNull (throwable, "throwable");

    final StringWriter stringWriter = new StringWriter ();
    final PrintWriter printWriter = new PrintWriter (stringWriter);

    throwable.printStackTrace (printWriter);

    return stringWriter.toString ();
  }

  /**
   * Converts an Iterable to a string representation.
   *
   * @param <T>
   *          The type of Iterable to convert to a string.
   * @param iterable
   *          The Iterable to convert to a string, must not be null.
   *
   * @return A string representation of the Iterable and its elements.
   */
  public static <T> String toString (final Iterable <T> iterable)
  {
    Arguments.checkIsNotNull (iterable, "iterable");

    final StringBuilder stringBuilder = new StringBuilder ("\n");

    int elementCounter = 0;

    for (final T element : iterable)
    {
      ++elementCounter;

      stringBuilder.append (String.format ("%1$s: %2$s\n", elementCounter, element));
    }

    return stringBuilder.toString ();
  }

  /**
   * Converts a map to a string representation.
   *
   * @param <T>
   *          The key type of the specified map.
   * @param <U>
   *          The value type of the specified map.
   * @param map
   *          The map to convert to a string, must not be null.
   *
   * @return A string representation of the map and its elements.
   */
  public static <T, U> String toString (final Map <T, U> map)
  {
    Arguments.checkIsNotNull (map, "map");

    final StringBuilder stringBuilder = new StringBuilder ("\n");

    int entryCounter = 0;

    for (final Map.Entry <T, U> entry : map.entrySet ())
    {
      ++entryCounter;

      final T entryKey = entry.getKey ();
      final U entryValue = entry.getValue ();

      stringBuilder.append (String.format ("Entry %1$s:[%2$s, %3$s]\n", entryCounter, entryKey, entryValue));
    }

    return stringBuilder.toString ();
  }

  /**
   * Converts a multimap to a string representation.
   *
   * @param <T>
   *          The key type of the specified multimap.
   * @param <U>
   *          The value type of the specified multimap.
   * @param multimap
   *          The multimap to convert to a string, must not be null.
   *
   * @return A string representation of the multimap and its elements.
   */
  public static <T, U> String toString (final Multimap <T, U> multimap)
  {
    Arguments.checkIsNotNull (multimap, "multimap");

    final StringBuilder stringBuilder = new StringBuilder ("\n");

    int entryCounter = 0;

    for (final Map.Entry <T, U> entry : multimap.entries ())
    {
      ++entryCounter;

      final T entryKey = entry.getKey ();
      final U entryValue = entry.getValue ();

      stringBuilder.append (String.format ("Entry %1$s:[%2$s, %3$s]\n", entryCounter, entryKey, entryValue));
    }

    return stringBuilder.toString ();
  }

  /**
   * Converts list elements to a string list, separated by separator, in case letterCase.
   *
   * @param <T>
   *          The type of the list elements.
   * @param listElements
   *          The list elements to convert, must not be null, must not contain any null elements.
   * @param separator
   *          The separator that should be added between list elements, must not be null.
   * @param letterCase
   *          The desired letter case of the list elements, must not be null, choose LetterCase.NONE to leave the list
   *          elements as-is.
   * @param hasAnd
   *          Whether or not to insert the word 'and ' between the last two elements in the list, one space after the
   *          last separator.
   *
   * @return A string list of listElements, separated by separator, in case letterCase, with an optional 'and '
   *         occurring between the last two elements of the list.
   */
  @SafeVarargs
  public static <T> String toStringList (final String separator,
                                         final LetterCase letterCase,
                                         final boolean hasAnd,
                                         final T... listElements)
  {
    return toStringList (ImmutableList.copyOf (listElements), separator, letterCase, hasAnd);
  }

  /**
   * Converts a collection of list elements to a string list, separated by separator, in case letterCase.
   *
   * @param <T>
   *          The type of the list elements.
   * @param listElements
   *          The collection of list elements to convert, must not be null, must not contain any null elements.
   * @param separator
   *          The separator that should be added between list elements, must not be null.
   * @param letterCase
   *          The desired letter case of the list elements, must not be null, choose LetterCase.NONE to leave the list
   *          elements as-is.
   * @param hasAnd
   *          Whether or not to insert the word 'and ' between the last two elements in the list, one space after the
   *          last separator.
   *
   * @return A string list of listElements, separated by separator, in case letterCase, with an optional 'and '
   *         occurring between the last two elements of the list.
   */
  public static <T> String toStringList (final Collection <T> listElements,
                                         final String separator,
                                         final LetterCase letterCase,
                                         final boolean hasAnd)
  {
    Arguments.checkIsNotNull (listElements, "listElements");
    Arguments.checkHasNoNullElements (listElements, "listElements");
    Arguments.checkIsNotNull (separator, "separator");
    Arguments.checkIsNotNull (letterCase, "letterCase");

    final ImmutableList.Builder <T> printableListElementsBuilder = ImmutableList.builder ();

    for (final T element : listElements)
    {
      if (isPrintable (element.toString ())) printableListElementsBuilder.add (element);
    }

    final ImmutableList <T> printableListElements = printableListElementsBuilder.build ();

    // Handle the first three special cases
    if (printableListElements.isEmpty ())
    {
      return "";
    }
    else if (printableListElements.size () == 1)
    {
      return toCase (Iterables.getOnlyElement (printableListElements).toString (), letterCase);
    }
    else if (printableListElements.size () == 2)
    {
      final Iterator <T> iterator = printableListElements.iterator ();

      // Here, if the separator is a comma, for example, it's either:
      // "item1 and item2" or "item1,item2" (if no "and" is desired)
      // because "item1, and item2" doesn't make sense grammatically, which is
      // what would happen if we didn't treat this as a special case
      return toCase (iterator.next ().toString () + (hasAnd ? " and " : separator) + iterator.next ().toString (),
                     letterCase);
    }

    final StringBuilder s = new StringBuilder ();

    for (final T element : printableListElements)
    {
      final String elementString = toCase (element.toString (), letterCase);

      s.append (elementString).append (separator);
    }

    try
    {
      // Delete the extra comma at the end of the last element in the list.
      s.delete (s.length () - separator.length (), s.length ());

      if (hasAnd && s.lastIndexOf (separator) >= 0)
      {
        // Insert the word 'and' between the last two elements in the list,
        // after the last comma.
        s.insert (s.lastIndexOf (separator) + 1, "and ");
      }
    }
    catch (final StringIndexOutOfBoundsException ignored)
    {
    }

    return s.toString ();
  }

  /**
   * Gets the English words for the specified integer.
   *
   * @param n
   *          The integer to get the words for.
   *
   * @return A string containing the English words for the specified integer.
   */
  public static String toWords (final int n)
  {
    final StringBuilder words = new StringBuilder ();

    words.append ((n < 0) ? "negative " : "");

    if (n != Integer.MIN_VALUE)
    {
      int remainder;

      final int nonnegativeN = Math.abs (n);

      if (nonnegativeN >= 0 && nonnegativeN < 20)
      {
        switch (nonnegativeN)
        {
          case 0:
            words.append ("zero");
            break;
          case 1:
            words.append ("one");
            break;
          case 2:
            words.append ("two");
            break;
          case 3:
            words.append ("three");
            break;
          case 4:
            words.append ("four");
            break;
          case 5:
            words.append ("five");
            break;
          case 6:
            words.append ("six");
            break;
          case 7:
            words.append ("seven");
            break;
          case 8:
            words.append ("eight");
            break;
          case 9:
            words.append ("nine");
            break;
          case 10:
            words.append ("ten");
            break;
          case 11:
            words.append ("eleven");
            break;
          case 12:
            words.append ("twelve");
            break;
          case 13:
            words.append ("thirteen");
            break;
          case 14:
            words.append ("fourteen");
            break;
          case 15:
            words.append ("fifteen");
            break;
          case 16:
            words.append ("sixteen");
            break;
          case 17:
            words.append ("seventeen");
            break;
          case 18:
            words.append ("eighteen");
            break;
          case 19:
            words.append ("nineteen");
            break;
        }
      }
      else if (nonnegativeN >= 20 && nonnegativeN < 100)
      {
        switch ((int) Math.floor (nonnegativeN / 10.0))
        {
          case 2:
            words.append ("twenty");
            break;
          case 3:
            words.append ("thirty");
            break;
          case 4:
            words.append ("forty");
            break;
          case 5:
            words.append ("fifty");
            break;
          case 6:
            words.append ("sixty");
            break;
          case 7:
            words.append ("seventy");
            break;
          case 8:
            words.append ("eighty");
            break;
          case 9:
            words.append ("ninety");
            break;
        }

        remainder = nonnegativeN % 10;

        if (remainder != 0)
        {
          words.append ("-").append (toWords (remainder));
        }
      }
      else if (nonnegativeN >= 100 && nonnegativeN < 1000)
      {
        words.append (toWords ((int) Math.floor (nonnegativeN / 100.0)));

        words.append (" hundred");

        remainder = nonnegativeN % 100;

        if (remainder != 0)
        {
          words.append (" and ").append (toWords (remainder));
        }
      }
      else if (nonnegativeN >= 1000 && nonnegativeN < 1000000)
      {
        words.append (toWords ((int) Math.floor (nonnegativeN / 1000.0)));

        words.append (" thousand");

        remainder = nonnegativeN % 1000;

        if (remainder != 0)
        {
          words.append (" ").append (toWords (remainder));
        }
      }
      else if (nonnegativeN >= 1000000 && nonnegativeN < 1000000000)
      {
        words.append (toWords ((int) Math.floor (nonnegativeN / 1000000.0)));

        words.append (" million");

        remainder = nonnegativeN % 1000000;

        if (remainder != 0)
        {
          words.append (" ").append (toWords (remainder));
        }
      }
      else
      {
        words.append (toWords ((int) Math.floor (nonnegativeN / 1000000000.0)));

        words.append (" billion");

        remainder = nonnegativeN % 1000000000;

        if (remainder != 0)
        {
          words.append (" ").append (toWords (remainder));
        }
      }
    }
    else
    {
      words.append ("two billion one hundred forty-seven million four "
              + "hundred eighty-three thousand six hundred and " + "forty-eight");
    }

    return words.toString ();
  }

  /**
   * Formats the given String using the slf4j MessageFormatter.
   */
  public static String format (final String message, final Object... args)
  {
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");

    return MessageFormatter.arrayFormat (message, args).getMessage ();
  }

  private Strings ()
  {
    Classes.instantiationNotAllowed ();
  }
}
