package com.forerunnergames.tools.common;

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
}
