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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringsTest
{
  @Test
  public void testToProperCase ()
  {
    final String test = "   hello world!  lol   ";
    final String expected = "   Hello World!  Lol   ";
    final String actual = Strings.toProperCase (test);

    assertEquals (expected, actual);
  }

  @Test
  public void testToStringList ()
  {
    final String element1 = "  hello    ";
    final String element2 = "  big world    ";
    final String element3 = "  galaxy   ";
    final String element4 = "  universe   ";
    final String separator = ", ";
    final LetterCase letterCase = LetterCase.PROPER;
    final boolean hasAnd = false;
    final String expected = "  Hello    ,   Big World    ,   Galaxy   ,   Universe   ";
    final String actual = Strings.toStringList (separator, letterCase, hasAnd, element1, element2, element3, element4);

    assertEquals (expected, actual);
  }

  @Test
  public void testSplitByUpperCase ()
  {
    final String word = "  HELLO,WORlD!!!   ";
    final String[] expected = { "  ", "H", "E", "L", "L", "O,", "W", "O", "Rl", "D!!!   " };
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test
  public void testSplitByUpperCase2 ()
  {
    final String word = "ThisIs A Camel-CaseWord.2A";
    final String[] expected = { "This", "Is ", "A ", "Camel-", "Case", "Word.2", "A" };
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test
  public void testSplitByUpperCaseNothingToSplit ()
  {
    final String word = " this string really doesn't have anything to split... ";
    final String[] expected = { " this string really doesn't have anything to split... " };
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test
  public void testSplitByUpperCaseEmptyString ()
  {
    final String word = "";
    final String[] expected = new String [0];
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test
  public void testSplitByUpperCaseAllWhitespace ()
  {
    final String word = " \b \t     \r \n\n   ";
    final String[] expected = { " \b \t     \r \n\n   " };
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test
  public void testSplitByUpperCaseAllUppercase ()
  {
    final String word = "THISISALLUPPERCASE";
    final String[] expected = { "T", "H", "I", "S", "I", "S", "A", "L", "L", "U", "P", "P", "E", "R", "C", "A", "S",
                                "E" };
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test
  public void testSplitByUpperCaseSingleUpperCaseCharacter ()
  {
    final String word = "A";
    final String[] expected = { "A" };
    final String[] actual = Strings.splitByUpperCase (word);

    assertArrayEqualsPrintAllElementsOnFail (expected, actual);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSplitByUpperCaseNullStringThrowsException ()
  {
    Strings.splitByUpperCase (null);
  }

  private <T> void assertArrayEqualsPrintAllElementsOnFail (final T[] expected, final T[] actual)
  {
    assertArrayEquals ("\n\nExpected:\n" + Strings.toString (expected) + "\nActual:\n" + Strings.toString (actual)
            + "\n", expected, actual);
  }
}
